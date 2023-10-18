package it.euris.stazioneconcordia.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "desc" , nullable = false)
    private String desc;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "date_last_activity", nullable = false)
    private String dateLastActivity;
}
