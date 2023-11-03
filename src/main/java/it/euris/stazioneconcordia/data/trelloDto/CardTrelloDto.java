package it.euris.stazioneconcordia.data.trelloDto;


import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardTrelloDto implements TrelloDto {

    private String id;
    private String idList;
    private List<String> idLabels;
    private String name;
    private String pos;
    private String closed;
    private String desc;
    private String due;
    private String dateLastActivity;


    @Override
    public CardDTO trellotoDto() {
        return CardDTO.builder()
                .idTrello(id)
                .idList(idList)
                .idLabels(idLabels)
                .name(name)
                .closed(closed)
                .description(desc)
                .expirationDate(due)
                .position(pos)
                .dateLastActivity(dateLastActivity)
                .build();
    }
}
