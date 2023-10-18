package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO implements Dto {

    private String id;
    private String name;
    private String description;
    private String url;
    private String dateLastActivity;
    private String closed;


    @Override
    public Board toModel() {
        return Board
                .builder()
                .id(stringToLong(id))
                .name(name)
                .description(description)
                .url(url)
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .closed(stringToBoolean(closed))
                .build();
    }
}
