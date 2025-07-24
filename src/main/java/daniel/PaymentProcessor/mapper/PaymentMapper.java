package daniel.PaymentProcessor.mapper;


import daniel.PaymentProcessor.controller.DTO.requestsPaymentsDTOs.RequestPrincipalPaymentDTO;
import daniel.PaymentProcessor.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (RequestPrincipalPaymentDTO dto);
}
