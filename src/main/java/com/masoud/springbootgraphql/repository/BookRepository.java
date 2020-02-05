package com.masoud.springbootgraphql.repository;

import com.masoud.springbootgraphql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
}
