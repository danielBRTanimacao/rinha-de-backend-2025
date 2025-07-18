package daniel.PaymentProcessor.repository;

import daniel.PaymentProcessor.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {}

