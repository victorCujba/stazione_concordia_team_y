package it.euris.stazioneconcordia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "comment_body")
    private String commentBody;


    @Column(name = "deleted")
    private Boolean deleted=false;

    @Column(name = "id_card")
    private Long idCard;

    @Column(name = "id_user")
    private Long idUser;




}
