package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;

@Component
public enum State {
    WAITING, CURRENT, FUTURE, REJECTED, CANCELLED
}
