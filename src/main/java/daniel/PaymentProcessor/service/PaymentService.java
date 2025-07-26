package daniel.PaymentProcessor.service;

import daniel.PaymentProcessor.component.PaymentProcessHandler;
import daniel.PaymentProcessor.controller.DTO.RequestTypePaymentDTO;
import daniel.PaymentProcessor.controller.DTO.ResponseSummaryDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProcessHandler payProcHandler;

    public void processPayment(Payment payment) {
        boolean success = payProcHandler.callProcess(
                type,
                payment.getCorrelationId(),
                payment.getAmount(),
                Instant.now()
        );

        if (success) {
            payment.setTypePayment(payProcHandler.getTypePayment());
            paymentRepository.save(payment);
        } else {
            System.out.println("Passar para o fallback ou default");
        }
    }

    public ResponseSummaryDTO getSummary(Instant from, Instant to) {
        return new ResponseSummaryDTO(
                paymentRepository.getProcessorSummary("default", from, to),
                paymentRepository.getProcessorSummary("fallback", from, to)
        );
    }
}
