package org.example.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.example.hospital.entity.dto.DoctorDto;
import org.example.hospital.entity.dto.UserDto;
import org.example.hospital.entity.helper.Room;
import org.example.hospital.entity.helper.Speciality;
import org.example.hospital.entity.helper.User;
import org.example.hospital.entity.mainEntity.Doctor;
import org.example.hospital.entity.mainEntity.Patient;
import org.example.hospital.repo.DoctorRepo;
import org.example.hospital.repo.PatientRepo;
import org.example.hospital.repo.RoomRepo;
import org.example.hospital.repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepo userRepo;
    private final PatientRepo patientRepo;
    private final org.example.hospital.repo.specialityRepo specialityRepo;
    private final RoomRepo roomRepo;
    private final DoctorRepo doctorRepo;

    @GetMapping("")
    public String show()
    {return "/admin/superadmin";}

    @GetMapping("/patient")
    public String showp(Model model) {
        model.addAttribute("patients", patientRepo.findAllByOrderByIdAsc());
        return "/admin/adminPatient";
    }
    @GetMapping("/addPatient")
    public String addPatient() {
        return "/adminstrator/addPatient";
    }
    @PostMapping("/addPatient")
    public String addPatientt(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) throws IOException {
        if (userRepo.existsByPhoneNumber(userDto.phonenumber())) {
            redirectAttributes.addFlashAttribute("error", "Bu phone number mavjud");
            return "redirect:/admin/addPatient";
        }
        if (!(userDto.password().equals(userDto.passwordRepeat()))) {
            redirectAttributes.addFlashAttribute("error", "Passwords kiritishda xatolik bo'ldi");
            return "redirect:/admin/addPatient";
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
        return "redirect:/admin/patient";
    }
    @GetMapping("/editPatient")
    public String editPatient(@RequestParam Integer id, Model model) {
        model.addAttribute("patient", patientRepo.findById(id).get());
        return "/admin/patient/editPatient";
    }
    @PostMapping("/editPatient")
    public String editPatientt(RedirectAttributes redirectAttributes,@ModelAttribute UserDto userDto, @RequestParam Integer id) {
        if (userRepo.existsByPhoneNumber(userDto.phonenumber()) && !(userRepo.findByPhoneNumber(userDto.phonenumber()).getPhoneNumber().equals(userDto.phonenumber())) ) {
            redirectAttributes.addFlashAttribute("error", "Bu phone number mavjud");
            return "redirect:/admin/editPatient?id="+id;
        }
        Patient patient = patientRepo.findById(id).get();
        User user = userRepo.findById(userDto.userId()).get();
        user.setFirstName(userDto.firstname());
        user.setLastName(userDto.lastname());
        user.setPhoneNumber(userDto.phonenumber());
        user.setPassword(userDto.password());
        userRepo.save(user);
        patient.setUser(user);
        patientRepo.save(patient);
        return "redirect:/admin/patient";
    }
    @PostMapping("/deletePatient")
    public String deletePatient(@RequestParam Integer id) {
        try {
            patientRepo.deleteById(id);
            return "redirect:/admin/patient";
        }catch (Exception e){
            return "redirect:/admin/patient";
        }
    }


    @GetMapping("/doctor")
    public String showd(Model model) {
        model.addAttribute("doctors", doctorRepo.findAllByOrderByIdAsc());
        return "/admin/adminDoctor";
    }
    @GetMapping("/addDoctor")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("specialities", specialityRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());
        return "/admin/doctor/addDoctor";
    }
    @PostMapping("/addDoctor")
    public String addDoctor(@ModelAttribute DoctorDto doctorDto, RedirectAttributes redirectAttributes) {
        if (userRepo.existsByPhoneNumber(doctorDto.phonenumber())) {
            redirectAttributes.addFlashAttribute("error", "Bu phone number mavjud");
            return "redirect:/admin/addDoctor";
        }
        if (!(doctorDto.password().equals(doctorDto.passwordRepeat()))) {
            redirectAttributes.addFlashAttribute("error", "Passwords kiritishda xatolik bo'ldi");
            return "redirect:/admin/addDoctor";
        }

        User build = User.builder()
                .firstName(doctorDto.firstname())
                .lastName(doctorDto.lastname())
                .phoneNumber(doctorDto.phonenumber())
                .password(doctorDto.password())
                .build();
        userRepo.save(build);
        Doctor doctor = Doctor.builder()
                .user(build)
                .room(roomRepo.findById(doctorDto.roomId()).get())
                .speciality(specialityRepo.findById(doctorDto.specialityId()).get())
                .build();
        doctorRepo.save(doctor);
        return "redirect:/admin/doctor";
    }
    @GetMapping("/editDoctor")
    public String editDoctor(@RequestParam Integer id,Model model){
        Doctor doctor = doctorRepo.findById(id).get();
        model.addAttribute("doctor",doctor);
        List<Speciality> allS=new ArrayList<>();
        List<Room> allR=new ArrayList<>();
        for (Speciality speciality : specialityRepo.findAll()) {
            if (speciality.getId()!=doctor.getSpeciality().getId()){
            allS.add(speciality);
            }
        }
        for (Room room : roomRepo.findAll()) {
            if (room.getId()!=doctor.getRoom().getId()){
                allR.add(room);
            }
        }
        model.addAttribute("specialities",allS);
        model.addAttribute("rooms",allR);
        return "/admin/doctor/editDoctor";
    }
    @PostMapping("/editDoctor")
    public String editPatient(RedirectAttributes redirectAttributes,@ModelAttribute DoctorDto doctorDto, @RequestParam Integer id) {
        if (userRepo.existsByPhoneNumber(doctorDto.phonenumber()) && !(userRepo.findByPhoneNumber(doctorDto.phonenumber()).getPhoneNumber().equals(doctorDto.phonenumber())) ) {
            redirectAttributes.addFlashAttribute("error", "Bu phone number mavjud");
            return "redirect:/admin/editDoctor?id="+id;
        }

        Doctor doctor = doctorRepo.findById(id).get();
        User user = userRepo.findById(doctorDto.userId()).get();
        user.setFirstName(doctorDto.firstname());
        user.setLastName(doctorDto.lastname());
        user.setPhoneNumber(doctorDto.phonenumber());
        user.setPassword(doctorDto.password());
        userRepo.save(user);
        doctor.setUser(user);
        doctor.setRoom(roomRepo.findById(doctorDto.roomId()).get());
        doctor.setSpeciality(specialityRepo.findById(doctorDto.specialityId()).get());
        doctorRepo.save(doctor);
        return "redirect:/admin/doctor";
    }
    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam Integer id) {
        try {
            doctorRepo.deleteById(id);
            return "redirect:/admin/doctor";
        }catch (Exception e){
            return "redirect:/admin/doctor";
        }
    }


    @GetMapping("/room")
    public String room(Model model){
        model.addAttribute("rooms",roomRepo.findAll());
        return "/admin/adminRoom";
    }
    @GetMapping("/addRoom")
    private String addRoom(){
      return "/admin/room/addRoom";
    }
    @PostMapping("/addRoom")
    private String addRoomms(@RequestParam String name){
        roomRepo.save(Room.builder().name(name).build());
        return "redirect:/admin/room";
    }
    @GetMapping("/editRoom")
    private String addRoodm(@RequestParam Integer id,Model model){
        model.addAttribute("room",roomRepo.findById(id).get());
      return "/admin/room/editRoom";
    }
    @PostMapping("/editRoom")
    private String addRoomm(@RequestParam String name,@RequestParam Integer id){
        Room room = roomRepo.findById(id).get();
        room.setName(name);
        roomRepo.save(room);
        return "redirect:/admin/room";
    }
    @PostMapping("/deleteRoom")
    private String deleteRoom(@RequestParam Integer id){
        try {
            Room room = roomRepo.findById(id).get();
            roomRepo.delete(room);
            return "redirect:/admin/room";
        }catch (Exception e){
            return "redirect:/admin/room";
        }

    }



    @GetMapping("/speciality")
    public String spe(Model model){
        model.addAttribute("speciality",specialityRepo.findAll());
        return "/admin/adminSpeciality";
    }
    @GetMapping("/addSpeciality")
    private String addRooma(){
      return "/admin/speciality/addSpeciality";
    }
    @PostMapping("/addSpeciality")
    private String addRoommas(@RequestParam String name){
        specialityRepo.save(Speciality.builder().name(name).build());
        return "redirect:/admin/speciality";
    }
    @GetMapping("/editRoom")
    private String addRosodm(@RequestParam Integer id,Model model){
        model.addAttribute("speciality",specialityRepo.findById(id).get());
      return "/admin/speciality/editSpeciality";
    }
    @PostMapping("/editSpeciality")
    private String addsRoomm(@RequestParam String name,@RequestParam Integer id){
        Speciality speciality = specialityRepo.findById(id).get();
        speciality.setName(name);
        specialityRepo.save(speciality);
        return "redirect:/admin/speciality";
    }
    @PostMapping("/deleteSpeciality")
    private String deleteRaoom(@RequestParam Integer id){
        try {
         specialityRepo.deleteById(id);
            return "redirect:/admin/speciality";
        }catch (Exception e){
            return "redirect:/admin/speciality";
        }

    }








}