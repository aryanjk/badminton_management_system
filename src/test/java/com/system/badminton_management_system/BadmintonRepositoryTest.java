package com.system.badminton_management_system;

import com.system.badminton_management_system.Repo.BadmintonRepo;
import com.system.badminton_management_system.entity.Badminton;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BadmintonRepositoryTest {
    @Autowired
    private BadmintonRepo badmintonRepo;


    @Test
    @Order(1)
    public void savebadmintonTest() {

        Badminton badminton = Badminton.builder()
                .badmintonname("rak")
                .badmintoncontact("123454")
                .badmintonprice("98888888")
                .build();


        badmintonRepo.save(badminton);

        Assertions.assertThat(badminton.getBasket_ballId()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void updatebadmintonTest(){

        Badminton badminton = Badminton.builder()
                .badmintonname("rak")
                .badmintoncontact("123454")
                .badmintonprice("98888888")
                .build();


        badmintonRepo.save(badminton);

        Badminton badminton1 = badmintonRepo.findById(badminton.getBasket_ballId()).get();

        badminton1.setBadmintoncontact("13265");

        Badminton badmintonupdated  = badmintonRepo.save(badminton);

        Assertions.assertThat(badmintonupdated.getBadmintoncontact()).isEqualTo("85207410");

    }
}