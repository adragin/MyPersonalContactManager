package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.RequestBodyClient;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import com.example.MyPersonalContactManager.repository.DatabaseUserRepository;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MyPersonalContactManager")
public class ContactController {

    private ResponseAPI responseAPI;

    @Autowired
    private DatabaseUserRepository dbUserRepository;
    @Autowired
    private DatabaseContactService dbService;

    @PostMapping(value = "/createContact", consumes = "application/json")
    public ResponseEntity<ResponseAPI> crateContact(@Valid @RequestBody RequestBodyClient requestBodyClient) {
        Contact contact = dbService.createContact(requestBodyClient.contact);
        responseAPI.response = contact;
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ResponseAPI> getContactById(@RequestHeader("GetContacts") String token,
                                                      @PathVariable String id) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }
        boolean userRole = dbUserRepository.getUserRoleByToken(token);
        Contact contact = dbService.getContactById(id);
        String user = dbUserRepository.getUserIdByToken(token);

        if (!user.equals(contact.getUserOwner) || userRole) {
            responseAPI.response = contact;
            return ResponseEntity.ok(responseAPI);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.ok(responseAPI);
        }
        responseAPI.response = new Error(204, "No Content");
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<ResponseAPI> getAllContacts(@RequestHeader("GetContacts") String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }
        boolean userRole = dbUserRepository.getUserRoleByToken(token);
        List<Contact> contactList = dbService.getAllContacts();
        String user = dbUserRepository.getUserIdByToken(token);

        for (int i = 0; i < contactList.size(); i++) {
            if (!user.equals(contactList.get(i).getUserOwner) || userRole) {
                responseAPI.response = contactList;
                return ResponseEntity.ok(responseAPI);
            } else {
                responseAPI.response = new Error(403, "Access denied.");
                return ResponseEntity.ok(responseAPI);
            }
        }
        responseAPI.response = new Error(204, "No Content");
        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping(value = "/updateContact/{id}", consumes = "application/json")
    public ResponseEntity<ResponseAPI> updateContact(@PathVariable String id,
                                                     @RequestHeader("Authorization") String token,
                                                     @RequestBody RequestBodyClient requestBodyClient) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }

        boolean userRole = dbUserRepository.getUserRoleByToken(token);
        responseAPI = new ResponseAPI();
        if (userRole) {
            ContactDTOBig contactDTOBig = dbService.updateContact(id, requestBodyClient.contactDTOBig);
            responseAPI.response = contactDTOBig;
            return ResponseEntity.ok(responseAPI);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.ok(responseAPI);
        }
    }

    @DeleteMapping("contacts/delete")
    public ResponseEntity<ResponseAPI> deleteContactById(@RequestHeader("Contact-Id") String id,
                                                         @RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }

        boolean userRole = dbUserRepository.getUserRoleByToken(token);
        responseAPI = new ResponseAPI();
        boolean isDeleted;
        if (userRole) {
            isDeleted = dbService.deleteContactById(id);
            responseAPI.response = isDeleted;
            return ResponseEntity.ok(responseAPI);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.ok(responseAPI);
        }
    }
}