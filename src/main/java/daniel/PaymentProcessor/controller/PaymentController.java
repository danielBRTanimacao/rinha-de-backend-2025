package daniel.PaymentProcessor.controller;

import daniel.PaymentProcessor.controller.DTO.RequestPaymentDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentController {
    @PostMapping("/payments")
    ResponseEntity<Void> createPayment(@Valid @RequestBody RequestPaymentDTO paymentDTO);

    @GetMapping("/payments-summary")
    String getPaymentSummary();
}
