package com.flightapp.model;

import javax.persistence.*;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @OneToOne
    // @JoinColumn(name = "keyID") 
    private Long id;
    //@Column(name = "keyID")
    private Long keyID;
    private String card_number;
    private String card_expiry_month;
    private String card_expiry_year;
    private String card_cvv;
    private Long userID;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard_number() {
        return this.card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_expiry_month() {
        return this.card_expiry_month;
    }

    public void setCard_expiry_month(String card_expiry_month) {
        this.card_expiry_month = card_expiry_month;
    }

    public String getCard_expiry_year() {
        return this.card_expiry_year;
    }

    public void setCard_expiry_year(String card_expiry_year) {
        this.card_expiry_year = card_expiry_year;
    }

    public String getCard_cvv() {
        return this.card_cvv;
    }

    public void setCard_cvv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    public Long getUserID() {
        return this.userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getKeyID() {
        return this.keyID;
    }

    public void setKeyID(Long keyID) {
        this.keyID = keyID;
    }

    public CreditCard() {}

    public CreditCard(String card_number, String card_expiry_month, String card_expiry_year, String card_cvv) {
        this.setCard_number(card_number);
        this.setCard_expiry_month(card_expiry_month);
        this.setCard_expiry_year(card_expiry_year);
        this.setCard_cvv(card_cvv);
    }

}
