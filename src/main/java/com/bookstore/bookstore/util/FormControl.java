package com.bookstore.bookstore.util;

import com.bookstore.bookstore.exception.EmptyFieldException;
import com.bookstore.bookstore.exception.NotFoundExceptionHandler;

import java.math.BigDecimal;

public class FormControl {

    public static void validateField(String fieldValue) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new EmptyFieldException("The field is empty. Please provide a value.");
        }
    }

    public static void objIsExist(Object o, String type) {
        if (o == null) {
            throw new NotFoundExceptionHandler("The " + type + " is not found. Please Provide exist one.");
        }
    }

    public static void isMinimumOrderPrice(BigDecimal price) {
        BigDecimal minimumPrice = new BigDecimal("25");
        int comparisonResult = price.compareTo(minimumPrice);
        if ((comparisonResult < 0)) {
            throw new EmptyFieldException("Please ensure that your order total exceeds $25. You are currently $" + minimumPrice.subtract(price) + " short of the required amount.");
        }
    }
}
