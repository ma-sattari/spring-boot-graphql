package com.masoud.springbootgraphql.repository;

import com.masoud.springbootgraphql.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
