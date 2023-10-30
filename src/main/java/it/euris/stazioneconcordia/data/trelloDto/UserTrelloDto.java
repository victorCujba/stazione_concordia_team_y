package it.euris.stazioneconcordia.data.trelloDto;

import it.euris.stazioneconcordia.data.dto.UserDTO;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTrelloDto implements TrelloDto {
    private String id;
    private String fullName;
    private String bio;
    private String avatarUrl;
    private String email;
    private String status;
    @Override
    public UserDTO trellotoDto() {
        return UserDTO
                .builder()
                .id(id)
                .fullName(fullName)
                .bio(bio)
                .avatarUrl(avatarUrl)
                .email(email)
                .status(status)
                .build();
    }
}
