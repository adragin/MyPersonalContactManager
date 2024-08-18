package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.ContactDTO;
import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;

import java.util.List;

public class DatabaseContactService implements  ContactServiceInterface<Contact, ContactDTO>{
    @Override
    public ContactDTO getContactById(String id) {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }

    @Override
    public Contact createContact(Contact contact) {
        if (contact.getAddress().isBlank() || contact.getBirthday() == null || contact.getEmail().isBlank() ||
                contact.getLastName().isBlank() || contact.getPhoto() == null) {
            return new Contact(contact.getFirstName(), "", "", contact.getPhone(), null, "", null);
        }

        DatabaseContactRepository dbRepository = new DatabaseContactRepository();
        dbRepository.createContact(contact);

        return contact;
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
