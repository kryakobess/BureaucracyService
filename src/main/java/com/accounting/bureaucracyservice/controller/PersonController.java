package com.accounting.bureaucracyservice.controller;

import com.accounting.bureaucracyservice.model.dto.ApiError;
import com.accounting.bureaucracyservice.model.dto.CitizenCreateDto;
import com.accounting.bureaucracyservice.model.dto.CitizenDto;
import com.accounting.bureaucracyservice.service.facade.PersonFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonFacade personFacade;

    @Operation(summary = "Create new citizen by provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citizen created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitizenDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
    })
    @PostMapping
    public ResponseEntity<CitizenDto> createPerson(@RequestBody CitizenCreateDto citizenCreateDto) {
        return ResponseEntity.ok(personFacade.createPerson(citizenCreateDto));
    }

    @Operation(summary = "Get information about citizen by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the citizen",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CitizenDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CitizenDto> getPersonById(@PathVariable long id) {
        return ResponseEntity.ok(personFacade.getPersonById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CitizenDto>> getPersons(
            @RequestParam(name = "ids", required = false) List<Long> ids,
            @RequestParam(name = "firstName", required = false) List<String> firstNames,
            @RequestParam(name = "secondName", required = false) List<String> secondNames,
            @RequestParam(name = "registrationAddressRegions", required = false) List<String> regions,
            @RequestParam(name = "registrationAddressCities", required = false) List<String> cities,
            @RequestParam(name = "registrationAddressStreets", required = false) List<String> streets,
            @RequestParam(name = "registrationAddressHouseNumbers", required = false) List<String> houseNumbers,
            @RequestParam(name = "registrationAddressApartments", required = false) List<String> apartments,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(personFacade.getCitizenPages(ids, firstNames, secondNames, regions, cities, streets, houseNumbers, apartments, pageable));
    }
}
