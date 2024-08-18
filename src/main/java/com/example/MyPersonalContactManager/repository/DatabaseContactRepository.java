package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.model.Contact;
import com.example.MyPersonalContactManager.model.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class DatabaseContactRepository implements ContactRepositoryInterface <Contact, ContactDTO>{

    private JdbcTemplate jdbcTemplate;

    public DatabaseContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Contact> contactItemRowMapper = (ResultSet rs, int rowNum) -> {
        Contact contact = new Contact();
        contact.setId(rs.getString("id"));
        contact.setFirstName(rs.getString("First_Name"));
        contact.setLastName(rs.getString("Last_Name"));
        contact.setEmail(rs.getString("Email"));
        contact.setPhone(rs.getString("Phone"));
        contact.setBirthday(rs.getTimestamp("Birth_Day").toLocalDateTime());
        contact.setAddress(rs.getString("Address"));
        contact.setPhone(rs.getString("Photo"));
        contact.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
        return contact;
    };

    @Override
    public Contact createContact(Contact contact) {
        String sql = "INSERT INTO MyPersonalCpntactManager_DB (id, First_Name, Last_Name, Email, Phone, Birth_Day, " +
                "Address, Photo, Create_Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getId());
            ps.setString(2, contact.getFirstName());
            ps.setString(3, contact.getLastName());
            ps.setString(4, contact.getEmail());
            ps.setString(5, contact.getPhone());
            ps.setString(6, String.valueOf(contact.getBirthday()));
            ps.setString(7, contact.getAddress());
            ps.setString(8, contact.getPhone());
            ps.setString(9, String.valueOf(contact.getCreateDate()));
            return ps;
        }, keyHolder);

        String selectSql = "SELECT * FROM MyPersonalCpntactManager_DB WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, contactItemRowMapper, contact.getId());

    }

    @Override
    public ContactDTO getContactById(String id) {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
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
