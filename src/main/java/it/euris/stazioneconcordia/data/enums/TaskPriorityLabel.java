package it.euris.stazioneconcordia.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPriorityLabel {
    ALTA_PRIORITA(
            "Alta Priorità",
            "652d5736ec602d3f7cd4c6ac"),

    MEDIA_PRIORITA(
            "Media Priorità",
            "652d5736ec602d3f7cd4c6af"),

    BASSA_PRIORITA(
            "Bassa Priorità",
            "652d5736ec602d3f7cd4c6b2"),

    DESIDERATA(
            "Desiderata",
            "652d5736ec602d3f7cd4c6b5");

    private final String name;
    private final String idTrelloLabel;

    //"id": "652d5736ec602d3f7cd4c6ac",
//        "idBoard": "652d5736ec602d3f7cd4c625",
//        "name": "Alta Priorità",
//        "color": "red",
//        "uses": 10

    //  "idLabels": ["652d5736ec602d3f7cd4c6ac"

    //"idLabels": ["652d5736ec602d3f7cd4c6af"

    //"id": "652d5736ec602d3f7cd4c6af",
//        "idBoard": "652d5736ec602d3f7cd4c625",
//        "name": "Media Priorità",
//        "color": "yellow",
//        "uses": 3
    //    "labels": [
//    {
//        "id": "652d5736ec602d3f7cd4c6b2",
//            "idBoard": "652d5736ec602d3f7cd4c625",
//            "name": "Bassa Priorità",
//            "color": "green",
//            "uses": 2
//    }
//],
//        "idLabels": [
//        "652d5736ec602d3f7cd4c6b2"

//    "labels": [
//{
//"id": "652d5736ec602d3f7cd4c6b5",
//"idBoard": "652d5736ec602d3f7cd4c625",
//"name": "Desiderata",
//"color": "blue",
//"uses": 2
//}
//],
//"idLabels": [
//"652d5736ec602d3f7cd4c6b5"
//],

}
