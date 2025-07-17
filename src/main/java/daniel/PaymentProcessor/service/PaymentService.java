package daniel.PaymentProcessor.service;

import daniel.PaymentProcessor.controller.DTO.ResponseSummaryDTO;
import daniel.PaymentProcessor.entities.Payment;

import java.time.Instant;

public interface PaymentService {
    void processPayment(Payment payment);
    ResponseSummaryDTO getSummary(Instant from, Instant to);
}
