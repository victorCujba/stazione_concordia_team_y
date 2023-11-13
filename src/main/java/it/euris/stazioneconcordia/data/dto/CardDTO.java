package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO implements Dto {

    private String id;
    private String idTrello;
    private String idList;
//    private List<String> idUser;
    private List<String> idLabels;
    private String name;
    private String position;
    private String description;
    private String closed;
    private String expirationDate;
    private String dateLastActivity;

    @Override
    public Card toModel() {
        if (idLabels.isEmpty()) {
            idLabels.add(0, "0");
        }
        List<CardUser> cardUserList = new ArrayList<>();
        return Card
                .builder()
                .id(stringToLong(id))
                .idTrello(idTrello)
                .name(name)
                .position(stringToLong(position))
                .description(description)
                .expirationDate(stringToLocalDateTime(expirationDate))
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .closed(stringToBoolean(closed))
                .list(Lists.builder().idTrello(idList).build())
//                .cardUsers(cardUserList.add(CardUser.builder().user(User.builder().id(idUser.get(0)).build()).card(Card.builder().idTrello(idTrello).build()).build()))
                .labels(Labels.builder().idTrello(idLabels.get(0)).build())
                .build();
    }

    @Override
    public CardTrelloDto toTrelloDto() {
        return CardTrelloDto.builder()
                .id(idTrello)
                .idLabels(idLabels)
                .idList(idList)
//                .idMembers(idUser)
                .name(name)
                .pos(position)
                .desc(description)
                .due(expirationDate)
                .dateLastActivity(dateLastActivity)
                .closed(closed)
                .build();
    }
}
