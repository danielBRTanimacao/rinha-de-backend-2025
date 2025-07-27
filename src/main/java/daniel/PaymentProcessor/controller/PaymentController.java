package daniel.PaymentProcessor.controller;

import daniel.PaymentProcessor.component.PaymentProcessHandler;
import daniel.PaymentProcessor.controller.DTO.*;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.mapper.PaymentMapper;
import daniel.PaymentProcessor.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentMapper payMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentProcessHandler payProcHandler;

    @PostMapping("/payments")
    public ResponseEntity<Void> createPayment(@Valid @RequestBody RequestedPaymentsDTO paymentDTO) {
        Payment payment = payMapper.toEntity(paymentDTO);

        boolean success = payProcHandler.callProcess(payment);

        if (success) {
            payment.setTypePayment(payProcHandler.getLastUsedProcessor());
            paymentRepository.save(payment);
        } else {
            System.out.println("Ambos os processadores estão indisponíveis");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/payments-summary")
    public ResponseSummaryDTO getSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {

        return new ResponseSummaryDTO(
                paymentRepository.getProcessorSummary("default", from, to),
                paymentRepository.getProcessorSummary("fallback", from, to)
        );
    }
}
