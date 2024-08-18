package org.example.hospital.entity.mainEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hospital.entity.helper.User;

import java.lang.reflect.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Patient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
