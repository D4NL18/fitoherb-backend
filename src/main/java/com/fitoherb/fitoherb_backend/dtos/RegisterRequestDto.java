package com.fitoherb.fitoherb_backend.dtos;

public record RegisterRequestDto(String user_name, String email, String password, boolean isAdmin) {
}
