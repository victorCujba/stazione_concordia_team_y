package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.cardStatusToString;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.localDateTimeToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_state")
public class CardState implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "from_list")
    @Enumerated(EnumType.STRING)
    private CardStatus fromList;

    @Column(name = "to_list")
    @Enumerated(EnumType.STRING)
    private CardStatus toList;

    @Column(name = "date_last_update")
    private LocalDateTime dateLastUpdate;

    @Override
    public CardStateDTO toDto() {
        return CardStateDTO
                .builder()
                .id(id)
                .idCard(card.getId())
                .fromList(cardStatusToString(fromList))
                .toList(cardStatusToString(toList))
                .dateLastUpdate(localDateTimeToString(dateLastUpdate))
                .build();
    }
}
