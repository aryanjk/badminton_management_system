package com.system.badminton_management_system.Repo;

import com.system.badminton_management_system.entity.Badminton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadmintonRepo extends JpaRepository <Badminton, Integer>{
}
