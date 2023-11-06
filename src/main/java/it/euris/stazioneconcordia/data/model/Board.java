package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_trello")
    private String idTrello;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "date_last_activity")
    private LocalDateTime dateLastActivity;


    @OneToMany
    @JoinColumn(name = "id_board")
    private List<Lists> lists;

    @OneToMany
    @JoinColumn(name = "id_board")
    private List<Labels> labels;


    @Override
    public BoardDTO toDto() {
        return BoardDTO.builder()
                .id(numberToString(id))
                .idTrello(idTrello)
                .name(name)
                .description(description)
                .url(url)
                .dateLastActivity(localDateTimeToString(dateLastActivity))
                .build();
    }
}
