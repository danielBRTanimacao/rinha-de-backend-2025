package daniel.PaymentProcessor.service.impl;

import daniel.PaymentProcessor.repository.PaymentRepository;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
}
