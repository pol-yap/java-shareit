package ru.practicum.shareit.common.errors;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException (String message) {
        super(message);
    }
}
