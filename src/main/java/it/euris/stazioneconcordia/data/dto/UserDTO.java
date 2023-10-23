package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Dto {

    private String id;
    private String fullName;
    private String bio;
    private String avatarUrl;
    private String email;
    private String status;

    @Override
    public User toModel() {
        return User
                .builder()
                .id(id)
                .fullName(fullName)
                .bio(bio)
                .avatarUrl(avatarUrl)
                .email(email)
                .status(stringToBoolean(status))
                .build();
    }
}
