package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;

import java.util.List;

public interface ContactServiceInterface {

    ContactDTO getContactByContactId(String id);

    List<ContactDTO> getAllContacts();

    Contact createContact(Contact contact, String userID);

    Contact updateContact(String id, Contact newContact);

    boolean deleteContactById(String id);
}
