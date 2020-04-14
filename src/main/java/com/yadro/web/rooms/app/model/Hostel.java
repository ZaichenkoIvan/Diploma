package com.yadro.web.rooms.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Hostel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max = 100, message = "Name must have at least 3 characters.")
    private String name;

    private String address;

    @NotNull
    @ManyToOne
    private University university;
}