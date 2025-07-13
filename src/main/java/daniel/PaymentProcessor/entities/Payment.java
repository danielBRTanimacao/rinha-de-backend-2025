package daniel.PaymentProcessor.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private UUID correlationId;
    private BigDecimal amount;
    private TypePayment typePayment;
    private Instant requestedAt;
}
