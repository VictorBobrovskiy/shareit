package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;
    private static final String USER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<ItemRequestDto> createItemRequest(
            @RequestHeader(USER) Long userId,
            @RequestBody @Valid ItemRequestDto itemRequestDto
    ) {
        ItemRequestDto itemRequestDtoCreated = itemRequestService.createItemRequest(userId, itemRequestDto);
        log.debug("Create New ItemRequest requested");
        return new ResponseEntity<>(itemRequestDtoCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getOwnItemRequests(@RequestHeader(USER) Long userId) {

        List<ItemRequestDto> ownItemRequests = itemRequestService.getOwnItemRequests(userId);
        log.debug("Own ItemRequests requested by user " + userId);
        return new ResponseEntity<>(ownItemRequests, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllItemRequests(
            @RequestHeader(USER) Long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<ItemRequestDto> allItemRequests = itemRequestService.getAllItemRequests(userId, from, size);
        log.debug("All ItemRequests requested by user " + userId);
        return new ResponseEntity<>(allItemRequests, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getItemRequest(
            @RequestHeader(USER) Long userId,
            @PathVariable("requestId") long itemRequestId
    ) {
        ItemRequestDto itemRequestDto = itemRequestService.getItemRequest(userId, itemRequestId);
        log.debug("ItemRequest with id " + itemRequestId + " requested by user " + userId);
        return new ResponseEntity<>(itemRequestDto, HttpStatus.OK);
    }
}
