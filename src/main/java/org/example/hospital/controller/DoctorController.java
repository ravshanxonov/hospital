package org.example.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.example.hospital.entity.helper.Procedure;
import org.example.hospital.entity.mainEntity.Amission;
import org.example.hospital.entity.status.PatientStatus;
import org.example.hospital.repo.AmissionRepo;
import org.example.hospital.repo.ProcedureRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final AmissionRepo amissionRepo;
    private final ProcedureRepo procedureRepo;

    @GetMapping("")
    public String show(Model model){
        List<Amission> all = amissionRepo.findAllByStatus(PatientStatus.waiting);
        model.addAttribute("amissions",all);
        return "/doctor/doctor";
    }
    @GetMapping("/pro")
    public String pro(Model model, @RequestParam Integer id ,@RequestParam Integer current){
        Amission amission = amissionRepo.findById(id).get();
        List<Amission> all = amissionRepo.findAllByStatus(PatientStatus.waiting);
        model.addAttribute("amissions",all);
        model.addAttribute("current",amission);
        amission.setStatus(PatientStatus.process);
        amissionRepo.save(amission);
        return "/doctor/doctorPro";
    }

    @PostMapping("/pro")
    public String prr(@RequestParam("amissionId") Integer amissionId,
                      @RequestParam("name") List<String> names,
                      @RequestParam("price") List<Integer> prices) {
        Optional<Amission> optionalAmission = amissionRepo.findById(amissionId);
        if (optionalAmission.isPresent()) {
            Amission amission = optionalAmission.get();
            List<Procedure> procedures = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                Procedure procedure = new Procedure(names.get(i), prices.get(i));
                procedureRepo.save(procedure);
                procedures.add(procedure);
            }
            amission.setProcedures(procedures);
            amission.setStatus(PatientStatus.finish);
            amissionRepo.save(amission);
            return "redirect:/doctor";
        } else {
            RuntimeException runtimeException = new RuntimeException();
            return runtimeException.getMessage();
        }
    }
}
