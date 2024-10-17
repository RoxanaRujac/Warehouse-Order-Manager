package model;

import java.time.LocalDateTime;

/**
 * A record representing a bill containing information such as the timestamp, client name, product name,
 * total price, and quantity.
 */
public record Bill(LocalDateTime timeStamp, String clientName, String productName, double totalprice, int quantity) {
}
