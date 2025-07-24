package daniel.PaymentProcessor.controller;

import daniel.PaymentProcessor.controller.DTO.requestsPaymentsDTOs.*;
import daniel.PaymentProcessor.controller.DTO.ResponseSummaryDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.entities.TypePayment;
import daniel.PaymentProcessor.mapper.PaymentMapper;
import daniel.PaymentProcessor.service.PaymentService;
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
    private final PaymentService paymentService;
    private final PaymentMapper payMapper;

    @PostMapping("/payments")
    public ResponseEntity<Void> createPayment(@Valid @RequestBody RequestPrincipalPaymentDTO paymentDTO) {
        Payment reqPayment = payMapper.toEntity(paymentDTO);
        reqPayment.setTypePayment(TypePayment.DEFAULT);

        paymentService.processPayment(reqPayment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/payments-summary")
    public ResponseSummaryDTO getSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        return paymentService.getSummary(from, to);
    }

}
