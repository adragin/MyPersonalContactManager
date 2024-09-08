package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTO;
import com.example.MyPersonalContactManager.models.ContactModels.Phone;
import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseContactRepository implements ContactRepositoryInterface {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Contact> contactRowMapper = (rs, rowNum) -> {
        Contact contact = new Contact();
        contact.setId(rs.getString("id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setEmail(rs.getString("email"));

        List<Phone> phones = getPhoneListByContactId(contact.getId());
        contact.setPhones(phones);

        contact.setBirthday(rs.getDate("birth_day").toLocalDate());
        contact.setAddress(rs.getString("address"));
        contact.setPhoto(rs.getURL("photo"));

        User owner = getOwner(rs.getString("owner_id"));
        contact.setOwner(owner);

        contact.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        contact.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return contact;
    };
    private final RowMapper<ContactDTO> contactDTORowMapper = (rs, rowNum) -> {
        ContactDTO contactDTO = new ContactDTO();
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

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId(rs.getString("user_id"));
        user.setRole(rs.getBoolean("user_role"));
        user.setLogin(rs.getString("login"));
        user.setUserName(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        user.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        user.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return user;
    };

    private List<Phone> getPhoneListByContactId(String contactId) {
        String selectSql = "SELECT *  FROM contacts_phones WHERE contact_id = ?";
        return jdbcTemplate.query(selectSql, phoneRowMapper, contactId);
    }

    private User getOwner(String userId) {
        String selectUser = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.query(selectUser, userRowMapper, userId).get(0);
    }

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

    @Override
    public Contact getContactByContactId(String contactId) {
        String selectSql = "SELECT * FROM contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, contactRowMapper, contactId);
    }

    public List<Contact> getContactsByUserId(String userId) {
        String selectSql = "SELECT * FROM contacts WHERE owner_id = ?";
        return jdbcTemplate.query(selectSql, contactRowMapper, userId);
    }

    @Override
    public List<Contact> getAllContacts() {
        String selectSql = "SELECT * FROM contacts";
        List<Contact> contactList = jdbcTemplate.query(selectSql, contactRowMapper);

        return contactList.stream().toList();
    }

    public String getOwnerId(String contactId) {
        String selectSql = "SELECT owner_id FROM Contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, String.class, contactId);
    }

    @Override
    public ContactDTO updateContact(String contactId, ContactDTO newContact) {
        String deletePhoneNumbersSql = "DELETE FROM contacts_phones WHERE contact_id =?";
        jdbcTemplate.update(deletePhoneNumbersSql, contactId);

        String sql = "UPDATE contacts " +
                "SET first_name = ? , last_name = ? , email = ?, birth_day = ?, address = ?, " +
                "photo = ?, last_update_date = ? WHERE id = ?";

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

        String phonesSql = "INSERT INTO contacts_phones (contact_id, phone_number, create_date, last_update_date) VALUES (?, ?, ?, ?)";
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

        String selectSql = "SELECT * FROM contacts WHERE id = ?";
        ContactDTO updatedContact = jdbcTemplate.queryForObject(selectSql, contactDTORowMapper, contactId);

        String selectPhonesSql = "SELECT * FROM contacts_phones WHERE contact_id = ?";
        List<String> phoneNumbers = jdbcTemplate.query(selectPhonesSql, (rs, rowNum) -> rs.getString("phone_number"), contactId);
        updatedContact.setPhones(phoneNumbers);

        return updatedContact;
    }

    @Override
    public boolean deleteContactById(String contactId) {
        String deletePhoneNumbersSql = "DELETE FROM contacts_phones WHERE contact_id =?";
        jdbcTemplate.update(deletePhoneNumbersSql, contactId);

        return jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", contactId) > 0;
    }
}
