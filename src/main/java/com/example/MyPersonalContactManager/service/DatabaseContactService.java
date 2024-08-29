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
    public List<Contact> getContactById(String contactId) {
        List<Contact> tempContact = dbRepository.getContactByContactId(contactId);
        for (int i = 0; i < tempContact.size(); i++) {
            List<Phone> phoneList = dbRepository.getPhoneListByContactId(contactId);
            tempContact.get(i).setPhones(phoneList);
        }
        return tempContact;
    }

    public List<Contact> getContactByUserId(String userId) {
        List<Contact> tempContact = dbRepository.getContactByUserId(userId);
        for (int i = 0; i < tempContact.size(); i++) {
            List<Phone> phoneList = dbRepository.getPhoneListByContactId(tempContact.get(i).getId());
            tempContact.get(i).setPhones(phoneList);
        }
        return tempContact;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> tempListAllContacts = dbRepository.getAllContacts();
        for (int i = 0; i < tempListAllContacts.size(); i++) {
            List<Phone> phoneList = dbRepository.getPhoneListByContactId(tempListAllContacts.get(i).getId());
            tempListAllContacts.get(i).setPhones(phoneList);
        }
        return tempListAllContacts;
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
