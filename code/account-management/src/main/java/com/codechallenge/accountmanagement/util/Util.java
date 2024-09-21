package com.codechallenge.accountmanagement.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    public static Date[] convertToDates(String dateRange) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date[] dates = new Date[2];

        try {
            String[] parts = dateRange.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Formato de fecha no válido. Se esperaba 'yyyyMMdd-yyyyMMdd'.");
            }

            dates[0] = formatter.parse(parts[0]);
            dates[1] = formatter.parse(parts[1]);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error al parsear las fechas: " + e.getMessage());
        }

        return dates;
    }

}
