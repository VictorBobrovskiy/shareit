package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    public List<ItemDto> getItems(Long userId) {
        List<Item> items = itemRepository.findAllItemsByOwnerId(userId);
        List<ItemDto> itemDtoList =  items.stream().map(ItemMapper::mapItemToDto).collect(Collectors.toList());

        for (ItemDto itemDto : itemDtoList) {
            Item item = ItemMapper.mapDtoToItem(itemDto);
            Booking lastBooking = getLastBooking(item);
            if (lastBooking != null) {
                itemDto.setLastBooking(BookingMapper.toDto(lastBooking));
            }
            Booking nextBooking = getNextBooking(item);
            if (nextBooking != null) {
                itemDto.setNextBooking(BookingMapper.toDto(nextBooking));
            }
        }
        Collections.sort(itemDtoList);
        return itemDtoList;
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item not found"));
        ItemDto itemDto = ItemMapper.mapItemToDto(item);
        if (item.getOwner().getId() == userId) {
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
        if (owner.getId() != userId) {
            throw new UserNotFoundException("Wrong user");
        } else {
            itemRepository.deleteById(itemId);
        }
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        User owner = item.getOwner();
        if (owner.getId() != userId) {
            throw new UserNotFoundException("Wrong user");
        }
        if (itemDto.getName() != null) {
        item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        } else {
            itemDto.setId(itemId);
            item.setOwner(owner);
        }
        return ItemMapper.mapItemToDto(itemRepository.save(item));
    }



    @Override
    public List<ItemDto> searchForItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(text)
                .stream()
                .map(ItemMapper::mapItemToDto)
                .collect(Collectors.toList());

    }

    @Override
    public Booking getNextBooking(Item item) {
        List<Booking> bookings = bookingRepository.findByItemOrderByStartAsc(item);
        LocalDateTime now = LocalDateTime.now();
        Booking nextBooking = null;
        for (Booking booking : bookings) {
            if (booking.getStart().isAfter(now)) {
                nextBooking = booking;
                break;
            }
        }
        return nextBooking;
    }

    @Override
    public Booking getLastBooking(Item item) {
        List<Booking> bookings = bookingRepository.findByItemOrderByStartDesc(item);
        LocalDateTime now = LocalDateTime.now();
        Booking nextBooking = null;
        for (Booking booking : bookings) {
            if (booking.getStart().isBefore(now)) {
                nextBooking = booking;
                break;
            }
        }

        return nextBooking;
    }

    @Override
    public CommentDto addNewComment(Long userId, CommentDto commentDto) {
        Comment comment = CommentMapper.toComment(commentDto);
        User author = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        comment.setAuthor(author);
        return  CommentMapper.toDto(commentRepository.save(comment));
    }

}
