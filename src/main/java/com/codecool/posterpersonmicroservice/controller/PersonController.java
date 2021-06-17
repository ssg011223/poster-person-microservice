package com.codecool.posterpersonmicroservice.controller;

import com.codecool.posterpersonmicroservice.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
@CrossOrigin(origins = {"${cross.origin.port.number}"})
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity getPerson(@RequestParam long requesterId) { return personService.getPersonDetails(requesterId); }

    @GetMapping("/{id}")
    public ResponseEntity getPersonById(@RequestParam long requesterId,
                                    @PathVariable("id") String profileId) {
        return personService.getPersonById(profileId, requesterId); }
}
