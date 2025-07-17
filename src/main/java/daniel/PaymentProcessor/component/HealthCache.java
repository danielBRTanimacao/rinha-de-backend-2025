package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.entities.TypePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class HealthCache {
    private final WebClient webClient;
    private final Map<String, ResponseHealthDTO> cache = new ConcurrentHashMap<>();

    @Value("${spring.payment.default.url}")
    private String defaultUrl;
    @Value("${spring.payment.fallback.url}")
    private String fallbackUrl;


    @Scheduled(fixedRateString = "${health.cache.refresh.ms:5000}")
    public void updateHealthCache() {
        updateServiceHealth(TypePayment.DEFAULT, defaultUrl);
        updateServiceHealth(TypePayment.FALLBACK, fallbackUrl);
    }

    private void updateServiceHealth(TypePayment processor, String url) {
        try {
            ResponseHealthDTO healthDto = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ResponseHealthDTO.class).block(Duration.ofMillis(500));
            cache.put(processor.toString(), healthDto);
        } catch (Exception e) {
            cache.put(processor.toString(), new ResponseHealthDTO(true, 1000));
        }
    }

    public ResponseHealthDTO getHealth(TypePayment processor) {
        return cache.getOrDefault(processor.toString(), new ResponseHealthDTO(true, 1000));
    }
}
