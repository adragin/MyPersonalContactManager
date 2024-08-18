package com.example.MyPersonalContactManager.repository;

import java.util.List;

public interface ContactRepositoryInterface <T,U>{
    T createContact(T contact);
    U getContactById (String id);
    List<T> getAllContacts();
    U updateContact (String id, U newContact);
    boolean deleteContactById(String id);
}
