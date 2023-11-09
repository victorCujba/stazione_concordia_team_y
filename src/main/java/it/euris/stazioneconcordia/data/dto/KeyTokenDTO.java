package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.KeyToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLong;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyTokenDTO implements Dto {

    private String id;
    private String key;
    private String token;
    private String idBoard;

    @Override
    public KeyToken toModel() {
        return KeyToken
                .builder()
                .id(stringToLong(id))
                .key(key)
                .token(token)
                .board(Board.builder().id(stringToLong(idBoard)).build())
                .build();
    }

    @Override
    public TrelloDto toTrelloDto() {
        return null;
    }
}
