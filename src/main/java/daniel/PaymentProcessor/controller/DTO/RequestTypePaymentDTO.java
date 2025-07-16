package daniel.PaymentProcessor.controller.DTO;

import daniel.PaymentProcessor.entities.TypePayment;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestTypePaymentDTO(
        TypePayment typePayment,
        UUID correlationId,
        BigDecimal amount
) {
}
