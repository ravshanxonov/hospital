package org.example.hospital.repo;

import org.example.hospital.entity.helper.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface specialityRepo extends JpaRepository<Speciality,Integer> {
}
