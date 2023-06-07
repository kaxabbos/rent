package com.rent.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum BodyType {
    MINIVAN("Минивен"),
    OFF_ROAD_VEHICLE("Внедорожник"),
    COUPE("Купе"),
    SEDAN("Седан"),
    UNIVERSAL("Универсал"),
    HATCHBACK("Хэтчбек");
    private final String name;
}

