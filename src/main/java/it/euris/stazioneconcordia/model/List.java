package it.euris.stazioneconcordia.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @Column(name = "position")
    private Long position;

    @Column(name = "id_board")
    private Long idBoard;




}

