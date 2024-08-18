package org.example.hospital.repo;

import org.example.hospital.entity.mainEntity.Doctor;
import org.example.hospital.entity.mainEntity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    List<Doctor> findAllByOrderByIdAsc();



}
