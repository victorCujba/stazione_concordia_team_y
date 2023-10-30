package it.euris.stazioneconcordia.data.trelloDto;

import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListsTrelloDto implements TrelloDto {
    private String id;
    private String name;
    private String position;
    private String closed;
    private String idBoard;

    @Override
    public ListsDTO trellotoDto() {
        return ListsDTO
                .builder()
                .id(id)
                .name(name)
                .closed(closed)
                .idBoard(idBoard)
                .position(position)
                .build();
    }
}
