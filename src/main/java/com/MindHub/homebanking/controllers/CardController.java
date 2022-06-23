package com.MindHub.homebanking.controllers;

import com.MindHub.homebanking.dtos.CardDTO;
import com.MindHub.homebanking.models.Card;
import com.MindHub.homebanking.models.CardColor;
import com.MindHub.homebanking.models.CardType;
import com.MindHub.homebanking.models.Client;
import com.MindHub.homebanking.services.CardService;
import com.MindHub.homebanking.services.ClientService;
import com.MindHub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards(){return cardService.getCards();}

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard (Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color)
    {
        Client client = clientService.findByMail(authentication.getName());

        String number = CardUtils.getCardNumber();
        int cvv = CardUtils.getCVV();
        Set<Card> clientCards = client.getCards().stream().filter(card -> card.getType().equals(type) && card.isDisable() == false).collect(Collectors.toSet());

        Card card = new Card(color, type, number, cvv, LocalDate.now(), LocalDate.now().plusYears(5), client, false);

        if (clientCards.size() >= 3){
            return new ResponseEntity<>("You have reached the cards limit", HttpStatus.FORBIDDEN);
        }
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> disableCard (Authentication authentication,
                                               @RequestParam long id){


        Card disableCard = cardService.findById(id);
        Client client =  clientService.findByMail(authentication.getName());


        if(!client.getCards().contains(disableCard))
        {
            return new ResponseEntity<>("This card cannot be deleted because it is not yours", HttpStatus.FORBIDDEN);
        }

        disableCard.setDisable(true);
        cardService.saveCard(disableCard);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
