package com.MindHub.homebanking.services.implement;

import com.MindHub.homebanking.dtos.CardDTO;
import com.MindHub.homebanking.models.Card;
import com.MindHub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CardServiceImplement implements com.MindHub.homebanking.services.CardService {

    @Autowired
    CardRepository cardRepository;


    @Override
    public void saveCard(Card card)
    {
        cardRepository.save(card);
    }

    @Override
    public void deleteCard(Card card){cardRepository.deleteById(card.getId());}

    @Override
    public Card findById(long id) {

        return cardRepository.findById(id).orElse(null);

    }

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }


}
