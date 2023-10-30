package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.CardService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findByLabels(Long idLabels) {

        List<Card> cards = cardRepository.findAll();
        List<Card> cardsWithPriority = cards.stream()
                .filter(card -> Objects.equals(card.getLabels().getId(), idLabels))
                .collect(Collectors.toList());

        return cardsWithPriority;
    }


    @Override
    public List<Card> findAllCardsWhitExpirationDateInLast5Days() {
        List<Card> cards = cardRepository.findAll();

        List<Card> cardsNearExpiration = cards
                .stream()
                .filter(Card -> Card.getExpirationDate().isAfter(LocalDateTime.now().minusDays(5L)))
                .collect(Collectors.toList());


        if (cardsNearExpiration.size() > 1) {
            return cardsNearExpiration.stream()
                    .sorted(Comparator.comparing(Card::getExpirationDate).reversed())
                    .collect(Collectors.toList());

        }
        return cardsNearExpiration;

    }


    @Override
    public Card insert(Card card) {
        if (card.getId() != null) {
            throw new IdMustBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Card update(Card card) {
        if (card.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Boolean deleteById(Long idCard) {
        cardRepository.deleteById(idCard);
        return cardRepository.findById(idCard).isEmpty();
    }

    @Override
    public Card findById(Long idCard) {
        return cardRepository.findById(idCard).orElse(Card.builder().build());
    }

    @Override
    @SneakyThrows
    public Card[] getCardsFromTrelloList(Long idList, String key, String token) {

        String url = "https://api.trello.com/1/lists/" + idList + "/cards?key=" + key + "&token=" + token;

        URI targetURI = new URI(url);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();

        CardDTO[] cardDTOs = gson.fromJson(response.body(), CardDTO[].class);

        for (CardDTO cardDTO : cardDTOs) {
            String date = cardDTO.getDateLastActivity().substring(0, 19);
            cardDTO.setDateLastActivity(date);
            if (cardDTO.getExpirationDate()!=null) {
                String date2 = cardDTO.getExpirationDate().substring(0, 19);
                cardDTO.setExpirationDate(date2);
            }
            Card card = cardDTO.toModel();
            insert(card);
        }

        Card[] cards = new Card[cardDTOs.length];
        for (int i = 0; i < cardDTOs.length; i++) {
            cards[i] = cardDTOs[i].toModel();
        }

        return cards;
    }


}

