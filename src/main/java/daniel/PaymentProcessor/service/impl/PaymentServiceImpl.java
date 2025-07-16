package daniel.PaymentProcessor.service.impl;

import daniel.PaymentProcessor.component.HealthCache;
import daniel.PaymentProcessor.controller.DTO.RequestPaymentDTO;
import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.entities.TypePayment;
import daniel.PaymentProcessor.repository.PaymentRepository;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final HealthCache healthCache;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.payment.default.url}")
    private static String DEFAULT_URL;
    @Value("${spring.payment.fallback.url}")
    private static String FALLBACK_URL;

    @Override
    public void processPayment(Payment payment) {
        TypePayment processor = getTypePayment();
        boolean sucess = callProcess(processor, payment.getCorrelationId(), payment.getAmount());

        if (sucess) {
            paymentRepository.save(payment);
        } else {
            System.out.println("Passar para o fallback ou default");
        }
    }

    private TypePayment getTypePayment() {
        ResponseHealthDTO defaultHealth = healthCache.getHealth(TypePayment.DEFAULT);
        ResponseHealthDTO fallbackHealth = healthCache.getHealth(TypePayment.FALLBACK);

        if (!defaultHealth.failing()) return TypePayment.DEFAULT;
        if (!fallbackHealth.failing()) return TypePayment.FALLBACK;
        throw new RuntimeException("Process downs"); // lançar erro e mudar a logica!
    }

    private boolean callProcess(TypePayment typePayment, UUID correlationId, BigDecimal amount) {
        String url = typePayment.equals(TypePayment.DEFAULT) ? DEFAULT_URL : FALLBACK_URL;

        try {
            RequestPaymentDTO rePayment = new RequestPaymentDTO(correlationId, amount);
            ResponseEntity<String> res = restTemplate.postForEntity(url, rePayment, String.class);
            return res.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }

    }
}
