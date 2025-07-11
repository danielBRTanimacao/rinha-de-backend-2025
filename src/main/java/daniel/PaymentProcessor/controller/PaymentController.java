package daniel.PaymentProcessor.controller;

import org.springframework.web.bind.annotation.GetMapping;

public interface PaymentController {
    @GetMapping("/payments")
    String createPayment();

    @GetMapping("/payments-summary")
    String getPaymentSummary();
}
