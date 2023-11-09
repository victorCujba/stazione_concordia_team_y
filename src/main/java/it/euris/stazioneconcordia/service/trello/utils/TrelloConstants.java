package it.euris.stazioneconcordia.service.trello.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;


public interface TrelloConstants {

    String ID = "id";
    String CARD = "card";
    String DATE = "date";
    String DATE_LAST_ACTIVITY = "dateLastActivity";
    String EXPIRATION_DATE = "due";
    String ID_LIST = "idList";
    String ID_BOARD = "idBoard";
    String ID_CARD = "idCard";
    String ID_LABEL = "idLabel";
    String POSITION = "pos";
    String DESCRIPTION = "desc";
    String LABELS = "labels";
    String NAME = "name";
    String CLOSED = "closed";


    String BOARD_ID_VALUE = "652d5736ec602d3f7cd4c625";

    // LISTST ID
    String LIST_O1_ID_VALUE = "652d5736ec602d3f7cd4c626";
    String LIST_O2_ID_VALUE = "652d5736ec602d3f7cd4c627";
    String LIST_O3_ID_VALUE = "652d5736ec602d3f7cd4c628";
    String LIST_O4_ID_VALUE = "652d5736ec602d3f7cd4c629";

    //CARDS ID
    String CARD_01_ID_VALUE = "652d5736ec602d3f7cd4c696";
    String CARD_02_ID_VALUE = "652d5736ec602d3f7cd4c676";
    String CARD_03_ID_VALUE = "652d5736ec602d3f7cd4c6a0";
    String CARD_04_ID_VALUE = "652d5736ec602d3f7cd4c674";
    String CARD_05_ID_VALUE = "652d5736ec602d3f7cd4c698";


    String BOARD_URL = "https://trello.com/b/bXilfUhy/progetto-base-spaziale-team-y";

    String KEY_VALUE = "656d5bde047c3ac9c66eae4c33aa9230";

    String TOKEN_VALUE = "ATTA27702686ff9d2e286aadb299d53c874f655dc93f653cb20c42ea2f2be5eb111399494FE0";


    String URL_API_TRELLO_GET_CARDS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}/cards?key={key}&token={token}";
    String URL_API_TRELLO_GET_CARDS_BY_ID_LISTS = "https://api.trello.com/1/lists/{id}/cards?key={key}&token={token}";
    String URL_API_TRELLO_GET_CARD_BY_ID_CARD = "https://api.trello.com/1/cards/{id}?key={key}&token={token}";
    String URL_API_TRELLO_GET_BADGES_BY_ID_CARD = "https://api.trello.com/1/cards/{id}/badges?key={key}&token={token}";
    String URL_API_TRELLO_GET_BOARD_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}?key={key}&token={token}";
    String URL_API_TRELLO_GET_COMMENTS_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}/actions?filter=commentCard&key={key}&token={token}";
    String URL_API_TRELLO_POST_COMMENT_BY_ID_CARD = "https://api.trello.com/1/cards/{id}/actions/comments?text={text}&key={key}&token={token}";
    String URL_API_TRELLO_GET_USER_BY_USERNAME = "https://api.trello.com/1/members/{userName}?key={key}&token={token}";
    String URL_API_TRELLO_GET_LISTS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}/lists?key={key}&token={token}";
    String URL_API_TRELLO_GET_LABELS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}/labels?key={key}&token={token}";
    String URL_API_TRELLO_GET_ALL_USERS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}//members?key={key}&token={token}";
    String URL_API_TRELLO_PUT_MOVE_CARD_BY_ID_LIST_AND_ID_CARD = "https://api.trello.com/1/cards/{idCard}?&idList={idList}&key={key}&token={token}";
    String URL_API_TRELLO_UPDATE_CARD = "https://api.trello.com/1/cards/{id}?key={key}&token={token}";
    String URL_API_TRELLO_CREATE_CARD = "https://api.trello.com/1/cards?key={key}&token={token}";
    String URL_API_TRELLO_DELETE_CARD = "https://api.trello.com/1/cards/{id}?key={key}&token={token}";
    String URL_API_TRELLO_DELETE_COMMENT_ON_A_CARD = "https://api.trello.com/1/cards/{id}/actions/{idAction}/comments?key={key}&token={token}";
    String URL_API_TRELLO_UPDATE_LIST = "https://api.trello.com/1/lists/{id}?key={key}&token={token}";
    String URL_API_TRELLO_CREATE_A_NEW_LIST = "https://api.trello.com/1/lists?key={key}&token={token}";
    String URL_API_TRELLO_CLOSE_A_LIST = "https://api.trello.com/1/lists/{id}/closed?key={key}&token={token}";
    String URL_API_TRELLO_CREATE_NEW_COMMENT_ON_A_CARD = "https://api.trello.com/1/cards/{id}/actions/comments?key={key}&token={token}";
    String URL_API_TRELLO_GET_USER_BY_ID_USER = "https://api.trello.com/1/members/{id}?key={key}&token={token}";


}
