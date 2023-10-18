package it.euris.stazioneconcordia.model;

import it.euris.stazioneconcordia.data.dto.KeyTokenDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "key_token")
public class KeyToken implements Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "key")
  private String key;

  @Column(name = "token")
  private String token;

  @OneToOne
  @MapsId("id_board")
  @JoinColumn(name = "id_board")
  private Board board;

  @Override
  public KeyTokenDTO toDto() {
    return KeyTokenDTO
        .builder()
        .id(numberToString(id))
        .key(key)
        .token(token)
        .idBoard(numberToString(board.getId()))
        .build();
  }
}
