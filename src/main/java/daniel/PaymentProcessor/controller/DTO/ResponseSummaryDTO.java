package daniel.PaymentProcessor.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public record ResponseSummaryDTO(
        ProcessorSummary defaultCall,
        ProcessorSummary fallback
) {
    @Getter
    @Setter
    public static class ProcessorSummary {
        private long totalRequests;
        private BigDecimal totalAmount;
    }
}
