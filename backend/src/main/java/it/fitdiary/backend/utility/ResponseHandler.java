package it.fitdiary.backend.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class ResponseHandler {
    /**
     * costruttore privato.
     */
    private ResponseHandler() {
    }

    /**
     * @param status      HttpStatus
     * @param responseObj risposta formattata
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateResponse(
            final HttpStatus status,
            final Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        String statusMessage = "";
        if (status.is1xxInformational()
                || status.is2xxSuccessful()
                || status.is3xxRedirection()) {
            map.put("data", responseObj);
            statusMessage = "success";
        } else if (status.is4xxClientError()) {
            map.put("data", responseObj);
            statusMessage = "fail";
        }else if (status.is5xxServerError()) {
            generateResponse(status,responseObj.toString());
        }
        map.put("status", statusMessage);
        return new ResponseEntity<>(map, status);
    }

    /**
     * @param status      HttpStatus
     * @param objectName  String
     * @param responseObj risposta formatata
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateResponse(
            final HttpStatus status,
            final String objectName,
            final Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        String statusMessage = "";
        if (status.is1xxInformational()
                || status.is2xxSuccessful()
                || status.is3xxRedirection()) {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put(objectName, responseObj);
            map.put("data", objectMap);
            statusMessage = "success";
        } else if (status.is4xxClientError()) {
            map.put("data", responseObj);
            statusMessage = "fail";
        }
        map.put("status", statusMessage);
        return new ResponseEntity<>(map, status);
    }

    /**
     * @param status status
     * @param message message
     * @return ResponseEntity
     */
    private static ResponseEntity<Object> generateResponse(
            final HttpStatus status,
            final String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", "error");
        return new ResponseEntity<>(map, status);
    }
}
