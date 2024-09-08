package com.example.MyPersonalContactManager.utils;

import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UtilsCheckToken {

    @Autowired
    private ResponseAPI responseAPI;

    public ResponseEntity<ResponseAPI> isTokenCorrect(String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseAPI);
        }
        return null;
    }
}
