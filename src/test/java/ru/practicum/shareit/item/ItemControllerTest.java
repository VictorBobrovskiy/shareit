package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @MockBean
    private ItemServiceImpl itemService;

    @InjectMocks
    private ItemController itemController;


    @Autowired
    private MockMvc mockMvc;

    private Long requestId;

    private BookingDto lastBooking;

    private BookingDto nextBooking;

    private CommentDto comment;

    private List<CommentDto> comments;
    private ItemDto itemDto;

    @Autowired
    public ItemControllerTest(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        requestId = 1L;
        lastBooking = new BookingDto();
        nextBooking = new BookingDto();
        comment = new CommentDto();
        comments = new ArrayList<>();
        comments.add(comment);
        itemDto = new ItemDto(1L, "Plasma TV", "Awesome plasma",
                true, requestId, lastBooking, nextBooking, comments);

    }

    @SneakyThrows
    @Test
    public void testGetAll() {
        Long userId = 1L;
        int from = 0;
        int size = 20;
        List<ItemDto> items = Arrays.asList(
                new ItemDto(1L, "Item 1"),
                new ItemDto(2L, "Item 2")
        );
        when(itemService.getItems(userId, from, size)).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .header("X-Sharer-User-Id", userId)
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(items.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(items.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(items.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(items.get(1).getName()))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void testGetItem() {
        Long userId = 1L;
        Long itemId = 1L;
        when(itemService.getItem(userId, itemId)).thenReturn(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(itemDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(itemDto.getName()))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void testAddNewItem() {
        Long userId = 1L;

        when(itemService.addNewItem(userId, itemDto)).thenReturn(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(itemDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(itemDto.getName()))
                .andDo(print());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    @Test
    public void testUpdate() {
        Long userId = 1L;
        Long itemId = 1L;

        when(itemService.updateItem(userId, itemId, itemDto)).thenReturn(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content("{\"name\":\"Plasma TV\"}"))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(itemDto.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(itemDto.getName()))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void testDeleteItem() {
        Long userId = 1L;
        Long itemId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void testSearchForItems() {
        String searchText = "test";
        int from = 0;
        int size = 20;
        List<ItemDto> items = Arrays.asList(
                new ItemDto(1L, "Item 1"),
                new ItemDto(2L, "Item 2")
        );
        when(itemService.searchForItems(searchText, from, size)).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/items/search")
                        .param("text", searchText)
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(items.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(items.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(items.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(items.get(1).getName()))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void testAddNewComment() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto(1L, "New Comment");
//        CommentDto createdComment = new CommentDto(1L, "New Comment");
        User author = new User(userId);
        author.setName("Author");
        commentDto.setAuthorName(author.getName());
        commentDto.setItemId(itemId);
        commentDto.setCreated(LocalDateTime.now());
        when(itemService.addNewComment(userId, itemId, commentDto)).thenReturn(commentDto);

        mockMvc.perform(post("/items/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content("{\"text\":\"New Comment\"}"))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentDto.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(commentDto.getText()))
                .andDo(print());
    }

}



