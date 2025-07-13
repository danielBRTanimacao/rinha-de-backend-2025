package daniel.PaymentProcessor.service.impl;

import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.repository.PaymentRepository;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
