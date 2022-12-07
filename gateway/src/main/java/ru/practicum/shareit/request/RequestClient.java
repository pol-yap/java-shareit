package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;

import java.util.Map;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder, serverUrl + API_PREFIX);
    }

    public ResponseEntity<Object> create(Long userId, ItemRequestDtoCreate dto) {
        return post("", userId, dto);
    }

    public ResponseEntity<Object> getOwn(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAll(Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> findById(Long userId, Long requestId) {
        return get("/" + requestId, userId);
    }
}
