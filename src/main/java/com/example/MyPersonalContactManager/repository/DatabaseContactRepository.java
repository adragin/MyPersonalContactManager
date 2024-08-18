package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.ContactDTO;

import java.util.List;

public class DatabaseContactRepository implements ContactRepositoryInterface <Contact, ContactDTO>{
    @Override
    public Contact createContact(Contact contact) {
        return null;
    }

    @Override
    public ContactDTO getContactById(String id) {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }

    @Override
    public ContactDTO updateContact(String id, ContactDTO newContact) {
        return null;
    }

    @Override
    public boolean deleteContactById(String id) {
        return false;
    }
}
