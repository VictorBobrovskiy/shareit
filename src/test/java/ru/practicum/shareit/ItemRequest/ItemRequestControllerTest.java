package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ItemRequestControllerTest {

    @Mock
    private ItemRequestService itemRequestService;

    private ItemRequestController itemRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemRequestController = new ItemRequestController(itemRequestService);
    }

    @Test
    void createItemRequest_shouldReturnCreatedStatusAndItemRequestDto() {
        // Mock data
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        ItemRequestDto createdItemRequestDto = new ItemRequestDto();
        ResponseEntity<ItemRequestDto> expectedResponse = new ResponseEntity<>(createdItemRequestDto, HttpStatus.CREATED);

        // Mock service method
        when(itemRequestService.createItemRequest(userId, itemRequestDto)).thenReturn(createdItemRequestDto);

        // Perform the controller method
        ResponseEntity<ItemRequestDto> actualResponse = itemRequestController.createItemRequest(userId, itemRequestDto);

        // Verify service method is called and response is correct
        verify(itemRequestService, times(1)).createItemRequest(userId, itemRequestDto);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getOwnItemRequests_shouldReturnListOfItemRequestDto() {
        // Mock data
        Long userId = 1L;
        List<ItemRequestDto> ownItemRequests = new ArrayList<>();
        ResponseEntity<List<ItemRequestDto>> expectedResponse = new ResponseEntity<>(ownItemRequests, HttpStatus.OK);

        // Mock service method
        when(itemRequestService.getOwnItemRequests(userId)).thenReturn(ownItemRequests);

        // Perform the controller method
        ResponseEntity<List<ItemRequestDto>> actualResponse = itemRequestController.getOwnItemRequests(userId);

        // Verify service method is called and response is correct
        verify(itemRequestService, times(1)).getOwnItemRequests(userId);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAllItemRequests_shouldReturnListOfItemRequestDto() {
        // Mock data
        Long userId = 1L;
        int from = 0;
        int size = 20;
        List<ItemRequestDto> allItemRequests = new ArrayList<>();
        ResponseEntity<List<ItemRequestDto>> expectedResponse = new ResponseEntity<>(allItemRequests, HttpStatus.OK);

        // Mock service method
        when(itemRequestService.getAllItemRequests(userId, from, size)).thenReturn(allItemRequests);

        // Perform the controller method
        ResponseEntity<List<ItemRequestDto>> actualResponse = itemRequestController.getAllItemRequests(userId, from, size);

        // Verify service method is called and response is correct
        verify(itemRequestService, times(1)).getAllItemRequests(userId, from, size);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getItemRequest_shouldReturnItemRequestDto() {
        // Mock data
        Long userId = 1L;
        long itemRequestId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        ResponseEntity<ItemRequestDto> expectedResponse = new ResponseEntity<>(itemRequestDto, HttpStatus.OK);

        // Mock service method
        when(itemRequestService.getItemRequest(userId, itemRequestId)).thenReturn(itemRequestDto);

        // Perform the controller method
        ResponseEntity<ItemRequestDto> actualResponse = itemRequestController.getItemRequest(userId, itemRequestId);

        // Verify service method is called and response is correct
        verify(itemRequestService, times(1)).getItemRequest(userId, itemRequestId);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
