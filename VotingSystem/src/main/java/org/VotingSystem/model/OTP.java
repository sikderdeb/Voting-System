package org.VotingSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="otp")
public class OTP {
    @Id
    @Column(name="Epic No")
    private String EpicNo;

    @Column(name="Otp_Value")
    private String otpValue;

    @Column(name="Created At")
    private Timestamp createdAt;

    @Column(name="Expires At")
    private Timestamp expiresAt;
}
