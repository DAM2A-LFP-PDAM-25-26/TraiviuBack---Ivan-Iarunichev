package com.example.traiviu_backend.repository;

import com.example.traiviu_backend.model.ListEntity;
import com.example.traiviu_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ListRepository extends JpaRepository<ListEntity, UUID> {
    List<ListEntity> findByUser(User user);
}
