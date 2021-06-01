package com.flightapp.model;

import javax.persistence.*;

@Entity
@Table(name = "secretKeys")
public class Keys {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Long cardID;
    private String secretKey;
    private String iv;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getCardID() {
        return this.cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return this.iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public Keys() {}

    public Keys(Long cardID, String secretKey, String iv) {
        this.setCardID(cardID);
        this.setSecretKey(secretKey);
        this.setIv(iv);
    }

}
