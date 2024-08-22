package com.example.MyPersonalContactManager.models.RequestResponseModels;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOShort;
import jakarta.validation.Valid;

import java.util.List;

public class RequestBodyClient{
    @Valid
    public Contact contact;
    public ContactDTOShort contactDTOShort;
    public ContactDTOBig contactDTOBig;

    public List<Contact> contactList;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ContactDTOShort getContactDTO() {
        return contactDTOShort;
    }

    public void setContactDTO(ContactDTOShort contactDTOShort) {
        this.contactDTOShort = contactDTOShort;
    }
}


