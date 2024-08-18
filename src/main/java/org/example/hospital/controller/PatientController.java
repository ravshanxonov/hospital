package org.example.hospital.controller;
import lombok.RequiredArgsConstructor;
import org.example.hospital.entity.mainEntity.Amission;
import org.example.hospital.repo.AmissionRepo;
import org.example.hospital.repo.PatientRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/patient")
@Controller
@RequiredArgsConstructor
public class PatientController {
    private final AmissionRepo amissionRepo;
    private final PatientRepo patientRepo;


    @GetMapping("")
    public String show(Model model){
        List<Amission> all = amissionRepo.findAllByPatientIdOrderByIdDesc(2);
        model.addAttribute("amissions",all);
        model.addAttribute("patient",patientRepo.findById(2).get());
        return "patient/patient";

    }

}
