package daniel.PaymentProcessor.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private UUID correlationId;
    private BigDecimal amount;
    private TypePayment typePayment;
    // qual processo e horario feito do processo
}
