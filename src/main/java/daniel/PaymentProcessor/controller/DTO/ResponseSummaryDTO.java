package daniel.PaymentProcessor.controller.DTO;

import java.math.BigDecimal;

public record ResponseSummaryDTO(
        Long totalRequests,
        BigDecimal totalAmount
) {
}
