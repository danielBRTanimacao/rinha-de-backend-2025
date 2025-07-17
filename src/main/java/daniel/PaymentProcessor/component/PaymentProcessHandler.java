package daniel.PaymentProcessor.component;

import daniel.PaymentProcessor.controller.DTO.RequestPaymentDTO;
import daniel.PaymentProcessor.controller.DTO.RequestTypePaymentDTO;
import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.entities.TypePayment;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentProcessHandler {
    private final HealthCache healthCache;
    // Pesquisar sobre webClient e sobre o bean webCLient e RestTemplate
    private RestTemplate restTemplate;

    @Value("${spring.payment.default.url}")
    private static String DEFAULT_URL;
    @Value("${spring.payment.fallback.url}")
    private static String FALLBACK_URL;

    public TypePayment getTypePayment() {
        ResponseHealthDTO defaultHealth = healthCache.getHealth(TypePayment.DEFAULT);
        ResponseHealthDTO fallbackHealth = healthCache.getHealth(TypePayment.FALLBACK);

        if (!defaultHealth.failing()) return TypePayment.DEFAULT;
        if (!fallbackHealth.failing()) return TypePayment.FALLBACK;
        throw new RuntimeException("Process downs"); // lançar erro e mudar a logica!
    }

    public boolean callProcess(RequestTypePaymentDTO reqDto) {
        String url = reqDto.typePayment().equals(TypePayment.DEFAULT) ? DEFAULT_URL : FALLBACK_URL;

        try {
            RequestPaymentDTO rePayment = new RequestPaymentDTO(reqDto.correlationId(), reqDto.amount());
            ResponseEntity<String> res = restTemplate.postForEntity(url, rePayment, String.class);
            return res.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }

    }
}
