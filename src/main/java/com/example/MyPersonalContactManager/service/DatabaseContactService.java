package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
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
    public Contact getContactById(String contactId) {
        Contact tempContact = dbRepository.getContactByContactId(contactId);
        List<String> phoneList = dbRepository.getPhoneListByContactId(contactId);
        tempContact.setPhones(phoneList);
        return tempContact;
    }

    public List<Contact> getContactByUserId(String userId) {
        List<Contact> tempContact = dbRepository.getContactByUserId(userId);
        for (int i = 0; i < tempContact.size(); i++) {
            List<String> phoneList = dbRepository.getPhoneListByContactId(tempContact.get(i).getId());
            tempContact.get(i).setPhones(phoneList);
        }
        return tempContact;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> tempListAllContacts = dbRepository.getAllContacts();
        for (Contact contact : tempListAllContacts) {
            List<String> phoneList = dbRepository.getPhoneListByContactId(contact.getId());
            contact.setPhones(phoneList);
        }
        return tempListAllContacts;
    }

    @Override
    public Contact createContact(Contact contact, String userID) {
        Contact tempContact = dbRepository.createContact(contact, userID);
//        List<String> phoneList = dbRepository.createPhone(contact.getPhones(), tempContact.getId());
//        tempContact.setPhones(phoneList);
        return tempContact;
    }

    @Override
    public ContactDTOBig updateContact(String contactId, ContactDTOBig newContact) {
        return dbRepository.updateContact(contactId, newContact);
    }

    @Override
    public boolean deleteContactById(String contactId) {
        return dbRepository.deleteContactById(contactId);
    }

    public String getOwnerId(String contactId) {
        return dbRepository.getOwnerId(contactId);
    }
}
