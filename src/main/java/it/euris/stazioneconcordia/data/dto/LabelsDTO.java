package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelsDTO implements Dto {
    private String id;
    private String idTrello;
    private String name;
    private String color;
    private Long uses;
    private String idTrelloBoard;

    @Override
    public Labels toModel() {
        return Labels
                .builder()
                .idTrello(idTrello)
                .board(Board.builder().idTrello(idTrelloBoard).build())
                .name(name)
                .color(color)
                .uses(uses)
                .build();
    }

    @Override
    public LabelsTrelloDto toTrelloDto() {
        return LabelsTrelloDto
                .builder()
                .id(idTrello)
                .name(name)
                .color(color)
                .uses(uses)
                .idBoard(idTrelloBoard)
                .build();
    }
}
