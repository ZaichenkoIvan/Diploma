package com.yadro.web.rooms.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.*;

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
	
	@Column(name="start")
	private Date start;
	
	@Column(name="stop")
	private Date end;
	
    @NotNull
    @ManyToOne
    private Pitch pitch;
   
    @NotNull
    @ManyToOne
    private Account account;
	
	public Event(String title, Date start, Date end, Pitch pitch, Account account) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
		this.pitch = pitch;
		this.account = account;
	}

}