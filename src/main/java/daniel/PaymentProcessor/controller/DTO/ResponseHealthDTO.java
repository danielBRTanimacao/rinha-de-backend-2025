package daniel.PaymentProcessor.controller.DTO;

public record ResponseHealthDTO(
        boolean failing,
        int minResTime
) {
}
