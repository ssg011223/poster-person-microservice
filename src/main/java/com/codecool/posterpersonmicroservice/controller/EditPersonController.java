package com.codecool.posterpersonmicroservice.controller;

import com.codecool.posterpersonmicroservice.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"${cross.origin.port.number}"})
@RequestMapping(path = "/profile/settings")
public class EditPersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity getPersonDetails(@RequestParam long requesterId) {
        return personService.getPersonDetails(requesterId); }

    @PutMapping
    public ResponseEntity editPerson(@RequestParam long requesterId,
                                     @RequestParam String id,
                                     @RequestParam(required = false, value = "profileImageId") MultipartFile newProfileImageRoute,
                                     @RequestParam(required = false, value = "profileBackgroundImageId") MultipartFile newProfileBackgroundImageRoute,
                                     @RequestParam(required = false, value = "username") String newUsername,
                                     @RequestParam(required = false, value = "description") String newBio) {
        return personService.editPerson(requesterId, id, newProfileImageRoute, newProfileBackgroundImageRoute, newUsername, newBio);
    }
}
