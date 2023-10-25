package it.euris.stazioneconcordia.trello.data;

import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.trello.enums.HttpRequestType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    private HttpRequestType httpRequest;
    private T responseData;


}
