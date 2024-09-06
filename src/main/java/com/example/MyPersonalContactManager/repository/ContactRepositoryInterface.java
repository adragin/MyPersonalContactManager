package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;

import java.util.List;

public interface ContactRepositoryInterface {

    Contact createContact(Contact contact, String userID);

    Contact getContactByContactId(String id);

    List<Contact> getAllContacts();

    List<Contact> getContactsByUserId(String userId);

    Contact updateContact(String id, Contact newContact);

    boolean deleteContactById(String id);

    String getOwnerId(String contactId);
}
