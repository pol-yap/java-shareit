package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImplInMemory implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long nextItemId = 1;

    public Item create(Item item) {
        item.setId(nextItemId);
        items.put(nextItemId, item);
        nextItemId++;

        return item;
    }

    public Optional<Item> findById(Long id) {
        if (items.containsKey(id)) {
            return Optional.of(copyOf(items.get(id)));
        } else {
            return Optional.empty();
        }
    }

    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    public Item update(Long id, Item item) {
        items.put(id, item);

        return item;
    }

    public void deleteById(Long id) {
        items.remove(id);
    }

    public List<Item> findAllByOwner(Long id) {
        return findAll().stream()
                        .filter(i -> Objects.equals(i.getOwner(), id))
                        .collect(Collectors.toList());
    }

    public List<Item> search(String criteria) {
        return findAll().stream()
                        .filter(Item::getAvailable)
                        .filter(i -> isAppropriate(i, criteria.toLowerCase()))
                        .collect(Collectors.toList());
    }

    public boolean isExists(Long id) {
        return items.containsKey(id);
    }

    private Item copyOf(Item item) {
        Item copy = new Item();
        copy.setId(item.getId());
        copy.setName(item.getName());
        copy.setDescription(item.getDescription());
        copy.setAvailable(item.getAvailable());
        copy.setOwner(item.getOwner());
        copy.setRequest(item.getRequest());

        return copy;
    }

    private boolean isAppropriate(Item item, String criteria) {
        return item.getName().toLowerCase().contains(criteria) ||
                item.getDescription().toLowerCase().contains(criteria);
    }
}
