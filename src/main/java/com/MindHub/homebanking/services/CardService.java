package com.MindHub.homebanking.services;

import com.MindHub.homebanking.dtos.CardDTO;
import com.MindHub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    void saveCard (Card card);
     void deleteCard(Card card);

    Card findById (long id);

    List<CardDTO> getCards();

}
