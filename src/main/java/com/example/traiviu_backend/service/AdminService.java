package com.example.traiviu_backend.service;

import com.example.traiviu_backend.dto.admin.*;
import com.example.traiviu_backend.model.Clan;
import com.example.traiviu_backend.model.ClanMember;
import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.model.enums.ClanStatus;
import com.example.traiviu_backend.repository.ClanMemberRepository;
import com.example.traiviu_backend.repository.ClanRepository;
import com.example.traiviu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ClanRepository clanRepository;
    private final ClanMemberRepository clanMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminUserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapUserToDto(user);
    }

    @Transactional
    public AdminUserDto createUser(AdminUserUpdateRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode("changeme123"))
                .displayName(request.displayName().trim())
                .role(normalizeRole(request.role()))
                .blocked(request.blocked())
                .build();

        return mapUserToDto(userRepository.save(user));
    }

    @Transactional
    public AdminUserDto updateUser(UUID id, AdminUserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newEmail = request.email().trim().toLowerCase();

        if (!user.getEmail().equalsIgnoreCase(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("El email ya está registrado");
        }

        user.setEmail(newEmail);
        user.setDisplayName(request.displayName().trim());
        user.setRole(normalizeRole(request.role()));
        user.setBlocked(request.blocked());

        return mapUserToDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<AdminClanDto> getAllClans() {
        return clanRepository.findAll().stream()
                .map(this::mapClanToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminClanDto getClanById(UUID id) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));
        return mapClanToDto(clan);
    }

    @Transactional
    public AdminClanDto updateClan(UUID id, AdminClanUpdateRequest request) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        clan.setName(request.name().trim());
        clan.setStatus(ClanStatus.valueOf(request.status().trim().toUpperCase()));

        return mapClanToDto(clanRepository.save(clan));
    }

    @Transactional
    public void deleteClan(UUID id) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));
        clanMemberRepository.deleteByClanId(id);
        clanRepository.delete(clan);
    }

    @Transactional(readOnly = true)
    public List<AdminClanMemberDto> getClanMembers(UUID clanId) {
        clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        List<ClanMember> members = clanMemberRepository.findByIdClanId(clanId);

        return members.stream()
                .map(member -> {
                    User user = userRepository.findById(member.getId().getUserId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                    return new AdminClanMemberDto(
                            user.getId(),
                            user.getEmail(),
                            user.getDisplayName(),
                            member.getRole(),
                            member.getJoinedAt()
                    );
                })
                .toList();
    }

    @Transactional
    public void removeClanMember(UUID clanId, UUID userId) {
        ClanMember member = clanMemberRepository.findByIdClanIdAndIdUserId(clanId, userId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado en el clan"));

        clanMemberRepository.delete(member);

        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan no encontrado"));

        clan.setMembersCount(Math.max(0, clan.getMembersCount() - 1));
        clanRepository.save(clan);
    }

    private String normalizeRole(String role) {
        String normalized = role == null ? "" : role.trim().toUpperCase();

        if (!normalized.equals("USER") && !normalized.equals("ADMIN")) {
            throw new RuntimeException("Rol no válido. Solo se permite USER o ADMIN");
        }

        return normalized;
    }

    private AdminUserDto mapUserToDto(User user) {
        return new AdminUserDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getRole(),
                user.isBlocked(),
                user.getCreatedAt(),
                user.getLastLoginAt()
        );
    }

    private AdminClanDto mapClanToDto(Clan clan) {
        return new AdminClanDto(
                clan.getId(),
                clan.getName(),
                clan.getInviteCode(),
                clan.getOwnerId(),
                clan.getCreatedAt(),
                clan.getStatus().name(),
                clan.getMembersCount()
        );
    }
}