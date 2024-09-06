package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.ContactModels.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseContactRepository implements ContactRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Contact> contactRowMapper = (ResultSet rs, int rowNum) -> {
        Contact contact = new Contact();
        contact.setId(rs.getString("id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setEmail(rs.getString("email"));
        contact.setBirthday(rs.getDate("birth_day").toLocalDate());
        contact.setAddress(rs.getString("address"));
        contact.setPhoto(rs.getURL("photo"));
        contact.setOwnerId(rs.getString("owner_id"));
        contact.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        contact.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return contact;
    };
    private final RowMapper<ContactDTOBig> contactDTOBigRowMapper = (ResultSet rs, int rowNum) -> {
        ContactDTOBig contactDTO = new ContactDTOBig();
        contactDTO.setFirstName(rs.getString("first_name"));
        contactDTO.setLastName(rs.getString("last_name"));
        contactDTO.setEmail(rs.getString("email"));
        contactDTO.setBirthday(rs.getDate("birth_day").toLocalDate());
        contactDTO.setAddress(rs.getString("address"));
        contactDTO.setPhoto(rs.getURL("photo"));
        contactDTO.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return contactDTO;
    };

    private final RowMapper<Phone> phoneRowMapper = (rs, rowNum) -> {
        Phone phone = new Phone();
        phone.setContactId(rs.getString("contact_id"));
        phone.setPhoneNumber(rs.getString("phone_number"));
        return phone;
    };

    @Override
    public Contact createContact(Contact contact, String userID) {
        String sqlContacts = "INSERT INTO contacts (id, first_name, last_name, email, birth_day, " +
                "address, photo, owner_id, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlContacts, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getId());
            ps.setString(2, contact.getFirstName());
            ps.setString(3, contact.getLastName());
            ps.setString(4, contact.getEmail());
            ps.setString(5, String.valueOf(contact.getBirthday()));
            ps.setString(6, contact.getAddress());
            ps.setString(7, String.valueOf(contact.getPhoto()));
            ps.setString(8, String.valueOf(userID));
            ps.setString(9, String.valueOf((contact.getCreateDate())));
            ps.setString(10, String.valueOf(contact.getLastUpdateDate()));

            return ps;
        });

        String phonesSql = "INSERT INTO contacts_phones (contact_id, phone_number) VALUES (?, ?)";
        for (Phone phone : contact.getPhones()) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(phonesSql);
                ps.setString(1, contact.getId());
                ps.setString(2, phone.getPhoneNumber());
                return ps;
            });
        }
        return contact;
    }

//    public List<String> createPhone(List<String> phoneList, String contactId) {
//        String sqlPhoneNumbers = "INSERT INTO Contacts_Phones (Contact_Id, Phone_Number, Create_Date, Last_Update_Date)" +
//                "VALUES (?, ?, ?, ?)";
//
//
//        for (String phone : phoneList) {
//            KeyHolder keyHolderForPhoneNumbers = new GeneratedKeyHolder();
//            jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection.prepareStatement(sqlPhoneNumbers, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, contactId);
//                ps.setString(2, phone);
//                ps.setString(3, String.valueOf(phone.getCreateDate().toLocalDate()));
//                ps.setString(4, String.valueOf(phone.getLastUpdateDate().toLocalDate()));
//                return ps;
//            }, keyHolderForPhoneNumbers);
//        }
//        String selectSql = "SELECT * FROM Contacts_Phones WHERE Contact_Id = ?";
//        return jdbcTemplate.query(selectSql, phoneRowMapper, contactId);
//    }

    @Override
    public Contact getContactByContactId(String contactId) {
        String selectSql = "SELECT * FROM Contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, contactRowMapper, contactId);
    }

    public List<Contact> getContactByUserId(String userId) {
        String selectSql = "SELECT * FROM Contacts WHERE Owner_Id = ?";
        return jdbcTemplate.query(selectSql, contactRowMapper, userId);
    }

    public List<String> getPhoneListByContactId(String contactId) {
        String selectSql = "SELECT Phone_Number FROM Contacts_Phones WHERE Contact_Id = ?";
        return jdbcTemplate.query(selectSql, (rs, rowNum) -> rs.getString("Phone_Number"), contactId);
    }

    @Override
    public List<Contact> getAllContacts() {
        String selectSql = "SELECT * FROM Contacts";
        List<Contact> contactList = jdbcTemplate.query(selectSql, contactRowMapper);

        return contactList.stream().toList();
    }

    public String getOwnerId(String contactId) {
        String selectSql = "SELECT Owner_Id FROM Contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, String.class, contactId);
    }

    @Override
    public ContactDTOBig updateContact(String contactId, ContactDTOBig newContact) {
        String deletePhoneNumbersSql = "DELETE FROM Contacts_Phones WHERE Contact_Id =?";
        jdbcTemplate.update(deletePhoneNumbersSql, contactId);

        String sql = "UPDATE Contacts " +
                "SET First_Name = ? , Last_Name = ? , Email = ?, Birth_Day = ?, Address = ?, " +
                "Photo = ?, Last_Update_Date = ? where id = ?";

//        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            ps.setString(1, newContact.getFirstName());
            ps.setString(2, newContact.getLastName());
            ps.setString(3, newContact.getEmail());
            ps.setString(4, String.valueOf(newContact.getBirthday()));
            ps.setString(5, newContact.getAddress());
            ps.setString(6, String.valueOf(newContact.getPhoto()));
            ps.setString(7, String.valueOf(newContact.getLastUpdateDate()));
            ps.setString(8, contactId);
            return ps;
        });

        String phonesSql = "INSERT INTO Contacts_Phones (Contact_Id, Phone_Number, Create_Date, Last_Update_Date) VALUES (?, ?, ?, ?)";
        for (String phone : newContact.getPhones()) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(phonesSql);
                ps.setString(1, contactId);
                ps.setString(2, phone);
                ps.setString(3, String.valueOf(LocalDateTime.now()));
                ps.setString(4, String.valueOf(LocalDateTime.now()));
                return ps;
            });
        }

        String selectSql = "SELECT * FROM Contacts WHERE id = ?";
        ContactDTOBig updatedContact = jdbcTemplate.queryForObject(selectSql, contactDTOBigRowMapper, contactId);

        String selectPhonesSql = "SELECT * FROM Contacts_Phones WHERE Contact_Id = ?";
        List<String> phoneNumbers = jdbcTemplate.query(selectPhonesSql, (rs, rowNum) -> rs.getString("Phone_Number"), contactId);
        updatedContact.setPhones(phoneNumbers);

        return updatedContact;
    }

    @Override
    public boolean deleteContactById(String contactId) {
        String deletePhoneNumbersSql = "DELETE FROM Contacts_Phones WHERE Contact_Id =?";
        jdbcTemplate.update(deletePhoneNumbersSql, contactId);

        return jdbcTemplate.update("DELETE FROM Contacts WHERE id = ?", contactId) > 0;
    }
}
