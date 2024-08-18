package org.example.hospital.repo;

import org.example.hospital.entity.helper.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepo extends JpaRepository<Procedure,Integer> {
}
