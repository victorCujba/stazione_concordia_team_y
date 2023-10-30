package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardStateDTO implements Dto {

    private String id;
    private String idCard;
    private String fromList;
    private String toList;
    private String dateLastUpdate;

    @Override
    public CardState toModel() {
        return CardState
                .builder()
                .id(stringToLong(id))
                .card(Card.builder().id(stringToLong(idCard)).build())
                .fromList(stringToListLabel(fromList))
                .toList(stringToListLabel(toList))
                .dateLastUpdate(stringToLocalDateTime(dateLastUpdate))
                .build();
    }

    @Override
    public TrelloDto toTrelloDto() {
        return null;
    }
}
