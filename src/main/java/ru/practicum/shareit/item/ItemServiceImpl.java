package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.ItemRequest.ItemRequestRepository;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    private final ItemRequestRepository itemRequestRepository;

    public List<ItemDto> getItems(Long userId, int from, int size) {
        if (size < 1 || from < 0) {
            throw new IllegalArgumentException("Wrong page number");
        }
        return itemRepository.findAllItemsByOwnerIdOrderById(userId).stream()
                .map(item -> {
                    ItemDto itemDto = ItemMapper.mapItemToDto(item);
                    Booking lastBooking = getLastBooking(item);
                    if (lastBooking != null) {
                        itemDto.setLastBooking(BookingMapper.toDto(lastBooking));
                    }
                    Booking nextBooking = getNextBooking(item);
                    if (nextBooking != null) {
                        itemDto.setNextBooking(BookingMapper.toDto(nextBooking));
                    }
                    return itemDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item not found"));

        ItemDto itemDto = ItemMapper.mapItemToDto(item);

        if (Objects.equals(item.getOwner().getId(), userId)) {
            Booking lastBooking = getLastBooking(item);
            if (lastBooking != null) {
                itemDto.setLastBooking(BookingMapper.toDto(lastBooking));
            }

            Booking nextBooking = getNextBooking(item);
            if (nextBooking != null) {
                itemDto.setNextBooking(BookingMapper.toDto(nextBooking));
            }
        }
        List<CommentDto> itemComments = commentRepository.findAllByItemId(itemId)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
        itemDto.setComments(itemComments);
        return itemDto;
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {

        Item item = ItemMapper.mapDtoToItem(itemDto);
        item.setOwner(userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")));
        return ItemMapper.mapItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        User owner = item.getOwner();
        if (!Objects.equals(owner.getId(), userId)) {
            throw new UserAccessException("Wrong user");
        } else {
            itemRepository.deleteById(itemId);
        }
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));

        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new UserAccessException("Wrong user");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return ItemMapper.mapItemToDto(itemRepository.save(item));
    }


    @Override
    public List<ItemDto> searchForItems(String text, int from, int size) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        if (size < 1 || from < 0) {
            throw new IllegalArgumentException("Wrong page number");
        }
        String query = text.toLowerCase();
        return itemRepository.findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(query)
                .stream()
                .map(ItemMapper::mapItemToDto)
                .collect(Collectors.toList());

    }


    public Booking getNextBooking(Item item) {
        LocalDateTime now = LocalDateTime.now();
        Optional<Booking> nextBooking = bookingRepository
                .findFirstByItemAndStartAfterAndStatusOrderByStartAsc(item.getId(), now, "APPROVED");
        return nextBooking.orElse(null);
    }


    public Booking getLastBooking(Item item) {
        LocalDateTime now = LocalDateTime.now();
        Optional<Booking> lastBooking = bookingRepository
                .findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(item.getId(), now, "APPROVED");
        return lastBooking.orElse(null);
    }


    @Override
    public CommentDto addNewComment(Long userId, Long itemId, CommentDto commentDto) {
        List<Booking> bookings = bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(userId);

        boolean hasPastBookings = bookings.stream()
                .anyMatch(booking -> booking.getItem().getId().equals(itemId) && booking.getEnd().isBefore(LocalDateTime.now()));

        if (hasPastBookings) {
            User author = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException("Item not found"));

            Comment comment = CommentMapper.toComment(commentDto);
            comment.setAuthor(author);
            comment.setItem(item);
            comment.setCreated(LocalDateTime.now());

            return CommentMapper.toDto(commentRepository.save(comment));
        } else {
            throw new ValidationException("No past bookings found for the user");
        }
    }

}
