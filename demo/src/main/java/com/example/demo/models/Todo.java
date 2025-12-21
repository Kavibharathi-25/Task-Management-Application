package com.example.demo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

@Data
@Entity

public class Todo {
    @Id
    @GeneratedValue
    Long id;
    @NotNull
    @Schema(name="title",example="Spring boot")
   String title;

    boolean completed;




}
