package com.codecool.posterpersonmicroservice.service;

import com.codecool.posterpersonmicroservice.model.Person;
import com.codecool.posterpersonmicroservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    @Autowired
    private MediaServiceCaller mediaServiceCaller;

    private final PersonRepository personRepository;

    public ResponseEntity getPersonById(String id, long requesterId) {
        long newId = Long.parseLong(String.valueOf(id));
        Optional<Person> person = personRepository.findById(newId);
        Map<Object, Object> result = new HashMap();

        if (requesterId == newId && person.isPresent()) {
            result.put("person", person.get());
            result.put("isLoggedPerson", true);
            return ResponseEntity.ok(result);
        }
        else if (person.isPresent()) {
            result.put("person", person.get());
            result.put("isLoggedPerson", false);
            return ResponseEntity.ok(result);
        }

            return ResponseEntity.badRequest().body("User not found!");
    }

    public Optional<Person> getUser(String username) {
        return personRepository.findByUsername(username);
    }

    public ResponseEntity editPerson(long requesterId, String id, MultipartFile newProfileImage, MultipartFile newProfileBackgroundImage, String newUsername, String newBio) {
        long newId = Long.parseLong(String.valueOf(id));
        Optional<Person> person = personRepository.findById(newId);

        if (requesterId == newId && person.isPresent()) {
            Person personToEdit = person.get();

            if (newUsername != null)
                personToEdit.setUsername(newUsername);

            if (newBio != null)
                personToEdit.setDescription(newBio);

            if (newProfileImage != null) {
                ResponseEntity<Long> responseEntity = mediaServiceCaller.submitImage(requesterId, newProfileImage);
                personToEdit.setProfileImageId(responseEntity.getBody() != null ? responseEntity.getBody() : 0);
            }

            if (newProfileBackgroundImage != null) {
                ResponseEntity<Long> responseEntity = mediaServiceCaller.submitImage(requesterId, newProfileImage);
                personToEdit.setProfileBackgroundImageId(responseEntity.getBody() != null ? responseEntity.getBody() : 0);
            }

            personRepository.save(personToEdit);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body("Token or Id are not valid!");
    }

    public Collection<Person> searchPeople(String searchPhrase) { return personRepository.findAllByUsernameMatches(searchPhrase); }

    public Person getPersonByUsername(String username) {
        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isPresent())
            return person.get();
        else
            throw new IllegalStateException("Username not found!");
    }

    public ResponseEntity getPersonDetails(long requesterId) {
        Optional<Person> person = personRepository.findById(requesterId);

        if (person.isPresent())
            return ResponseEntity.ok(person.get());

        return ResponseEntity.badRequest().body("Username not found!");
    }

    public ResponseEntity getAllPeopleExpectLoggedPerson(long requesterId) {
            return ResponseEntity.ok(personRepository.findAllExpectOnePerson(requesterId));
    }
}
