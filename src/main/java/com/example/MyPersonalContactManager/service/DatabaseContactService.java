package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.ContactModels.Phone;
import com.example.MyPersonalContactManager.repository.ContactRepositoryInterface;
import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseContactService implements ContactServiceInterface {

    private ContactRepositoryInterface dbRepository;

    @Autowired
    public DatabaseContactService(DatabaseContactRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public ContactDTO getContactByContactId(String contactId) {
        Contact tempContact = dbRepository.getContactByContactId(contactId);
        return convertToDTO(tempContact);
    }

    public List<ContactDTO> getContactsByUserId(String userId) {
        List<Contact> contactListByUserId = dbRepository.getContactsByUserId(userId);
        return contactListByUserId.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> tempListAllContacts = dbRepository.getAllContacts();
        return tempListAllContacts.stream()
                .map(contact -> convertToDTO(contact))
                .toList();
    }

    @Override
    public Contact createContact(Contact contact, String userID) {
        Contact tempContact = dbRepository.createContact(contact, userID);
        return tempContact;
    }

    @Override
    public Contact updateContact(String id, Contact newContact) {
        return null;
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

    public ContactDTO convertToDTO(Contact contact) {
        List<String> phoneNumbers = contact.getPhones()
                .stream()
                .map(Phone::getPhoneNumber)
                .toList();

        return new ContactDTO(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                phoneNumbers,  // Только номера телефонов
                contact.getBirthday(),
                contact.getAddress(),
                contact.getPhoto(),
                contact.getOwner().getUserId(),
                contact.getCreateDate(),
                contact.getLastUpdateDate()
        );
    }
}
