package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardUser;
import it.euris.stazioneconcordia.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardUserDTO implements Dto {

    private Long id;
    private Long idCard;
    private String idUser;

    @Override
    public CardUser toModel() {
        return CardUser.builder()
                .id(id)
                .card(Card.builder().id(idCard).build())
                .user(User.builder().id(stringToLong(idUser)).build())
                .build();
    }

    @Override
    public TrelloDto toTrelloDto() {
        return null;
    }
}
