package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.ContactModels.Phone;
import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseContactService implements ContactServiceInterface<Contact, ContactDTOBig> {
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
    public Contact createContact(Contact contact, String userID) {
        Contact tempContact = dbRepository.createContact(contact, userID);
        List<Phone> phoneList = dbRepository.createPhone(contact.getPhones(), tempContact.getId());
        tempContact.setPhones(phoneList);
        return tempContact;
    }

    @Override
    public ContactDTOBig updateContact(String id, ContactDTOBig newContact) {
        dbRepository.updateContact(id, newContact);
        return newContact;
    }

    @Override
    public boolean deleteContactById(String id) {
        dbRepository.deleteContactById(id);
        return true;
    }
}
