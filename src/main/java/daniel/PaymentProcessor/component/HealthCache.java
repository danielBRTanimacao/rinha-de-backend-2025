package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HealthCache {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, ResponseHealthDTO> cache = new ConcurrentHashMap<>();
}
