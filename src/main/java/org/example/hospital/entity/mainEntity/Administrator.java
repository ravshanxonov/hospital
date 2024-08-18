package org.example.hospital.entity.mainEntity;

import jakarta.persistence.*;
import lombok.*;
import org.example.hospital.entity.helper.User;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Administrator  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   @OneToOne
    private User user;

}
