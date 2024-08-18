package org.example.hospital.repo;


import org.example.hospital.entity.helper.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String PhoneNumber);
}
