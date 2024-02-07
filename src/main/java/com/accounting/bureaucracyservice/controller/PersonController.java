package com.accounting.bureaucracyservice.controller;

import com.accounting.bureaucracyservice.model.dto.*;
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
                            schema = @Schema(implementation = ApiError.class)) })
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
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<CitizenDto> getPersonById(@PathVariable long id) {
        return ResponseEntity.ok(personFacade.getPersonById(id));
    }

    @Operation(summary = "Get all citizens filtered and pageable")
    @GetMapping
    public ResponseEntity<Page<CitizenDto>> getPersons(
            CitizenPageableDto citizenPageableDto,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(personFacade.getCitizenPages(citizenPageableDto, pageable));
    }

    @Operation(summary = "Add address to existing citizen by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address linked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesGetDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Citizen with given id is not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @PostMapping("/{id}/address")
    public ResponseEntity<AddressesGetDto> addAddress(@PathVariable Long id, @RequestBody AddressCreateDto addressCreateDto) {
        return ResponseEntity.ok(personFacade.addAddress(id, addressCreateDto));
    }

    @Operation(summary = "Get all citizen addresses by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesGetDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Citizen with given id is not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @GetMapping("/{id}/address")
    public ResponseEntity<AddressesGetDto> getAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(personFacade.getAddress(id));
    }

    @Operation(summary = "Unlink address from citizen by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address unlinked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesGetDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @DeleteMapping("/{id}/address/{addressId}/unlink")
    public ResponseEntity<AddressesGetDto> unlinkAddressFromCitizen(@PathVariable Long id, @PathVariable Long addressId) {
        return ResponseEntity.ok(personFacade.unlinkAddress(id, addressId));
    }

    @Operation(summary = "Change citizen address specified by addressId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesGetDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @PutMapping("/{id}/address/{addressId}")
    public ResponseEntity<AddressesGetDto> changeAddressForCitizen(
            @PathVariable Long id,
            @PathVariable Long addressId,
            @RequestBody AddressCreateDto addressCreateDto
    ) {
        return ResponseEntity.ok(personFacade.changeAddress(id, addressId, addressCreateDto));
    }

    @Operation(summary = "Add citizen document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocumentDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @PostMapping("/{id}/document")
    public ResponseEntity<DocumentDto> addDocument(@PathVariable Long id, @RequestBody DocumentCreateDto dto) {
        return ResponseEntity.ok(personFacade.addDocument(id, dto));
    }

    @Operation(summary = "Change citizen document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocumentDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @PutMapping("/{id}/document")
    public ResponseEntity<DocumentDto> changeDocument(
            @PathVariable Long id,
            @RequestBody DocumentCreateDto createDto
    ) {
        return ResponseEntity.ok(personFacade.changeDocument(id, createDto));
    }

    @Operation(summary = "Get citizen documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocumentsGetDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @GetMapping("/{id}/document")
    public ResponseEntity<DocumentsGetDto> getDocuments(@PathVariable Long id) {
        return ResponseEntity.ok(personFacade.getDocuments(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitizenDto> changeCitizenInfo(@PathVariable Long id, @RequestBody ChangeCitizenDto dto) {
        return ResponseEntity.ok(personFacade.changeCitizenInfo(id, dto));
    }

    @GetMapping("/getByNameAndDocument")
    public ResponseEntity<CitizenInfoResponseDto> existsByNameAndDocument(CitizenInfoDto dto) {
        return ResponseEntity.ok(personFacade.getCitizenByInfo(dto));
    }
}
