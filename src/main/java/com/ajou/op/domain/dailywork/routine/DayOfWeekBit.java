package com.ajou.op.domain.dailywork.routine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayOfWeekBit {
    MONDAY(0b000001),      // 1
    TUESDAY(0b000010),     // 2
    WEDNESDAY(0b000100),   // 4
    THURSDAY(0b001000),    // 8
    FRIDAY(0b010000),      // 16
    SATURDAY(0b100000);    // 32

    private final int bit;

    public static boolean contains(int flags, DayOfWeekBit day) {
        return (flags & day.getBit()) != 0;
    }

    public static int add(int flags, DayOfWeekBit day) {
        return flags | day.getBit();
    }

    public static int remove(int flags, DayOfWeekBit day) {
        return flags & ~day.getBit();
    }
} 