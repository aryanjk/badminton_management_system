package com.system.badminton_management_system.Pojo;


import com.system.badminton_management_system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserPojo {
    private Integer id;
    private String email;
    private  String mobile_no;
    private String password;
    private String name;
    private String address;



    public UserPojo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.mobile_no = user.getMobileNo();
        this.name = user.getName();
        this.password = user.getPassword();
        this.address = user.getAddress();

    }
}


