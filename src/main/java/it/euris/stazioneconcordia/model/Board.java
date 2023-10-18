package it.euris.stazioneconcordia.model;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "name")
    private String name;

    @Column(name = "description" )
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


    @Override
    public BoardDTO toDto() {
        return BoardDTO.builder()
                .id(numberToString(id))
                .name(name)
                .description(description)
                .url(url)
                .closed(booleanToString(closed))
                .dateLastActivity(localDateTimeToString(dateLastActivity))
                .build();
    }
}
