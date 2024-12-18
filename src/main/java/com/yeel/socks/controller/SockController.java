package com.yeel.socks.controller;

import com.yeel.socks.dto.SockDTO;
import com.yeel.socks.model.Sock;
import com.yeel.socks.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("api/socks")
@RestController
@RequiredArgsConstructor
public class SockController {
    private final SockService sockService;
    @PostMapping("income")
    @Operation(summary = "Register socks income", description = "Adds socks to the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socks added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sock.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    public Sock income(@RequestBody SockDTO sockDTO) {
        log.info("Register income | " + sockDTO.toString());
        return sockService.registerIncome(
            sockDTO
        );
    }

    @PostMapping("outcome")
    public Sock outcome(@RequestBody SockDTO sockDTO) {
        log.info("Register outcome | " + sockDTO.toString());
        return sockService.registerOutcome(
                sockDTO
        );
    }

    @GetMapping
    public Integer getSocks(@RequestParam(required = false) String color,
                            @RequestParam(required = false) String operator,
                            @RequestParam(required = false) Integer percentage) {
        log.info("Get socks");
        return sockService.getSocksCount(color, operator, percentage);
    }

    @PutMapping("{id}")
    public Sock update(@PathVariable Long id,
                       @RequestBody SockDTO sockDTO) {
        log.info("Update sock with id = " + id + " | " + sockDTO.toString());
        return sockService.update(
                id,
                sockDTO
        );
    }

    @PostMapping("batch")
    public ResponseEntity<String> batch(@RequestParam(name = "file") MultipartFile multipartFile) {
        log.info("Register batch");

        sockService.registerBatch(multipartFile);
        return ResponseEntity.ok("Success");
    }

}
