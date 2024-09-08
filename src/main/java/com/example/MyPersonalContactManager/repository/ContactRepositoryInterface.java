package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;

import java.util.List;

public interface ContactRepositoryInterface {

    Contact createContact(Contact contact, String userID);

    Contact getContactByContactId(String id);

    List<Contact> getAllContacts();

    List<Contact> getContactsByUserId(String userId);

    ContactDTO updateContact(String id, ContactDTO newContact);

    boolean deleteContactById(String id);

    String getOwnerId(String contactId);
}
