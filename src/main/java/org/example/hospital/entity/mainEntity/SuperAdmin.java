package org.example.hospital.entity.mainEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hospital.entity.helper.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuperAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User user;
}
