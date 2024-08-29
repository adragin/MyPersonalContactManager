package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.RequestBodyClient;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import com.example.MyPersonalContactManager.service.DataBaseUserService;
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

    //    @Autowired
//    private DatabaseUserRepository dbUserRepository;
    @Autowired
    private DatabaseContactService dbContactService;
    @Autowired
    private DataBaseUserService dbUserService;

    @PostMapping(value = "/createContact", consumes = "application/json")
    public ResponseEntity<ResponseAPI> crateContact(@Valid @RequestBody RequestBodyClient requestBodyClient,
                                                    @RequestHeader("crateContact") String token) {
        if (token == null) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }
        String userId = dbUserService.getUserIdByToken(token);
        Contact contact = dbContactService.createContact(requestBodyClient.contact, userId);
        responseAPI = new ResponseAPI();
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
        boolean userRole = dbUserService.getUserRoleByToken(token);
        String user = dbUserService.getUserIdByToken(token);
        Contact contact = dbContactService.getContactById(id);

        if (!user.equals(contact.getOwnerId()) || userRole) {
            responseAPI.response = contact;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<ResponseAPI> getAllContacts(@RequestHeader("GetContacts") String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }
        boolean userRole = dbUserService.getUserRoleByToken(token);
        List<Contact> contactList = dbContactService.getAllContacts();
        String user = dbUserService.getUserIdByToken(token);

        for (int i = 0; i < contactList.size(); i++) {
            if (!user.equals(contactList.get(i).getOwnerId()) || userRole) {
                responseAPI.response = contactList;
            } else {
                responseAPI.response = new Error(403, "Access denied.");
            }
            return ResponseEntity.ok(responseAPI);
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

        boolean userRole = dbUserService.getUserRoleByToken(token);
        responseAPI = new ResponseAPI();
        if (userRole) {
            ContactDTOBig contactDTOBig = dbContactService.updateContact(id, requestBodyClient.contactDTOBig);
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

        boolean userRole = dbUserService.getUserRoleByToken(token);
        responseAPI = new ResponseAPI();
        boolean isDeleted;
        if (userRole) {
            isDeleted = dbContactService.deleteContactById(id);
            responseAPI.response = isDeleted;
            return ResponseEntity.ok(responseAPI);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.ok(responseAPI);
        }
    }
}