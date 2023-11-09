package it.euris.stazioneconcordia.service.trello.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

import static it.euris.stazioneconcordia.service.trello.utils.TrelloConstants.*;

@Service
public class TrelloCommentService {
    private final RestTemplate restTemplate;
    private final Gson gson;

    public TrelloCommentService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public List<CommentTrelloDto> getAllCommentsByIdCard(String idCard) {

        String url = buildUrlForGetAllCommentsByIdCardRequest(idCard);
        String json = restTemplate.getForObject(url, String.class);
        Type listType = new TypeToken<List<CommentTrelloDto>>() {
        }.getType();
        List<CommentTrelloDto> commentsTrelloDTO = gson.fromJson(json, listType);

        return commentsTrelloDTO;
    }

    public CommentTrelloDto getLastCommentFromCard(String idCard) {

        String url = buildUrlForGetAllCommentsByIdCardRequest(idCard);
        String json = restTemplate.getForObject(url, String.class);
        Type listType = new TypeToken<List<CommentTrelloDto>>() {
        }.getType();

        List<CommentTrelloDto> commentTrelloDtos = gson.fromJson(json, listType);
        if (!commentTrelloDtos.isEmpty()) {
            commentTrelloDtos.sort(Comparator.comparing(CommentTrelloDto::getDate));
            System.out.println(commentTrelloDtos.get(0));
            return commentTrelloDtos.get(0);
        } else {
            //TODO IMPLEMENT NULL EXCEPTION
            return null;
        }


    }

    public void insertNewCommentByCardIdAndText(String idCard, String text) {

        String url = buildUrlFo2PostCommentByIdCard(idCard, text);

        CommentDTO commentDTO = CommentDTO
                .builder()
                .commentBody(text)
                .build();

        String requestBody = gson.toJson(commentDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        //restTemplate.postForObject(url, requestEntity, CommentDTO.class);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

    }

    private static String buildUrlFo2PostCommentByIdCard(String idCard, String text) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_POST_COMMENT_BY_ID_CARD)
                .buildAndExpand(idCard, text, KEY_VALUE, TOKEN_VALUE)
                .toString();
    }


    private static String buildUrlForGetAllCommentsByIdCardRequest(String idCard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_COMMENTS_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE)
                .toString();
    }

    public static void main(String[] args) {
//        TrelloCommentService trelloCommentService = new TrelloCommentService();
//        String idCard = "652d5736ec602d3f7cd4c698";
//        String text = "Test comment inserted from Java";
//        // trelloCommentService.insertNewCommentByCardIdAndText(idCard, text);
//        System.out.println(trelloCommentService.getAllCommentsByIdCard(idCard));
    }


}
