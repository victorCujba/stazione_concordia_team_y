package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDTO implements Dto {

    private String text;

    private CardDTO card;

    public DataDTO(String string) {
        this.text=string;
    }

    @Override
    public Model toModel() {
        return null;
    }

    @Override
    public TrelloDto toTrelloDto() {
        return null;
    }
}
