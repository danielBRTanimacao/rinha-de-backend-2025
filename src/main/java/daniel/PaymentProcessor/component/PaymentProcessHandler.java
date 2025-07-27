package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.RequestTypePaymentDTO;
import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.controller.DTO.RequestedPaymentsDTO;
import daniel.PaymentProcessor.entities.TypePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public TypePayment getTypePayment() {
        ResponseHealthDTO defaultHealth = healthCache.getHealth(TypePayment.DEFAULT);
        ResponseHealthDTO fallbackHealth = healthCache.getHealth(TypePayment.FALLBACK);

        if (!defaultHealth.failing()) return TypePayment.DEFAULT;
        if (!fallbackHealth.failing()) return TypePayment.FALLBACK;
        throw new RuntimeException("Process downs");
    }

    public boolean callProcess(RequestTypePaymentDTO reqDto) {
        String url = reqDto.typePayment().equals(TypePayment.DEFAULT) ? defaultUrl : fallbackUrl;

        try {
            RequestedPaymentsDTO rePayment = new RequestedPaymentsDTO(
                    reqDto.correlationId(),
                    reqDto.amount(),
                    Instant.now()
            );
            return Optional.ofNullable(
                    webClient.post()
                            .uri(url)
                            .bodyValue(rePayment)
                            .retrieve()
                            .toEntity(String.class)
                            .block()
            ).map(ResponseEntity::getStatusCode)
                    .map(statuscode -> HttpStatus.valueOf(statuscode.value()))
                    .map(HttpStatus::is2xxSuccessful)
                    .orElse(false);

        } catch (Exception e) {
            return false;
        }

    }
}
