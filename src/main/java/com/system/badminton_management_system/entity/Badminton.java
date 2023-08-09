package com.system.badminton_management_system.entity;



import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Badminton")

public class Badminton {
    @Id
    @SequenceGenerator(name = "shb_product_seq_gen", sequenceName = "shb_product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "shb_product_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer badminton_Id;

    private String badmintonname;

    private String badmintonprice;

    private String badmintoncontact;

    private String badmintonlocation;

    private String badmintonimage;
    private String badmintonimage1;
    private String badmintonimage2;
@Column(length=1000)
    private String Description;

    @Transient
    private String imageBase64;

    @Transient
    private String image1Base64;


    @Transient
    private String image2Base64;

}
