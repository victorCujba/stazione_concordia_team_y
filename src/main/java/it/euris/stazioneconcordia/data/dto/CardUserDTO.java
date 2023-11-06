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
                .user(User.builder().id(idUser).build())
                .build();
    }

    @Override
    public TrelloDto toTrelloDto() {
        return null;
    }
}
