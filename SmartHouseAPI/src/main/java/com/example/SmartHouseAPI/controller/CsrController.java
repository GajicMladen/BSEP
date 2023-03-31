package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.exception.EntityNotFoundException;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.service.CsrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/csr")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CsrController {

    private final CsrService csrService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CsrDTO create(@RequestBody @Valid final Csr dto){

        return this.csrService.create(dto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<CsrDTO> getAll(){

        return this.csrService.getAll();
    }

    @GetMapping("/all/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<CsrDTO> getAll(@PathVariable final String status){

        return this.csrService.getAllByStatus(status);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CsrDTO get(@PathVariable final Long id) throws EntityNotFoundException {

        return this.csrService.getDTO(id);
    }

    @PutMapping("approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CsrDTO approve(@PathVariable final Long id, @RequestBody final String adminEmail) throws EntityNotFoundException {

        return this.csrService.approve(id, adminEmail);
    }

    @PutMapping("reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CsrDTO reject(@PathVariable final Long id, @RequestBody final String reason) throws EntityNotFoundException {

        return this.csrService.reject(id, reason);
    }
}
