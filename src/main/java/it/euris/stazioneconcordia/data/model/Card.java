package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
public class Card implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_list")
    private Long idList;

    @Column(name = "position")
    private Long position;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "description")
    private String description;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "date_last_activity")
    private LocalDateTime dateLastActivity;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @ManyToOne
    @MapsId("id_list")
    @JoinColumn(name = "id_list")
    private List list;

    @Override
    public CardDTO toDto() {
        return CardDTO.builder()
                .id(numberToString(id))
                .idList(numberToString(list.getId()))
                .name(name)
                .position(numberToString(position))
                .priority(priorityToString(priority))
                .description(description)
                .closed(booleanToString(closed))
                .expirationDate(localDateTimeToString(expirationDate))
                .dateLastActivity(localDateTimeToString(dateLastActivity))
                .build();
    }
}
