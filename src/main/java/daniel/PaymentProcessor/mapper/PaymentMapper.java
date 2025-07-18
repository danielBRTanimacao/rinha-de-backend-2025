package daniel.PaymentProcessor.mapper;


import daniel.PaymentProcessor.controller.DTO.requestsPaymentsDTOs.RequestPrincipalPaymentDTO;
import daniel.PaymentProcessor.entities.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (RequestPrincipalPaymentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Payment partialUpdate(RequestPrincipalPaymentDTO dto, @MappingTarget Payment entity);
}
