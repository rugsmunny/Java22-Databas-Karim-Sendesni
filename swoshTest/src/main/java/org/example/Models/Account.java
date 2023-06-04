package org.example.Models;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class Account {
    private int id;
    private LocalDateTime account_created;
    private String phone_number;
    private int user_id;
    private BigInteger balance;

    public Account() {
    }

    public Account(String phone_number, BigInteger balance, int user_id) {
        this.phone_number = phone_number;
        this.balance = balance;
        this.user_id = user_id;
    }

    public Account(int id, LocalDateTime account_created, String phone_number, int user_id, BigInteger balance) {
        this.id = id;
        this.account_created = account_created;
        this.phone_number = phone_number;
        this.user_id = user_id;
        this.balance = balance;
    }

/*    public int getId() {
        return id;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }*/

    public String getPhone_number() {
        return phone_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public BigInteger getBalance() {
        return balance;
    }
}
