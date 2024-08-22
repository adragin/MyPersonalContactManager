package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.ContactDTOBig;
import com.example.MyPersonalContactManager.model.ContactDTOShort;
import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseContactService implements  ContactServiceInterface<Contact, ContactDTOBig>{
    public DatabaseContactService(DatabaseContactRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Autowired
    private DatabaseContactRepository dbRepository;
    @Override
    public Contact getContactById(String id) {
        return dbRepository.getContactById(id);
    }

    @Override
    public List<Contact> getAllContacts() {
        return dbRepository.getAllContacts().stream().toList();
    }

    @Override
    public Contact createContact(Contact contact) {
        dbRepository.createContact(contact);
        return contact;
    }

    @Override
    public ContactDTOBig updateContact(String id, ContactDTOBig newContact) {
        dbRepository.updateContact(id, newContact);
        return newContact;
    }

    @Override
    public boolean deleteContactById(String id) {
        return false;
    }
}
