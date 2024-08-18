package org.example.hospital.entity.mainEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hospital.entity.helper.Procedure;
import org.example.hospital.entity.status.PatientStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Amission {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private LocalDateTime plan;

   private LocalDateTime came;

   private String description;

   private PatientStatus status;

   @ManyToOne(cascade = CascadeType.ALL)
   private Patient patient;

   @ManyToOne(cascade = CascadeType.ALL)
   private Doctor doctor;

   @OneToMany
   private List<Procedure> procedures =new ArrayList<>();

   @ManyToOne
   private Administrator administrator;

   public Integer getTotalPrice() {
      Integer total=0;
      for (Procedure procedure : procedures) {
         total+=procedure.getPrice();
      }
      return total;
   }
   public  String compareDateTime(LocalDateTime plan, LocalDateTime came) {
      if (came.isAfter(plan)) {
         return "Vaqtida kelgan";
      } else {
         return "Kech kelgan";
      }
   }
}
