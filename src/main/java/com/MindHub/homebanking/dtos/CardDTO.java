package com.MindHub.homebanking.dtos;

import com.MindHub.homebanking.models.Card;
import com.MindHub.homebanking.models.CardColor;
import com.MindHub.homebanking.models.CardType;
import com.MindHub.homebanking.models.Client;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

public class CardDTO {

    private long id;

    private String cardHolder, number;
    private CardColor color;
    private CardType type;
    private int cvv;
    private LocalDate fromDate, thruDate;
    private boolean disable;

    public CardDTO(){}

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.color = card.getColor();
        this.type = card.getType();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.disable = card.isDisable();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public boolean isDisable() {
        return disable;
    }
}
