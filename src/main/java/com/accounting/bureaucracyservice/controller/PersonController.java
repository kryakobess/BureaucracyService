package com.accounting.bureaucracyservice.controller;

import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonFacade personFacade;

    @PostMapping
    public ResponseEntity<CitizenDto> createPerson(@RequestBody CitizenCreateDto citizenCreateDto) {
        return ResponseEntity.ok(personFacade.createPerson(citizenCreateDto));
    }
}
