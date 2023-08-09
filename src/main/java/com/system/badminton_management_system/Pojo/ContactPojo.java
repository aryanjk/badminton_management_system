package com.system.badminton_management_system.Pojo;

import com.system.badminton_management_system.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ContactPojo {
    private Integer contactId;
    private String contactname;
    private String contactemail;
    private  String contactsubject;
    private String contactmessage;

    public ContactPojo(Contact contact) {
        this.contactId = contact.getContactId();
        this.contactname = contact.getContactname();
        this.contactemail = contact.getContactemail();
        this.contactsubject= contact.getContactsubject();
        this.contactmessage= contact.getContactmessage();


    }
}