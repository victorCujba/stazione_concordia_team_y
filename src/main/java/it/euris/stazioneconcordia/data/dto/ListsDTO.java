package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToBoolean;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListsDTO implements Dto {

    private String id;
    private String idTrello;
    private String name;
    private String position;
    private String closed;
    private String idBoard;

    @Override
    public Lists toModel() {
        return Lists
                .builder()
                .id(stringToLong(id))
                .idTrello(idTrello)
                .name(name)
                .position(stringToLong(position))
                .closed(stringToBoolean(closed))
                .board(Board.builder().id(stringToLong(idBoard)).build())
                .build();
    }

    @Override
    public ListsTrelloDto toTrelloDto() {
        return ListsTrelloDto
                .builder()
                .id(id)
                .name(name)
                .position(position)
                .closed(closed)
                .idBoard(idBoard)
                .build();
    }
}
