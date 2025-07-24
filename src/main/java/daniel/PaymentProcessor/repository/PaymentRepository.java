package daniel.PaymentProcessor.repository;

import daniel.PaymentProcessor.controller.DTO.ResponseSummaryDTO;
import daniel.PaymentProcessor.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final JdbcTemplate jdbc;

    public void save(Payment payment) {
        jdbc.update("""
        INSERT INTO payments (correlation_id, amount, type_payment, requested_at)
        VALUES (?, ?, ?, ?)
        """, payment.getCorrelationId(), payment.getAmount(), payment.getTypePayment().name(), Instant.now());

    }

    public ResponseSummaryDTO.ProcessorSummary getProcessorSummary(
            String processor,
            Instant from,
            Instant to
    ) {
        String sql = """
            SELECT 
                COUNT(*) AS total_requests,
                COALESCE(SUM(amount), 0) AS total_amount
            FROM payments
            WHERE type_payment = ? 
            AND requested_at BETWEEN ? AND ?
            """;

        if (from == null) {
            from = Instant.EPOCH;
        }
        if (to == null) {
            to = Instant.now();
        }

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            ResponseSummaryDTO.ProcessorSummary summary = new ResponseSummaryDTO.ProcessorSummary();
            summary.setTotalRequests(rs.getLong("total_requests"));
            summary.setTotalAmount(rs.getBigDecimal("total_amount"));
            return summary;
        }, processor, Timestamp.from(from), Timestamp.from(to));
    }
}