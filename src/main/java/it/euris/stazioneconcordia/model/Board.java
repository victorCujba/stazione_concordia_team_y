package it.euris.stazioneconcordia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed=false;

    @Column(name = "date_last_activity")
    private LocalDateTime dateLastActivity;

    @Builder.Default
    @OneToMany(mappedBy="idBoard", fetch = FetchType.EAGER)
    private List<it.euris.stazioneconcordia.model.List> lists = new ArrayList<>();



}
