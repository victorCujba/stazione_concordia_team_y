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
    private String idMemberCreator;
    private CommentDataDTO data;
    private String date;

    @Override
    public CommentDTO trellotoDto() {
        return CommentDTO.builder()
                .idTrello(id)
                .idUser(idMemberCreator)
                .idCard(data.getCard().getId())
                .commentBody(data.getText())
                .date(date)
                .build();
    }
}
