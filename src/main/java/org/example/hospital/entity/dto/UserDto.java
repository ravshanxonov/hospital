package org.example.hospital.entity.dto;

public record UserDto(
        Integer userId,
        String firstname,
        String lastname,
        String phonenumber,
        String password,
        String passwordRepeat

){


}
