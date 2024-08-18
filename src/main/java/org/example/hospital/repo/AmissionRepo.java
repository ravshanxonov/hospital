package org.example.hospital.repo;

import org.example.hospital.entity.mainEntity.Amission;
import org.example.hospital.entity.status.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmissionRepo extends JpaRepository<Amission,Integer> {
    List<Amission> findAllByPatientIdOrderByIdDesc(int patient_id);
    List<Amission> findAllByStatus(PatientStatus status);
}
