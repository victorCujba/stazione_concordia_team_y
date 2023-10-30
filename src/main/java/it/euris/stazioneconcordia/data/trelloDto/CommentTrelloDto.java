package it.euris.stazioneconcordia.data.trelloDto;

import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentTrelloDto implements TrelloDto {

    private String id;
    private String idCard;
    private String idMemberCreator;
    private String text;
    private String date;

    @Override
    public CommentDTO trellotoDto() {
        return CommentDTO.builder()
                .id(id)
                .idCard(idCard)
                .idUser(idMemberCreator)
                .date(date)
                .build();
    }
}
