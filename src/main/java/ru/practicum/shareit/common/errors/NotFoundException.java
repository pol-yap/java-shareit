package ru.practicum.shareit.common.errors;

public class NotFoundException extends RuntimeException {
    private final long id;
    private final String entity;

    public NotFoundException(long id, String entity) {
        this.id = id;
        this.entity = entity;
    }

    public long getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }
}
