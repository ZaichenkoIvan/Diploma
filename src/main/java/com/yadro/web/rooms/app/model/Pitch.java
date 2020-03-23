package com.yadro.web.rooms.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Pitch {
    @GeneratedValue
    @Id
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 100, message = "Name must have at least 3 characters.")
    private String name;
    
    private int ball = 0;
    
    private int manish = 0;
    
    private String grass = "NO";
    
    private String projector = "NO";
    
    private String changingRoom = "NO";
    
    private String shower = "NO";
    
    private String wc = "NO";
       
    @ManyToOne
    @NotNull
    private Stadium stadium;
    
}