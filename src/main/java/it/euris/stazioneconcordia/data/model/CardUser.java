package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardUserDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card_user")
public class CardUser implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("id_card")
    @JoinColumn(name = "id_card")
    private Card card;

    @ManyToOne
    @MapsId("id_user")
    @JoinColumn(name = "id_user")
    private User user;

    @Override
    public CardUserDTO toDto() {
        return CardUserDTO.builder()
                .id(id)
                .idCard(card.getId())
                .idUser(user.getId())
                .build();
    }
}
