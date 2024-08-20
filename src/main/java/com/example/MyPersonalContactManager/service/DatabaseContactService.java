package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.ContactDTO;
import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class DatabaseContactService implements  ContactServiceInterface<Contact, ContactDTO>{
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
    public ContactDTO updateContact(String id, ContactDTO newContact) {
        return null;
    }

    @Override
    public boolean deleteContactById(String id) {
        return false;
    }
}
