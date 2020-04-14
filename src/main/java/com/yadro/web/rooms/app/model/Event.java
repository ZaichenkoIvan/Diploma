package com.yadro.web.rooms.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(name = "start")
    private Date start;

    @Column(name = "stop")
    private Date end;

    @NotNull
    @ManyToOne
    private Room room;

    @NotNull
    @ManyToOne
    private Account account;

    public Event(String title, Date start, Date end, Room room, Account account) {
        super();
        this.title = title;
        this.start = start;
        this.end = end;
        this.room = room;
        this.account = account;
    }

}