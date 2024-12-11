package iuh.fit.fashionecommercewebsitebackend.models.enums;

import lombok.Getter;
@Getter
public enum DeliveryMethod {
    ECONOMY("road"),
    EXPRESS("fly");

    private final String transportType;

    DeliveryMethod(String transportType) {
        this.transportType = transportType;
    }

    public static DeliveryMethod fromString(String deliveryMethod) {
        for (DeliveryMethod method : DeliveryMethod.values()) {
            if (method.name().equalsIgnoreCase(deliveryMethod)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown delivery method: " + deliveryMethod);
    }
}

