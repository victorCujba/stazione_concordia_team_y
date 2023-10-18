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
public class ListDTO implements Dto {

    private String id;
    private String idBoard;
    private String name;
    private String closed;
    private String position;

    @Override
    public List toModel() {
        return List
                .builder()
                .id(stringToLong(id))
                .idBoard(stringToLong(idBoard))
                .name(name)
                .closed(stringToBoolean(closed))
                .position(stringToLong(position))
                .build();
    }
}
