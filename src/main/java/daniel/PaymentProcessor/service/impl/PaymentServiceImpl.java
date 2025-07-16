package daniel.PaymentProcessor.service.impl;

import daniel.PaymentProcessor.component.HealthCache;
import daniel.PaymentProcessor.controller.DTO.ResponseHealthDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.entities.TypePayment;
import daniel.PaymentProcessor.repository.PaymentRepository;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final HealthCache healthCache;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Override
    public void processPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    private TypePayment getTypePayment() {
        ResponseHealthDTO defaultHealth = healthCache.getHealth(TypePayment.DEFAULT);
        ResponseHealthDTO fallbackHealth = healthCache.getHealth(TypePayment.FALLBACK);

        return !defaultHealth.failing() ? TypePayment.DEFAULT : TypePayment.FALLBACK;
        throw new RuntimeException("Process downs"); // lançar erro e mudar a logica!
    }
}
