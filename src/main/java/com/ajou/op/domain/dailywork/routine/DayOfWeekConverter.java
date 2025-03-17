package com.ajou.op.domain.dailywork.routine;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class DayOfWeekConverter implements AttributeConverter<Set<DayOfWeekBit>, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(Set<DayOfWeekBit> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return 0;
        }
        return attribute.stream()
                .mapToInt(DayOfWeekBit::getBit)
                .reduce(0, (a, b) -> a | b);
    }

    @Override
    public Set<DayOfWeekBit> convertToEntityAttribute(Integer dbData) {
        if (dbData == null || dbData == 0) {
            return Set.of();
        }
        return Arrays.stream(DayOfWeekBit.values())
                .filter(day -> (dbData & day.getBit()) != 0)
                .collect(Collectors.toSet());
    }
} 