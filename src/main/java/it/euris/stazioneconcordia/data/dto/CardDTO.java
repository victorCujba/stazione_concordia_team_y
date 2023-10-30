package it.euris.stazioneconcordia.data.dto;

import com.google.gson.annotations.SerializedName;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<String> idLabels;
    private String name;
    private String position;
    private String description;
    private String closed;
    private List<CommentDTO> comment;
    private String expirationDate;
    private String dateLastActivity;

    @Override
    public Card toModel() {
        if (idLabels.isEmpty()) {
            idLabels.add(0, "0");
        }
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
                .list(Lists.builder().id(stringToLong(idList)).build())
                .labels(Labels.builder().id(stringToLong(idLabels.get(0))).build())
                .build();
    }

    @Override
    public CardTrelloDto toTrelloDto() {
        return CardTrelloDto.builder()
                .id(id)
                .idLabels(idLabels.toString())
                .idList(idList)
                .name(name)
                .pos(position)
                .desc(description)
                .due(expirationDate)
                .dateLastActivity(dateLastActivity)
                .closed(closed)
                .build();
    }
}
