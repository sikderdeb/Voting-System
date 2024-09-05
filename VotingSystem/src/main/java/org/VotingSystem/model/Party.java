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
@Table(name= "party")
public class Party {

    @Id
    @Column(name= "Id")
    private String id;

    @Column(name= "name")
    private String name;

    @Column(name= "Total Votes")
    private int totalVotes;
}
