package com.example.MyPersonalContactManager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class RequestBodyClient <T>{

    public Contact contact;
    public ContactDTO contactDTO;


}


