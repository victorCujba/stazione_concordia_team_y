package it.euris.stazioneconcordia.service.trello.data;

import it.euris.stazioneconcordia.service.trello.enums.HttpRequestType;
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
