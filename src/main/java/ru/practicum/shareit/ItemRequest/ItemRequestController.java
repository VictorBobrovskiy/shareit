package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;
    private final String user = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<ItemRequestDto> createItemRequest(
            @RequestHeader(user) Long userId,
            @RequestBody @Valid ItemRequestDto itemRequestDto
    ) {
        ItemRequestDto itemRequestDtoCreated = itemRequestService.createItemRequest(userId, itemRequestDto);
        return new ResponseEntity<>(itemRequestDtoCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getOwnItemRequests(@RequestHeader(user) Long userId) {

        List<ItemRequestDto> ownItemRequests = itemRequestService.getOwnItemRequests(userId);

        return new ResponseEntity<>(ownItemRequests, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllItemRequests(
            @RequestHeader(user) Long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<ItemRequestDto> allItemRequests = itemRequestService.getAllItemRequests(userId, from, size);

        return new ResponseEntity<>(allItemRequests, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getItemRequest(
            @RequestHeader(user) Long userId,
            @PathVariable("requestId") long ItemRequestId
    ) {
        ItemRequestDto itemRequestDto = itemRequestService.getItemRequest(userId, ItemRequestId);
        return new ResponseEntity<>(itemRequestDto, HttpStatus.OK);
    }
}
