package ru.practicum.shareit.item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.ItemRequest.ItemRequest;

public class ItemMapperTest {

    @Test
    public void testMapDtoToItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Item 1");
        itemDto.setDescription("Description 1");
        itemDto.setAvailable(true);
        itemDto.setRequestId(10L);

        Item item = ItemMapper.mapDtoToItem(itemDto);

        Assertions.assertEquals(itemDto.getId(), item.getId());
        Assertions.assertEquals(itemDto.getName(), item.getName());
        Assertions.assertEquals(itemDto.getDescription(), item.getDescription());
        Assertions.assertEquals(itemDto.getAvailable(), item.getAvailable());
        Assertions.assertEquals(itemDto.getRequestId(), item.getRequest().getId());
    }

    @Test
    public void testMapItemToDto() {
        Item item = new Item();
        item.setId(2L);
        item.setName("Item 2");
        item.setDescription("Description 2");
        item.setAvailable(false);
        ItemRequest request = new ItemRequest(20L);
        item.setRequest(request);

        ItemDto itemDto = ItemMapper.mapItemToDto(item);

        Assertions.assertEquals(item.getId(), itemDto.getId());
        Assertions.assertEquals(item.getName(), itemDto.getName());
        Assertions.assertEquals(item.getDescription(), itemDto.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemDto.getAvailable());
        Assertions.assertEquals(item.getRequest().getId(), itemDto.getRequestId());
    }
}
