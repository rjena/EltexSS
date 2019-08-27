package ru.eltex.app.lab8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eltex.app.lab2.Credentials;

import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credentials, UUID> {
}
