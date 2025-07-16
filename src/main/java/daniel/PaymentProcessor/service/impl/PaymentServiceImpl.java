package daniel.PaymentProcessor.service.impl;

import daniel.PaymentProcessor.component.PaymentProcessHandler;
import daniel.PaymentProcessor.controller.DTO.RequestTypePaymentDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.repository.PaymentRepository;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProcessHandler payProcHandler;

    @Override
    public void processPayment(Payment payment) {
        boolean success = payProcHandler.callProcess(
                new RequestTypePaymentDTO(
                        payProcHandler.getTypePayment(),
                        payment.getCorrelationId(),
                        payment.getAmount()
                )
        );

        if (success) {
            paymentRepository.save(payment);
        } else {
            System.out.println("Passar para o fallback ou default");
        }
    }

}
