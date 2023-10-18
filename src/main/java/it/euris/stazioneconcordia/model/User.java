package it.euris.stazioneconcordia.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "bio" , nullable = false)
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;


    @Builder.Default
    @OneToMany(mappedBy="idUser", fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

}
