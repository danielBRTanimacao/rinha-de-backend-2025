package daniel.PaymentProcessor.controller.DTO;

import daniel.PaymentProcessor.entities.TypePayment;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestTypePaymentDTO(
        @NotNull
        TypePayment typePayment,
        @NotNull
        UUID correlationId,
        @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
        @Digits(integer = 12, fraction = 2, message = "Amount must have up to 12 digits and 2 decimal places")
        BigDecimal amount
) {
}
