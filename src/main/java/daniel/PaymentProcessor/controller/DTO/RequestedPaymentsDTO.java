package daniel.PaymentProcessor.controller.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RequestedPaymentsDTO(
        @NotNull
        UUID correlationId,

        @NotNull
        @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
        @Digits(integer = 12, fraction = 2, message = "Amount must have up to 12 digits and 2 decimal places")
        BigDecimal amount,

        Instant requestedAt
) {
}
