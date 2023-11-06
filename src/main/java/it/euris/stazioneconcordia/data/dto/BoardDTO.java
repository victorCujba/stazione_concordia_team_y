package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
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
    private String idTrello;
    private String name;
    private String description;
    private String url;
    private String dateLastActivity;


    @Override
    public Board toModel() {
        return Board
                .builder()
                .id(stringToLong(id))
                .idTrello(idTrello)
                .name(name)
                .description(description)
                .url(url)
                .dateLastActivity(stringToLocalDateTime(dateLastActivity))
                .build();
    }

    @Override
    public BoardTrelloDTO toTrelloDto() {
        return BoardTrelloDTO.builder()
                .id(idTrello)
                .name(name)
                .desc(description)
                .url(url)
                .build();
    }
}
