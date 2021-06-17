package com.codecool.posterpersonmicroservice.repository;

import com.codecool.posterpersonmicroservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsername(String username);

    Collection<Person> findAllByUsernameMatches(String searchPhrase);

    @Query("SELECT p FROM Person as p WHERE p.id <> :id")
    Collection<Person> findAllExpectOnePerson(long id);
}
