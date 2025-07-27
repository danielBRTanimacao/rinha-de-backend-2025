package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.*;
import daniel.PaymentProcessor.entities.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentProcessHandler {
    private final HealthCache healthCache;
    private final WebClient webClient;

    @Value("${spring.payment.default.url}")
    private String defaultUrl;
    @Value("${spring.payment.fallback.url}")
    private String fallbackUrl;

    @Getter
    private TypePayment lastUsedProcessor;

    public boolean callProcess(Payment payment) {
        TypePayment processorToUse;
        try {
            processorToUse = getTypePayment();
        } catch (RuntimeException e) {
            return false;
        }

        String url = (processorToUse == TypePayment.DEFAULT) ? defaultUrl : fallbackUrl;
        lastUsedProcessor = processorToUse;

        try {
            RequestedPaymentsDTO dto = new RequestedPaymentsDTO(
                    payment.getCorrelationId(),
                    payment.getAmount(),
                    Instant.now()
            );

            return Optional.ofNullable(
                            webClient.post()
                                    .uri(url)
                                    .bodyValue(dto)
                                    .retrieve()
                                    .toEntity(String.class)
                                    .block()
                    ).map(ResponseEntity::getStatusCode)
                    .map(HttpStatusCode::is2xxSuccessful)
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    public TypePayment getTypePayment() {
        ResponseHealthDTO defaultHealth = healthCache.getHealth(TypePayment.DEFAULT);
        ResponseHealthDTO fallbackHealth = healthCache.getHealth(TypePayment.FALLBACK);

        if (!defaultHealth.failing()) return TypePayment.DEFAULT;
        if (!fallbackHealth.failing()) return TypePayment.FALLBACK;
        throw new RuntimeException("Ambos os processadores estão com falha");
    }
}
