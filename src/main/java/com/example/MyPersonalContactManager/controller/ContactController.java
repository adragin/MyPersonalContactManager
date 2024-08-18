package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.RequestBodyClient;
import com.example.MyPersonalContactManager.model.ResponseAPI;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/MyPersonalContactManager")
public class ContactController {

    private ResponseAPI responseAPI;

    private DatabaseContactService dbService = new DatabaseContactService();


    @PostMapping(value = "/createContact", consumes = "application/json")
    public ResponseEntity<ResponseAPI> crateContact(@Valid @RequestBody RequestBodyClient requestBodyClient) {
        responseAPI  = new ResponseAPI();

        Contact contact = dbService.createContact(requestBodyClient.contact);
        responseAPI.response = contact;
        return ResponseEntity.ok(responseAPI);
    }

//    @GetMapping("/getContactById")
//    public ResponseEntity<ResponseAPI> getContactById(@Valid @RequestBody RequestBodyClient requestBodyClient) {
//        responseAPI  = new ResponseAPI();
//
//        responseAPI.response = requestBodyClient.data;
//        return ResponseEntity.ok(responseAPI);
//    }
//
//    @GetMapping("/getAllContacts")
//    public ResponseEntity<ResponseAPI> getAllContacts(@Valid @RequestBody RequestBodyClient requestBodyClient) {
//        responseAPI  = new ResponseAPI();
//
//        responseAPI.response = requestBodyClient.data;
//        return ResponseEntity.ok(responseAPI);
//    }

}
