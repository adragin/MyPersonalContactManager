package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.RequestBodyClient;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import com.example.MyPersonalContactManager.service.DataBaseUserService;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import com.example.MyPersonalContactManager.utils.UtilsCheckToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MyPersonalContactManager")
@Tag(name = "Contact'ы", description = "Контроллер с CRUD методами для контактов user'а")
public class ContactController {

    @Autowired
    private DatabaseContactService dbContactService;
    @Autowired
    private DataBaseUserService dbUserService;
    @Autowired
    private ResponseAPI responseAPI;
    @Autowired
    private UtilsCheckToken utilsCheckToken;

    @Operation(summary = "Создать новый контакт", description = "Создаёт новый контакт в списке контактов")
    @PostMapping(value = "/createContact", consumes = "application/json")
    public ResponseEntity<ResponseAPI> crateContact(@Valid @RequestBody RequestBodyClient requestBodyClient,
                                                    @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userId = dbUserService.getUserIdByToken(token);
        Contact contact = dbContactService.createContact(requestBodyClient.contact, userId);
        responseAPI.response = contact;

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Получить контакт по id контакта", description = "Возвращает контакт, если token принадлежит владельцу контакта или админу")
    @GetMapping("/contacts/{contactId}")
    public ResponseEntity<ResponseAPI> getContactByContactId(@RequestHeader(value = "token", required = false) String token,
                                                             @PathVariable String contactId) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userId = dbUserService.getUserIdByToken(token);
        ContactDTO contactDTO = dbContactService.getContactByContactId(contactId);

        if (userId.equals(contactDTO.getOwnerId()) || dbUserService.isAdmin(token)) {
            responseAPI.response = contactDTO;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Получить все контакты",
            description = "Возвращает все контакты того, кому принадлежит token или вообще все контакты, если token принадлежит админу")
    @GetMapping("/contacts")
    public ResponseEntity<ResponseAPI> getAllContacts(@RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userId = dbUserService.getUserIdByToken(token);
        if (userId.isEmpty()) {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
        }

        if (dbUserService.isAdmin(token)) {
            List<ContactDTO> allContacts = dbContactService.getAllContacts();
            responseAPI.response = allContacts;
        } else {
            List<ContactDTO> contactListByUserId = dbContactService.getContactsByUserId(userId);
            responseAPI.response = contactListByUserId;
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Получить контакты конкретного пользователя", description = "Возвращает контакты конкретного пользователя, если token принадлежит админу")
    @GetMapping("/contacts/by-user")
    public ResponseEntity<ResponseAPI> getContactsByUserId(@RequestHeader(value = "token", required = false) String token,
                                                           @RequestHeader(value = "userId", required = false) String userId) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userIdByToken = dbUserService.getUserIdByToken(token);

        if (userIdByToken.equals(userId) || dbUserService.isAdmin(token)) {
            responseAPI.response = dbContactService.getContactsByUserId(userId);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Обновить контакт по id", description = "Позволяет обновить conatct, если token принадлежит владельцу контакта или админу")
    @PutMapping(value = "/updateContact/{id}", consumes = "application/json")
    public ResponseEntity<ResponseAPI> updateContact(@PathVariable("id") String contactId,
                                                     @RequestHeader(value = "token", required = false) String token,
                                                     @RequestBody RequestBodyClient requestBodyClient) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userIdByToken = dbUserService.getUserIdByToken(token);
        String ownerId = dbContactService.getOwnerId(contactId);

        if (userIdByToken.equals(ownerId) || dbUserService.isAdmin(token)) {
            ContactDTO contactDTO = dbContactService.updateContact(contactId, requestBodyClient.contactDTO);
            responseAPI.response = contactDTO;
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Удалить контакт по id", description = "Позволяет обновить сontact, если token принадлежит владельцу контакта или админу")
    @DeleteMapping("contacts/delete")
    public ResponseEntity<ResponseAPI> deleteContactById(@RequestHeader("Contact-Id") String contactId,
                                                         @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        String userIdByToken = dbUserService.getUserIdByToken(token);
        ContactDTO contactDTO = dbContactService.getContactByContactId(contactId);

        if (userIdByToken.equals(contactDTO.getOwnerId()) || dbUserService.isAdmin(token)) {
            responseAPI.response = dbContactService.deleteContactById(contactId);
        } else {
            responseAPI.response = new Error(403, "Access denied.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }
}