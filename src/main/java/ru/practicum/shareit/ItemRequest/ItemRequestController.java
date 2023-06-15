package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
        ItemRequestDto itemRequestDto2 = new ModelMapper().map(itemRequestService.createItemRequest(userId, itemRequestDto), ItemRequestDto.class);
        return new ResponseEntity<>(itemRequestDto2, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getOwnItemRequests(@RequestHeader(user) Long userId) {

        List<ItemRequestDto> ownItemRequests = itemRequestService.getOwnItemRequests(userId)
                .stream()
                .map(itemRequest -> new ModelMapper().map(itemRequest, ItemRequestDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(ownItemRequests, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllItemRequests(
            @RequestHeader(user) Long userId,
            @RequestParam(defaultValue = "0")  int from,
            @RequestParam(defaultValue = "20")  int size
    )  {
        List<ItemRequestDto> allItemRequests = itemRequestService.getAllItemRequests(from, size)
                .stream()
                .map(itemRequest -> new ModelMapper().map(itemRequest, ItemRequestDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(allItemRequests, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getItemRequest(
            @RequestHeader(user) Long userId,
            @PathVariable("requestId") long ItemRequestId
    )   {
        ItemRequestDto itemRequestDto = new ModelMapper().map(itemRequestService.getItemRequest(ItemRequestId), ItemRequestDto.class);
        return new ResponseEntity<>(itemRequestDto, HttpStatus.OK);
    }
}
