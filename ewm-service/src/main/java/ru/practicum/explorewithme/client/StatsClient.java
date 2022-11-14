package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.model.dto.EndpointHitDto;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        super(serverUrl);
    }

    public ResponseEntity<Object> sendStatistics(EndpointHitDto endpointHit) {
        return post("/hit", endpointHit);
    }
}
