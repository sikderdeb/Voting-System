package org.VotingSystem.repository;


import org.VotingSystem.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OtpRepository extends JpaRepository<OTP, String> {
    @Query("select o from OTP o where o.EpicNo = :epicId")
    OTP findByEpicNo(@Param("epicId") String epicId);

    List<OTP> findByExpiresAtBefore(Timestamp currentTime);
}
