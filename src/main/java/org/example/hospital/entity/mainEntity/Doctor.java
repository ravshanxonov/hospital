package org.example.hospital.entity.mainEntity;

import jakarta.persistence.*;
import lombok.*;
import org.example.hospital.entity.helper.Room;
import org.example.hospital.entity.helper.Speciality;
import org.example.hospital.entity.helper.User;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Speciality speciality;
    @ManyToOne
    private Room room;
    @OneToOne
    private User user;


}
