package com.example.traiviu_backend.controller;

import com.example.traiviu_backend.dto.admin.*;
import com.example.traiviu_backend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<AdminUserDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public AdminUserDto getUserById(@PathVariable UUID id) {
        return adminService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserDto createUser(@Valid @RequestBody AdminUserUpdateRequest request) {
        return adminService.createUser(request);
    }

    @PutMapping("/users/{id}")
    public AdminUserDto updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody AdminUserUpdateRequest request
    ) {
        return adminService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
    }

    @GetMapping("/clans")
    public List<AdminClanDto> getAllClans() {
        return adminService.getAllClans();
    }

    @GetMapping("/clans/{id}")
    public AdminClanDto getClanById(@PathVariable UUID id) {
        return adminService.getClanById(id);
    }

    @PutMapping("/clans/{id}")
    public AdminClanDto updateClan(
            @PathVariable UUID id,
            @Valid @RequestBody AdminClanUpdateRequest request
    ) {
        return adminService.updateClan(id, request);
    }

    @DeleteMapping("/clans/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClan(@PathVariable UUID id) {
        adminService.deleteClan(id);
    }

    @GetMapping("/clans/{clanId}/members")
    public List<AdminClanMemberDto> getClanMembers(@PathVariable UUID clanId) {
        return adminService.getClanMembers(clanId);
    }

    @DeleteMapping("/clans/{clanId}/members/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClanMember(
            @PathVariable UUID clanId,
            @PathVariable UUID userId
    ) {
        adminService.removeClanMember(clanId, userId);
    }
}