package com.masoud.springbootgraphql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
@Data
public class Book implements Serializable {

    @Id
    private int isn;
    private String title;
    private String publisher;
    private String[] authors;
    private String publishedDate;
}
