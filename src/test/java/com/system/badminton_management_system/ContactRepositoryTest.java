package com.system.badminton_management_system;


import com.system.badminton_management_system.Repo.ContactRepo;
import com.system.badminton_management_system.entity.Contact;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepo contactRepo;


    @Test
    @Order(1)
    public void saveContactTest() {


        Contact contact = Contact.builder()
                .contactemail("a@g.com")
                .contactmessage("asd")
                .build();

        contactRepo.save(contact);

        Assertions.assertThat(contact.getContactId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getContactTest() {

        Contact contact = Contact.builder()
                .contactemail("a@g.com")
                .contactmessage("asd")
                .build();

        contactRepo.save(contact);

        Contact contactCreated = contactRepo.findById(contact.getContactId()).get();
        Assertions.assertThat(contactCreated.getContactId()).isEqualTo(contact.getContactId());

    }

    @Test
    @Order(3)
    public void getListOfContactTest(){
        Contact contact = Contact.builder()
                .contactemail("a@g.com")
                .contactmessage("asd")
                .build();

        contactRepo.save(contact);


        List<Contact> User = contactRepo.findAll();
        Assertions.assertThat(User.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    public void updateContactTest(){

        Contact contact = Contact.builder()
                .contactemail("a@g.com")
                .contactmessage("asd")
                .build();

        contactRepo.save(contact);

        Contact contact1  = contactRepo.findById(contact.getContactId()).get();

        contact1.setContactemail("b@g.com");

        Contact contactUpdated  = contactRepo.save(contact);

        Assertions.assertThat(contactUpdated.getContactemail()).isEqualTo("b@g.com");

    }


}