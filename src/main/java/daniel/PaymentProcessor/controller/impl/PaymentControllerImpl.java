package daniel.PaymentProcessor.controller.impl;

import daniel.PaymentProcessor.controller.PaymentController;
import daniel.PaymentProcessor.controller.DTO.RequestPaymentDTO;
import daniel.PaymentProcessor.entities.Payment;
import daniel.PaymentProcessor.entities.TypePayment;
import daniel.PaymentProcessor.mapper.PaymentMapper;
import daniel.PaymentProcessor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper payMapper;

    @Override
    public ResponseEntity<Void> createPayment(RequestPaymentDTO paymentDTO) {
        Payment reqPayment = payMapper.toEntity(paymentDTO);
        reqPayment.setTypePayment(TypePayment.DEFAULT);

        paymentService.processPayment(reqPayment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
