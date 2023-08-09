package com.system.badminton_management_system.Service;

import com.system.badminton_management_system.Pojo.BadmintonPojo;
import com.system.badminton_management_system.entity.Badminton;

import java.io.IOException;
import java.util.List;

public interface BadmintonService {
    BadmintonPojo savebadminton(BadmintonPojo badmintonPojo) throws IOException;

    Badminton fetchById(Integer id);

    List<Badminton> fetchAll();

    void deleteById(Integer id);
}
