package org.example.Models;


import java.math.BigInteger;
import java.time.LocalDateTime;


public class User {

    private int id;
    private LocalDateTime account_created;
    private String password;
    private String first_name;
    private String last_name;
    private long signature_number;
    private BigInteger ssn;
    private boolean logged_in;

    public User(){

    }

    public User(String password, BigInteger ssn) {
        this.password = password;
        this.ssn = ssn;
    }

    public User(String first_name, String last_name, BigInteger ssn, String password, long signature_number) {
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.signature_number = signature_number;
        this.ssn = ssn;
        this.logged_in = false;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public long getSignature_number() {
        return signature_number;
    }

    public BigInteger getSsn() {
        return ssn;
    }

    public boolean getLogged_in() {
        return logged_in;
    }
}
