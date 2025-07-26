package daniel.PaymentProcessor.mapper;


import daniel.PaymentProcessor.controller.DTO.RequestedPaymentsDTO;
import daniel.PaymentProcessor.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (RequestedPaymentsDTO dto);
}
