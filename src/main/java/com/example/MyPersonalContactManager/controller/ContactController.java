package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.RequestBodyClient;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import com.example.MyPersonalContactManager.service.DataBaseUserService;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MyPersonalContactManager")
public class ContactController {

    private final ResponseAPI responseAPI;
    private final DatabaseContactService dbContactService;
    private final DataBaseUserService dbUserService;

    @Autowired
    public ContactController(ResponseAPI responseAPI, DatabaseContactService dbContactService, DataBaseUserService dbUserService) {
        this.responseAPI = responseAPI;
        this.dbContactService = dbContactService;
        this.dbUserService = dbUserService;
    }

    @PostMapping(value = "/createContact", consumes = "application/json")
    public ResponseEntity<ResponseAPI> crateContact(@Valid @RequestBody RequestBodyClient requestBodyClient,
                                                    @RequestHeader(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }

        String userId = dbUserService.getUserIdByToken(token);
        Contact contact = dbContactService.createContact(requestBodyClient.contact, userId);
        responseAPI.response = contact;
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/contacts/{contactId}")
    public ResponseEntity<ResponseAPI> getContactByContactId(@RequestHeader(value = "token", required = false) String token,
                                                             @PathVariable String contactId) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }
        boolean userRole = dbUserService.getUserRoleByToken(token);
        String userId = dbUserService.getUserIdByToken(token);
        ContactDTO contactDTO = dbContactService.getContactByContactId(contactId);
        if (userId.equals(contactDTO.getOwnerId()) || userRole) {
            responseAPI.response = contactDTO;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<ResponseAPI> getAllContacts(@RequestHeader(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseAPI);
        }

        boolean userRole = dbUserService.getUserRoleByToken(token);
        String userId = dbUserService.getUserIdByToken(token);

        if (userId.isEmpty()) {
            responseAPI.response = new Error(403, "Access denied.");
        } else if (userRole) {
            List<ContactDTO> allContacts = dbContactService.getAllContacts();
            responseAPI.response = allContacts;
        } else {
            List<ContactDTO> contactListByUserId = dbContactService.getContactsByUserId(userId);
            responseAPI.response = contactListByUserId;
        }

        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping(value = "/updateContact/{id}", consumes = "application/json")
    public ResponseEntity<ResponseAPI> updateContact(@PathVariable("id") String contactId,
                                                     @RequestHeader(value = "token", required = false) String token,
                                                     @RequestBody RequestBodyClient requestBodyClient) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }

        boolean userRole = dbUserService.getUserRoleByToken(token);
        System.out.println("role = " + userRole);
        String userId = dbUserService.getUserIdByToken(token);
        System.out.println("userId = " + userId);
        String ownerId = dbContactService.getOwnerId(contactId);
        System.out.println("contactId = " + contactId);
        System.out.println("ownerId = " + ownerId);
        if (userId.equals(ownerId) || userRole) {
            ContactDTOBig contactDTOBig = dbContactService.updateContact(contactId, requestBodyClient.contactDTOBig);
            responseAPI.response = contactDTOBig;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.ok(responseAPI);
    }

    @DeleteMapping("contacts/delete")
    public ResponseEntity<ResponseAPI> deleteContactById(@RequestHeader("Contact-Id") String contactId,
                                                         @RequestHeader(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new Error(400, "Authorization header is missing.");
            return ResponseEntity.badRequest().body(responseAPI);
        }

        boolean userRole = dbUserService.getUserRoleByToken(token);
        String userId = dbUserService.getUserIdByToken(token);
        ContactDTO contactDTO = dbContactService.getContactByContactId(contactId);
        boolean isDeleted;

        if (userId.equals(contactDTO.getOwnerId()) || userRole) {
            isDeleted = dbContactService.deleteContactById(contactId);
            responseAPI.response = isDeleted;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.ok(responseAPI);
    }
}