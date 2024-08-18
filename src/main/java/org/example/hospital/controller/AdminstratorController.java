package org.example.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.example.hospital.entity.dto.UserDto;
import org.example.hospital.entity.helper.User;
import org.example.hospital.entity.mainEntity.Amission;
import org.example.hospital.entity.mainEntity.Doctor;
import org.example.hospital.entity.mainEntity.Patient;
import org.example.hospital.entity.status.PatientStatus;
import org.example.hospital.repo.AmissionRepo;
import org.example.hospital.repo.DoctorRepo;
import org.example.hospital.repo.PatientRepo;
import org.example.hospital.repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/adminstrator")
@RequiredArgsConstructor
public class AdminstratorController {


    private final PatientRepo patientRepo;
    private final AmissionRepo amissionRepo;
    private final DoctorRepo doctorRepo;
    private final UserRepo userRepo;

    @GetMapping("")
    public String show(Model model){
        List<Patient> all = patientRepo.findAll();
        model.addAttribute("patients",all);
        return "/adminstrator/adminstrator";
    }
    @GetMapping("/info")
    public String info(@RequestParam Integer id, Model model){
        List<Amission> allByPatientId = amissionRepo.findAllByPatientIdOrderByIdDesc(id);
        Patient patient = patientRepo.findById(id).get();
        List<Patient> all = patientRepo.findAll();
        List<Doctor> all1 = doctorRepo.findAll();
        model.addAttribute("doctors",all1);
        model.addAttribute("patients",all);
        model.addAttribute("amissions",allByPatientId );
        model.addAttribute("patient",patient);
        return "/adminstrator/adminstratorArhiv";
    }

@GetMapping("/addPatient")
public String addPatient(){
        return "/adminstrator/addPatient";
}


@PostMapping("/addPatient")
public String addPatientt(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) throws IOException {
    if (userRepo.existsByPhoneNumber(userDto.phonenumber())) {
        redirectAttributes.addFlashAttribute("error","Bu phone number mavjud");
        return "redirect:/addPatient";
    }
    if (!(userDto.password().equals(userDto.passwordRepeat()))){
        redirectAttributes.addFlashAttribute("error","Passwords kiritishda xatolik bo'ldi");
        return "redirect:/addPatient";
    }

    User build = User.builder()
            .firstName(userDto.firstname())
            .lastName(userDto.lastname())
            .phoneNumber(userDto.phonenumber())
            .password(userDto.password())
            .build();
    userRepo.save(build);
    Patient build1 = Patient.builder()
            .user(build)
            .build();
patientRepo.save(build1);

    return "redirect:/adminstrator";
}
    @GetMapping("/search")
    public String search(@RequestParam String search,Model model){
        List<Patient> patients = patientRepo.findAll();
        if(search != null){
            patients=patientRepo.searchUsersByFirstNameOrLastNameContains(search);
            if (patients.isEmpty()){
                model.addAttribute("addPatient","addPatient");
            }
        }
        model.addAttribute("search",search);
        model.addAttribute("patients",patients);
        return "/adminstrator/adminstrator";
    }

    @PostMapping("/addAmission")
    public String saveAdmission(@RequestParam Integer doctorId,
                                @RequestParam LocalDateTime date,
                                @RequestParam String description,
                                @RequestParam Integer patientId) {
        Amission amission = new Amission();
        amission.setDoctor(doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found")));
        amission.setPatient(patientRepo.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found")));
        amission.setPlan(date);
        amission.setStatus(PatientStatus.plan);
        amission.setDescription(description);
        amissionRepo.save(amission);

        return "redirect:/adminstrator/info?id="+patientId;
    }

    @PostMapping("/waiting")
    public String waiting(@RequestParam Integer id){
        Amission amission = amissionRepo.findById(id).get();
        amission.setStatus(PatientStatus.waiting);
        amission.setCame(LocalDateTime.now());
        amissionRepo.save(amission);
        return "redirect:/adminstrator";
    }
}
