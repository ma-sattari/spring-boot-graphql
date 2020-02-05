package com.masoud.springbootgraphql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    private int id;
    private String name;
    private String phone;
    private String email;
    private String[] address;
}
