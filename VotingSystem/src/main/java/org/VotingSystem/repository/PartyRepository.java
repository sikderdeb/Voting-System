package org.VotingSystem.repository;

import org.VotingSystem.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Integer> {
    Optional<Party> findByName(String name);
}
