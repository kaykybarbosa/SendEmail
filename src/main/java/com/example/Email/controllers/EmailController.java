package com.example.Email.controllers;

import com.example.Email.Dtos.EmailDTO;
import com.example.Email.models.EmailModel;
import com.example.Email.services.EmailService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
public class EmailController {

    Logger logger = LogManager.getLogger(EmailModel.class);
    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO emailDTO){
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");

        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);

    }
    @GetMapping("/emails/{emailId}")
   public ResponseEntity<Object> getOndeEmail(@PathVariable(value="emailId") UUID emailId){
        Optional<EmailModel> emaiLModeLOptional = emailService.findById(emailId);
        if(!emaiLModeLOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(emaiLModeLOptional.get());
        }
    }
}