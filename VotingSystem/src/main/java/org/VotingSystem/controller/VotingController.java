package org.VotingSystem.controller;

import org.VotingSystem.model.Voters;
import org.VotingSystem.service.OtpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VotingController {

    @Autowired
    private VoterController voterController;

    @Autowired
    private OtpServices otpServices;

    @Autowired
    private PartyController partyController;

    @PostMapping("/generateOtp")
    public ResponseEntity<String> generateOtp(@RequestParam(value="EpicNo", required = true) String epicId,@RequestParam(value="Dob", required = true)String dob) {
        boolean isPresent = voterController.findVoter(epicId, dob);
        if (isPresent) {
            Voters v = voterController.findByEpicId(epicId);
            if (!v.isVoted()) {
                otpServices.generateAndSendOtp(v);
                //System.out.println("Otp sent");
                return ResponseEntity.ok("OTP generated");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You Have already voted");
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong Credentials");
    }
    @PostMapping("/validateOtp")
    public ResponseEntity<String > validateOtp(@RequestParam(value="EpicNo", required = true) String epicId, @RequestParam(value="Otp",required = true) String otp) {
        if((otpServices.confirmOtp(otp,epicId)))
        {
            Voters v= voterController.findByEpicId(epicId);
            v.setVoted(true);
            voterController.updateVoter(v);
            return ResponseEntity.ok("OTP validated");
        }
        else
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid OTP");
    }

    @PostMapping("/giveVote")
    public void giveVote(@RequestParam(value="Vote",required = true) String name){
        partyController.updateParty(name);
        System.out.println("Thank you for voting");
    }
}
