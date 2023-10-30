package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToBoolean;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLocalDateTime;

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
                .id(id)
                .name(name)
                .description(description)
                .url(url)
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .closed(stringToBoolean(closed))
                .build();
    }

    @Override
    public BoardTrelloDTO toTrelloDto() {
        return BoardTrelloDTO.builder()
                .id(id)
                .name(name)
                .desc(description)
                .closed(closed)
                .url(url)
                .build();
    }
}
