package org.VotingSystem.service;

import org.VotingSystem.model.OTP;
import org.VotingSystem.model.Voters;
import org.VotingSystem.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class OtpServices {
    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String generateOtp(){
        int otp=0;
        for(int i=0;i<6;i++)
        {
            int d=(int)(Math.random()*9)+1;
            otp=otp*10+d;
        }
        return String.valueOf(otp);
    }

    public void send(String mail, String otpValue){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("anpr28project@gmail.com");
        message.setSubject("Your Otp generated");
        message.setText("Your Otp is: "+otpValue);

        mailSender.send(message);
    }
    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }
    public void generateAndSendOtp(Voters v){
        String otpValue=generateOtp();
        String encodedOtp=hashString(otpValue);
        Timestamp now = Timestamp.from(Instant.now());
        int otpExpiryTime=1;
        Timestamp expires = Timestamp.from(Instant.now().plusSeconds(otpExpiryTime*60));

        OTP otp=new OTP();
        otp.setOtpValue(encodedOtp);
        otp.setEpicNo(v.getEpicId());
        otp.setCreatedAt(now);
        otp.setExpiresAt(expires);

        otpRepository.save(otp);

        send(v.getEmail(), otpValue);
    }
    @Scheduled(fixedRate=60000)
    public void deleteExpiredOTPs(){
        Timestamp now = Timestamp.from(Instant.now());
        List<OTP> expiredOTPs = otpRepository.findByExpiresAtBefore(now);
        otpRepository.deleteAll(expiredOTPs);
    }
    public boolean confirmOtp(String otpValue, String EpicId){
        OTP otp=otpRepository.findByEpicNo(EpicId);
        String encodedOtp=hashString(otpValue);
        if(otp.getOtpValue().equals(encodedOtp)){
            otpRepository.delete(otp);
            return true;
        }
        return false;
    }
}
