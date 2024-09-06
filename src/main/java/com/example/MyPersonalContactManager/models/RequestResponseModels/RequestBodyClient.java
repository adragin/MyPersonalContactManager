package com.example.MyPersonalContactManager.models.RequestResponseModels;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

public class RequestBodyClient {
    @Valid
    @Getter
    @Setter
    public Contact contact;
    public ContactDTOBig contactDTOBig;
    public String userId;
}


