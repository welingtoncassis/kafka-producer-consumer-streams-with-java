package br.com.welingtoncassis;

import java.math.BigDecimal;

public class Email {
    private final String userId, orderId;
    private final BigDecimal amount;

    public Email(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
