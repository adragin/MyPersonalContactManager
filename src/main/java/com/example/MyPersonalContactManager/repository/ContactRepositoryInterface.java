package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;

import java.util.List;

public interface ContactRepositoryInterface<T, U> {
    T createContact(T contact, String UserId);

    Contact createContact(Contact contact, String userID);

    T getContactByContactId(String id);

    List<T> getAllContacts();

    U updateContact(String id, U newContact);

    boolean deleteContactById(String id);
}
