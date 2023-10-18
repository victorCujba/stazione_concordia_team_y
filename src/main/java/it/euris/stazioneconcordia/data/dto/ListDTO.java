package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.List;
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
    private String name;
    private String position;
    private String closed;
    private String idBoard;

    @Override
    public List toModel() {
        return List
                .builder()
                .id(stringToLong(id))
                .name(name)
                .position(stringToLong(position))
                .closed(stringToBoolean(closed))
                .idBoard(Board.builder().id(stringToLong(idBoard))).build()
                .build();
    }
}
