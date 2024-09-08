package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;
import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class DBContactRepositoryAdapter implements ContactRepositoryInterface {

    @Autowired
    private JPAContactRepository jpaContactRepository;
    @Autowired
    private JPAUserRepository jpaUserRepository;

    @Override
    public Contact createContact(Contact contact, String UserId) {
        User owner = jpaUserRepository.findById(UserId).get();
        contact.setOwner(owner);
        jpaContactRepository.save(contact);
        return contact;
    }

    @Override
    public Contact getContactByContactId(String id) {
        return jpaContactRepository.findById(id).get();
    }

    @Override
    public List<Contact> getAllContacts() {
        return jpaContactRepository.findAll();
    }

    @Override
    public List<Contact> getContactsByUserId(String userId) {
        return jpaContactRepository.findByOwner_UserId(userId);
    }

    @Override
    public ContactDTO updateContact(String id, ContactDTO newContact) {
        return null;
    }

    @Override
    public boolean deleteContactById(String id) {
        return false;
    }

    public String getOwnerId(String contactId) {
        return jpaContactRepository.findById(contactId).get().getOwner().getUserId();
    }
}
