package daniel.PaymentProcessor.controller.DTO;

import lombok.Data;

import java.math.BigDecimal;

public record ResponseSummaryDTO(
        ProcessorSummary defaultCall,
        ProcessorSummary fallback
) {
    @Data
    public static class ProcessorSummary {
        private long totalRequests;
        private BigDecimal totalAmount;
    }
}
