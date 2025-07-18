package daniel.PaymentProcessor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private UUID correlationId;
    private BigDecimal amount;
    private TypePayment typePayment;
    private Instant requestedAt;
}
