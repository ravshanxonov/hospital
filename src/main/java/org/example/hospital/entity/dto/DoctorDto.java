package org.example.hospital.entity.dto;

public record DoctorDto(
        Integer userId,
        String firstname,
        String lastname,
        String phonenumber,
        String password,
        String passwordRepeat,
        Integer roomId,
        Integer specialityId


){


}
