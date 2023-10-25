package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.model.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO implements Dto {

    private String id;
    private String idList;
    private String idLabels;
    private String name;
    private String position;
    private String description;
    private String closed;
    private String expirationDate;
    private String dateLastActivity;

    @Override
    public Card toModel() {
        return Card
                .builder()
                .id(id)
                .name(name)
                .position(stringToLong(position))
                .description(description)
                .expirationDate(stringToLocalDateTime(expirationDate))
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .closed(stringToBoolean(closed))
                .list(Lists.builder().id(idList).build())
                .labels(Labels.builder().id(id).build())
                .build();
    }
}
