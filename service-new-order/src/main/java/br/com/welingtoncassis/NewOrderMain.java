package br.com.welingtoncassis;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderDispatcher = new KafkaDispatcher<Order>())  {
            try(var emailDispatcher = new KafkaDispatcher<String>()) {
                var userId = UUID.randomUUID().toString();
                var orderId = UUID.randomUUID().toString();
                var amount = Math.random() * 5000 + 1;
                var order = new Order(userId, orderId, new BigDecimal(amount));
                orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

                var email = "Welcome! we are processing your order!";
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
            }
        }
    }
}
