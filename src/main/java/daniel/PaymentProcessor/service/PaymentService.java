package daniel.PaymentProcessor.service;

import daniel.PaymentProcessor.entities.Payment;

public interface PaymentService {
    void processPayment(Payment payment);
}
