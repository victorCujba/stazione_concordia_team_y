package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.UserDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_trello")
    private String idTrello;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("id_user")
    @JoinColumn(name = "id_user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<CardUser> cardUsers;

    @Override
    public UserDTO toDto() {
        return UserDTO.builder()
                .id(numberToString(id))
                .id(idTrello)
                .fullName(fullName)
                .bio(bio)
                .avatarUrl(avatarUrl)
                .email(email)
                .status(status)
                .build();
    }
}
