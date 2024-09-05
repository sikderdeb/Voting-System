package org.VotingSystem.repository;

import org.VotingSystem.model.Voters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotersRepository extends JpaRepository<Voters,String> {
    public Voters findByEpicIdAndDob(String epicId, String dob);
    public Voters findByEpicId(String epicId);
}
