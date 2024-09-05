package org.VotingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voters")
public class Voters {
    @Id
    @Column(name = "EPIC NO")
    private String epicId;

    @Column(name= "DOB")
    private String dob;

    @Column(name = "Email")
    private String email;

    @Column(name = "Voted")
    private boolean voted;

    @Override
    public String toString() {
        return epicId + "\t" + dob + "\t" + email + "\t" + voted;
    }
}
