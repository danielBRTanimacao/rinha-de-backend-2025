package daniel.PaymentProcessor.controller;

import daniel.PaymentProcessor.controller.DTO.RequestPaymentDTO;
import daniel.PaymentProcessor.controller.DTO.ResponseSummaryDTO;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

public interface PaymentController {
    @PostMapping("/payments")
    ResponseEntity<Void> createPayment(@Valid @RequestBody RequestPaymentDTO paymentDTO);

    @GetMapping("/payments-summary")
    ResponseSummaryDTO getSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    );
}
