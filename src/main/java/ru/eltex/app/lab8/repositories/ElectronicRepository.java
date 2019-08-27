package ru.eltex.app.lab8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eltex.app.lab1.Electronic;

import java.util.UUID;

@Repository
public interface ElectronicRepository extends JpaRepository<Electronic, UUID> {
}
