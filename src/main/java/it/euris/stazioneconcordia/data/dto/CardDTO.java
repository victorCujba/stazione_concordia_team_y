package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.model.Card;
import it.euris.stazioneconcordia.model.List;
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
    private String name;
    private String position;
    private String priority;
    private String description;
    private String expirationDate;
    private String dateLastActivity;
    private String closed;
    private String idList;
    @Override
    public Card toModel() {
        return Card
                .builder()
                .id(stringToLong(id))
                .name(name)
                .position(stringToLong(position))
                .priority(stringToPriority(priority))
                .description(description)
                .expirationDate(stringToLocalDateTime(expirationDate))
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .closed(stringToBoolean(closed))
                .list(List.builder().id(stringToLong(idList)).build())
                .build();
    }
}