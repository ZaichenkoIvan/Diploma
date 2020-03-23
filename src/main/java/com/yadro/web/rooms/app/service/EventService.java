package com.yadro.web.rooms.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.repository.AccountRepository;
import com.yadro.web.rooms.app.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.model.Event;

@Service
public class EventService {

    private Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private AccountService accountService;

	@Autowired
	private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;

	@Autowired
	private MailService mailService;
	
	public Event findById(long id) {
		return eventRepository.findById(id);
	}
	
	public List<Event> findByAccount(Account account) {
		return eventRepository.findByAccount(account);
	}
	
	public List<Event> list() {
		return eventRepository.findAll();
	}
	
	public List<Event> findByDatesBetween(String start, String end, String pitch) {
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			startDate = inputDateFormat.parse(start);
			endDate = inputDateFormat.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long r = Long.parseLong(pitch);
		return eventRepository.findByDatesBetween(startDate, endDate, r);
	}

	public Event add(Event event) {
		Date now = new Date();
		long data1 = now.getTime();
		long data2 = event.getStart().getTime();

		List<Event> events = eventRepository.findByDatesBetweenOr(event.getStart(), event.getEnd(), event.getPitch().getId());
		if(events.size() == 0
				&& (accountService.getLoggedInAccount().isAdmin() || countInAccount(accountService.getLoggedInAccount())==0)
				&& (now.getTime() - event.getStart().getTime()) < 0
		) {
			Event created = eventRepository.save(event);
			String message = String.format(
					"Hello %s! \n" +
							"You book pitch %s on this time : %s to %s.",
					accountService.getLoggedInAccount().getFirstName(),
					created.getPitch().getName(),
					created.getStart(),
					created.getEnd()
			);
			mailService.send(created.getAccount().getEmail(), "Book pitch", message);
			accountRepository.updateCountEvent(accountService.getLoggedInAccount().getUserName(), countInAccount(accountService.getLoggedInAccount()));
			return created;
		}else{
			return null;
		}
	}
	
	public Event update(Event event) {
		List<Event> events = eventRepository.findByDatesBetweenOrAnd(event.getStart(), event.getEnd(), event.getPitch().getId(), event.getId());
		if(events.size() == 0) {
			String message = String.format(
					"Hello %s! \n" +
							"Your booking of pitch was UPDATE from %s on this time : %s to %s.",
                    accountService.getLoggedInAccount().getFirstName(),
					event.getPitch().getName(),
					event.getStart(),
					event.getEnd()
			);
			mailService.send(event.getAccount().getEmail(), "Update booking of pitch", message);
			return eventRepository.save(event);
		}else{
			return null;
		}
	}
	
    public Boolean delete(Long id) {
		Event event = eventRepository.findById(id);
		String message = String.format(
				"Hello %s! \n" +
						"Your booking of pitch %s on this time : %s to %s was DELETE",
                accountService.getLoggedInAccount().getFirstName(),
				event.getPitch().getName(),
				event.getStart(),
				event.getEnd()
		);
		mailService.send(event.getAccount().getEmail(), "Delete booking of pitch", message);
        this.eventRepository.delete(id);
        return true;
    }
	
	public int countInPitch(Pitch pitch) {
		return this.eventRepository.findByPitch(pitch).size();
	}
	
	public List<Event> pitchEventList(Pitch pitch) {
		return this.eventRepository.findByPitch(pitch);
	}
	
	public int countInAccount(Account account) {
		return this.eventRepository.findByAccount(account).size();
	}
	
	public List<Event> accountEventList(Account account) {
		return this.eventRepository.findByAccount(account);
	}
	
	public List<List<String>> listTable() {
		List<Event> events = this.eventRepository.findAll();
	    List<List<String>> eventsList = new LinkedList<List<String>>();
	    
	    Comparator<Event> comparator = new Comparator<Event>() {
		    @Override
		    public int compare(Event left, Event right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(events, comparator);
	    
	    for (Event event:events) {
	    	List<String> eventData = new ArrayList<String>();
	    	//eventData.add("<a style=\"color: #f9b012\" href=\"/event/edit/" + event.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	eventData.add(event.getAccount().getUserName());
	    	eventData.add(event.getPitch().getName());
	    	eventData.add(event.getTitle());
	    	eventData.add(String.valueOf(event.getStart()));
	    	eventData.add(String.valueOf(event.getEnd()));
	    	eventData.add("<a style=\"color: #f9b012\" href=\"/event/list/all/delete?id=" + event.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	eventsList.add(eventData);
	    }
		return eventsList;
	}
	
	public List<List<String>> listTable(Account account) {
		List<Event> events = this.eventRepository.findByAccount(account);
	    List<List<String>> eventsList = new LinkedList<List<String>>();
	    
	    Comparator<Event> comparator = new Comparator<Event>() {
		    @Override
		    public int compare(Event left, Event right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(events, comparator);
	    
	    for (Event event:events) {
	    	List<String> eventData = new ArrayList<String>();
	    	eventData.add("<a style=\"color: #f9b012\" href=\"/event/edit/" + event.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	eventData.add(event.getTitle());
	    	eventData.add(event.getPitch().getName());
	    	eventData.add(String.valueOf(event.getStart()));
	    	eventData.add(String.valueOf(event.getEnd()));
	    	eventData.add("<a style=\"color: #f9b012\" href=\"/event/list/delete?id=" + event.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	eventsList.add(eventData);
	    }
		return eventsList;
	}
	
	public Map<String, Object> listExport(Account a) {
		List<Event> events = new ArrayList<>();
		if (a == null) {
			events = eventRepository.findAll();
		}else{
			events = eventRepository.findByAccount(a);
		}
		
	    Comparator<Event> comparator = new Comparator<Event>() {
		    @Override
		    public int compare(Event left, Event right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(events, comparator);
		
		Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		for (Event event : events) {	
			Map<String, Object> linkedHashMapMap = new LinkedHashMap<String, Object>();
			linkedHashMapMap.put("id", event.getId());
			linkedHashMapMap.put("title", event.getTitle());
			linkedHashMapMap.put("pitch", event.getPitch());
			linkedHashMapMap.put("start", event.getStart());
			linkedHashMapMap.put("end", event.getEnd());
			linkedHashMapMap.put("account", event.getAccount().getUserName());
			linkedHashMap.put("event " + String.valueOf(event.getId()), linkedHashMapMap);
		}
		return linkedHashMap;
	}
}

