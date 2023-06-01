package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    public List<ItemDto> getItems(Long userId) {
        List<Item> items = itemRepository.findAllItemsByOwnerId(userId);
        Set<ItemDto> itemDtoSet = new TreeSet<>();
        itemDtoSet = items.stream().map(ItemMapper::mapItemToDto).collect(Collectors.toSet());

        for (ItemDto itemDto : itemDtoSet) {
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
        return new ArrayList<>(itemDtoSet);
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
        List<Booking> bookings = bookingRepository.findByItemOrderByStartAsc(item);
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

}
