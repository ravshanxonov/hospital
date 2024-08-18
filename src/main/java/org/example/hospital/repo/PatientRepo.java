package org.example.hospital.repo;

import org.example.hospital.entity.mainEntity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PatientRepo extends JpaRepository<Patient,Integer> {
   List<Patient> findAllByOrderByIdAsc();
    @Query("SELECT u FROM Patient u WHERE LOWER(u.user.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Patient> searchUsersByFirstNameOrLastNameContains(@Param("search") String search);
}
