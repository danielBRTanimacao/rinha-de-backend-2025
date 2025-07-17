package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.entities.TypePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HealthCache {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, ResponseHealthDTO> cache = new ConcurrentHashMap<>();

    @Value("${spring.payment.default.url}")
    private static String DEFAULT_URL;
    @Value("${spring.payment.fallback.url}")
    private static String FALLBACK_URL;


    @Scheduled(fixedRate = 5000)
    public void updateHealthCache() {
        updateServiceHealth(TypePayment.DEFAULT, DEFAULT_URL);
        updateServiceHealth(TypePayment.FALLBACK, FALLBACK_URL);
    }

    private void updateServiceHealth(TypePayment processor, String url) {
        try {
            ResponseHealthDTO healthDto = restTemplate.getForObject(url, ResponseHealthDTO.class);
            cache.put(processor.toString(), healthDto);
        } catch (Exception e) {
            cache.put(processor.toString(), new ResponseHealthDTO(true, 1000));
        }
    }

    public ResponseHealthDTO getHealth(TypePayment processor) {
        return cache.getOrDefault(processor.toString(), new ResponseHealthDTO(true, 1000));
    }
}
