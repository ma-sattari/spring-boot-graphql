package com.masoud.springbootgraphql.controller;

import com.masoud.springbootgraphql.entity.Person;
import com.masoud.springbootgraphql.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/addPerson")
    public String addPerson(@RequestBody List<Person> persons) {
        personRepository.saveAll(persons);

        return "Records inserted " + persons.size();
    }

    @GetMapping("/findAllPerson")
    public List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }
}
