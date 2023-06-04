package org.example.Models;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class Transaction {
    private int id;
    private String sender;
    private String recipient;
    private LocalDateTime date_time;
    private BigInteger amount;

    public Transaction() {
    }

    public Transaction(String recipient, String sender,  BigInteger amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Transaction(int id, String sender, String recipient, LocalDateTime date_time, BigInteger amount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.date_time = date_time;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public BigInteger getAmount() {
        return amount;
    }
}
