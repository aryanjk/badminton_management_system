package com.system.badminton_management_system.Pojo;

import com.system.badminton_management_system.entity.Badminton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BadmintonPojo {
    private Integer fid;
    private String fname;
    private String fcontact;
    private  String fprice;
    private String flocation;
    private MultipartFile image;
    private MultipartFile image1;
    private MultipartFile image2;
    private String Description;


    public BadmintonPojo(Badminton badminton) {
        this.fid = badminton.getBadminton_Id();
        this.fname = badminton.getBadmintonname();
        this.fcontact= badminton.getBadmintoncontact();
        this.fprice = badminton.getBadmintonprice();
        this.flocation = badminton.getBadmintonlocation();
        this.Description = badminton.getDescription();

    }
}