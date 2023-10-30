package it.euris.stazioneconcordia.data.trelloDto;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardTrelloDTO implements TrelloDto {

    private String id;
    private String name;
    private String desc;
    private String url;

    @Override
    public BoardDTO trellotoDto() {
        return BoardDTO.builder()
                .idTrello(id)
                .name(name)
                .description(desc)
                .url(url)
                .build();
    }
}
