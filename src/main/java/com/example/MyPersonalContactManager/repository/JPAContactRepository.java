package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findByOwner_UserId(String userId);
}
