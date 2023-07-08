package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    private final BookingService bookingService = mock(BookingService.class);

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final BookingController bookingController = new BookingController(bookingService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

    @Test
    public void testAddNewBooking() throws Exception {
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        Long userId = 1L;
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setStart(LocalDateTime.of(2023, 7, 25, 12, 59));
        bookingDto.setEnd(LocalDateTime.of(2023, 7, 27, 0, 0));
        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setAvailable(true);
        User owner = new User();
        item.setOwner(owner);
        User booker = new User(userId);
        bookingDto.setItemId(item.getId());
        bookingDto.setBookerId(userId);

        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setId(userId);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(Status.APPROVED);
        when(bookingService.addNewBooking(userId, bookingDto)).thenReturn(booking);

        String jsonContent = objectWriter.writeValueAsString(bookingDto);


        // Perform the POST request
        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(booking.getId()));

    }

    @Test
    public void testApproveBooking() throws Exception {
        // Prepare test data
        Long userId = 1L;
        Long bookingId = 2L;
        boolean approved = true;

        // Configure mock behavior
        Booking booking = new Booking();
        when(bookingService.approveBooking(eq(userId), eq(bookingId), eq(approved))).thenReturn(booking);

        // Perform the PATCH request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/bookings/{id}", bookingId)
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .param("approved", String.valueOf(approved));

        // Assert the response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(booking.getId()));
    }

    @Test
    public void testGetBooking() throws Exception {
        // Prepare test data
        Long userId = 1L;
        Long bookingId = 2L;

        // Configure mock behavior
        Booking booking = new Booking();
        when(bookingService.getBooking(eq(userId), eq(bookingId))).thenReturn(booking);

        // Perform the GET request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{id}", bookingId)
                .header("X-Sharer-User-Id", String.valueOf(userId));

        // Assert the response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(booking.getId()));
    }

    @Test
    public void testGetAllByBookerId() throws Exception {
        // Prepare test data
        Long bookerId = 1L;
        String state = "ALL";
        int from = 0;
        int size = 20;

        // Configure mock behavior
        List<Booking> bookings = Collections.singletonList(new Booking());
        when(bookingService.getAllByBookerId(eq(bookerId), eq(state), eq(from), eq(size))).thenReturn(bookings);

        // Perform the GET request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings")
                .header("X-Sharer-User-Id", String.valueOf(bookerId))
                .param("state", state)
                .param("from", String.valueOf(from))
                .param("size", String.valueOf(size));

        // Assert the response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bookings.get(0).getId()));
    }

    @Test
    public void testGetAllByOwnerId() throws Exception {
        // Prepare test data
        Long ownerId = 1L;
        String state = "ALL";
        int from = 0;
        int size = 20;

        // Configure mock behavior
        List<Booking> bookings = Collections.singletonList(new Booking());
        when(bookingService.getAllByOwnerId(eq(ownerId), eq(state), eq(from), eq(size))).thenReturn(bookings);

        // Perform the GET request
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/owner")
                .header("X-Sharer-User-Id", String.valueOf(ownerId))
                .param("state", state)
                .param("from", String.valueOf(from))
                .param("size", String.valueOf(size));

        // Assert the response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bookings.get(0).getId()));
    }
}
