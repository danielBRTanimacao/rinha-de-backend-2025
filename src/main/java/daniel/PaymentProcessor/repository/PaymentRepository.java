package daniel.PaymentProcessor.repository;

import daniel.PaymentProcessor.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final JdbcTemplate jdbc;

    // concertar aqui estudar lib JDBC e inserir valores corretos
    public void save(Payment payment) {
        jdbc.update("""
                INSERT INTO payments (correlation_id, amount, type_payment, related_at) VALUES (?, ?, ?, ?)
                """, payment.getCorrelationId(), payment.getAmount(), payment.getTypePayment(), Instant.now());
    }
}
