package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
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
    private String closed;
    private String dateLastActivity;

    @Override
    public Board toModel() {
        return Board
                .builder()
                .id(stringToLong(id))
                .name(name)
                .desctiption(description)
                .url(url)
                .closed(stringToBoolean(closed))
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .build();
    }
}
