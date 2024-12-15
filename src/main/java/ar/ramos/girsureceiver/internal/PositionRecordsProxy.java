package ar.ramos.girsureceiver.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PositionRecordsProxy {

    private final String API_URL;
    private final RestTemplate restTemplate;
    private final String ENCODED_AUTH;

    public PositionRecordsProxy(RestTemplate restTemplate,
                                @Value("${API_URL}") String API_URL,
                                @Value("${ENCODED_AUTH}") String ENCODED_AUTH

    ) {
        this.restTemplate = restTemplate;
        this.API_URL = API_URL;
        this.ENCODED_AUTH = ENCODED_AUTH;
    }

    @PostMapping("/positionRecords")
    public ResponseEntity<PositionRecord> save(@RequestBody PositionRecord record) {
        HttpHeaders headers = new HttpHeaders();
        String authHeader = "Basic " + ENCODED_AUTH;
        headers.set("Authorization", authHeader);

        HttpEntity<PositionRecord> request = new HttpEntity<>(record, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL + "/positionRecords", HttpMethod.POST, request, String.class);
        if (response.getStatusCode().isError()) {
            return ResponseEntity.status(response.getStatusCode()).build();
        } else if (response.getStatusCode().value() == 201) {
            return ResponseEntity.created(response.getHeaders().getLocation()).build();
        }
        return ResponseEntity.ok().build();
    }

}
