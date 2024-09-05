package org.VotingSystem.controller;

import jakarta.mail.Part;
import org.VotingSystem.model.Party;
import org.VotingSystem.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PartyController {
    @Autowired
    private PartyRepository partyRepository;

    public void updateParty(String name){
        Optional<Party> party = partyRepository.findByName(name);
        if(party.isPresent()){
            Party partyToUpdate = party.get();
            int votes=partyToUpdate.getTotalVotes()+1;
            partyToUpdate.setTotalVotes(votes);
            partyRepository.save(partyToUpdate);
        }
    }
}
