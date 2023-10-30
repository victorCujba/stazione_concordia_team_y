package it.euris.stazioneconcordia.data.trelloDto;

import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelsTrelloDto implements TrelloDto {

    private String id;
    private String name;
    private String color;
    private Long uses;
    private String idBoard;
    @Override
    public LabelsDTO trellotoDto() {
        return LabelsDTO
                .builder()
                .idTrello(id)
                .name(name)
                .color(color)
                .uses(uses)
                .idTrelloBoard(idBoard)
                .build();
    }
}
