package com.example.MyPersonalContactManager.model;

import java.util.Objects;

public class ResponseAPI <T>{
    public T response;

    public ResponseAPI(T response) {
        this.response = response;
    }

    public ResponseAPI() {
    }
}
