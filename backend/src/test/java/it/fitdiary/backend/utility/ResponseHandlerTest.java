package it.fitdiary.backend.utility;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResponseHandlerTest {

    @Test
    void generateResponseIs1xxInformational() {
        var map= new HashMap<String, Object>();
        var objectMap = "test";
        map.put("data", objectMap);
        var statusMessage = "success";
        map.put("status", statusMessage);
        var status= HttpStatus.CONTINUE;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status,
                (Object) "test"));

    }
    @Test
    void generateResponseIs3xxRedirection() {
        var map= new HashMap<String, Object>();
        var objectMap = "test";
        map.put("data", objectMap);
        var statusMessage = "success";
        map.put("status", statusMessage);
        var status= HttpStatus.FOUND;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status,
                (Object) "test"));
    }
    @Test
    void generateResponseIs4xxClientError() {
        var map= new HashMap<String, Object>();
        var statusMessage = "";
        map.put("status", statusMessage);
        var status= HttpStatus.BAD_GATEWAY;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status,
                (Object) "test"));
    }

    @Test
    void testGenerateResponseIs1xxInformational() {
        var map= new HashMap<String, Object>();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("test", "test");
        map.put("data", objectMap);
        var statusMessage = "success";
        map.put("status", statusMessage);
        var status= HttpStatus.CONTINUE;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status, "test",
                "test"));
    }

    @Test
    void testGenerateResponseIs3xxRedirection() {
        var map= new HashMap<String, Object>();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("test", "test");
        map.put("data", objectMap);
        var statusMessage = "success";
        map.put("status", statusMessage);
        var status= HttpStatus.FOUND;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status, "test",
                "test"));
    }

    @Test
    void testGenerateResponseIs4xxClientError() {
        var map= new HashMap<String, Object>();
        var statusMessage = "";
        map.put("status", statusMessage);
        var status= HttpStatus.BAD_GATEWAY;
        var response=new ResponseEntity<Object> (map, status);
        assertEquals(response,ResponseHandler.generateResponse(status, "test",
                "test"));
    }
}