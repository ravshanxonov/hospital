package org.example.hospital.repo;

import org.example.hospital.entity.helper.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Integer> {
}
