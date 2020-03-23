package com.yadro.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.model.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.model.Event;
import com.yadro.web.rooms.app.service.AccountService;
import com.yadro.web.rooms.app.service.EventService;
import com.yadro.web.rooms.app.service.PitchService;
import com.yadro.web.rooms.app.service.StadiumService;

@Controller
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private PitchService pitchService;
	
	@Autowired
	private StadiumService stadiumService;
	
	@RequestMapping(value="/calendar/addevent", method=RequestMethod.POST)
	public @ResponseBody Event addEvent(@RequestBody Event event, BindingResult result) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId()) || a.isAdmin()) {
			return eventService.add(event);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/calendar/updateevent", method=RequestMethod.PATCH)
	public @ResponseBody Event updateEvent(@RequestBody Event event) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId())){
			return eventService.update(event);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/calendar/removeevent", method=RequestMethod.DELETE)
	public @ResponseBody void removeEvent(@RequestBody Event event) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(event.getAccount().getId()) || a.isAdmin()){
			eventService.delete(event.getId());
		}
	}
	
	@RequestMapping(value="/calendar/events", method=RequestMethod.GET)
	public @ResponseBody List<Event> getEventsInRange(	@RequestParam(value = "start", required = true) String start, 
														@RequestParam(value = "end", required = true) String end,
														@RequestParam(value = "pitch", required = true) String pitch) {
		List<Event> events = eventService.findByDatesBetween(start, end, pitch);
		
		return clearAccount(events); 
	}	
	
	private List<Event> clearAccount(List<Event> e) {
		Account a = accountService.getLoggedInAccount();
		List<Event> events = e;
		for (int i = 0; i < events.size(); i++) {
			if (a != null) {
			Account eventAccount = events.get(i).getAccount();
			if (!a.getId().equals(eventAccount.getId())){
				eventAccount.setPassword("   ");
				eventAccount.setConfirmPassword("   ");
				eventAccount.setEmail("   ");
				eventAccount.setRole("   ");
				eventAccount.setToken("   ");
				eventAccount.setFirstName("   ");
				eventAccount.setLastName("   ");
				eventAccount.setAddress("   ");
				eventAccount.setGroupName("   ");
				eventAccount.setLastLogin("   ");
				if (!a.isAdmin()) {
					eventAccount.setId(null);
					eventAccount.setUserName("   ");
				}
				events.get(i).setAccount(eventAccount);
			}
			}else{
				
				events.get(i).setAccount(null);
			}
		}
		return events;
	}
	
	@RequestMapping("/event/list/delete")
	public String delete(Long id) {
		Account a = accountService.getLoggedInAccount();
		if (a.getId().equals(eventService.findById(id).getAccount().getId()) || a.isAdmin()) { 
			eventService.delete(id);
		}
		return "redirect:/event/list/";
	}
	
	@RequestMapping("/event/list/delete/all")
	public String deleteAll(Long id) {
		Account a = accountService.getLoggedInAccount();
			List<Event> events = eventService.findByAccount(a);
			for (Event e : events) {
				eventService.delete(e.getId());
			}
		return "redirect:/event/list/";
	}
		
	@RequestMapping("/event/list/all/delete")
	public String deleteInAll(Long id) {
		Account a = accountService.getLoggedInAccount();
		if (a.isAdmin()) {
			eventService.delete(id);
		}
		return "redirect:/event/list/all";
	}
	
	@RequestMapping("/event/list/")
	public String list(Model model) {
		Account a = accountService.getLoggedInAccount();
        model.addAttribute("events", eventService.listTable(a));
		return "event/list";
	}
	
	@RequestMapping("/event/list/all")
	public String listall(Model model) {
		Account a = accountService.getLoggedInAccount();
        if (a.isAdmin()) {
        	 model.addAttribute("events", eventService.listTable());
        	 return "event/listall";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/event/edit/{id}", method=RequestMethod.GET)
	public String pitch(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		Event e = this.eventService.findById(id);
		if (a.getId().equals(e.getAccount().getId()) || a.isAdmin()) {
			model.addAttribute("pitch", e.getPitch());
			model.addAttribute("evente", this.eventService.findById(id));
			return "/event/calendar";
		} else{
			return "redirect:/";
		}
	}
	
	@ResponseBody
	@RequestMapping(value ="/event/list/all/export")
	public String listAllDownload(HttpServletResponse response) {
		
		String fileName = "eventList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(eventService.listExport(null));
		return content;
	}
	
	@ResponseBody
	@RequestMapping(value ="/event/list/export")
	public String listDownload(HttpServletResponse response) {
		Account a = accountService.getLoggedInAccount();
		
		String fileName = "eventList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(eventService.listExport(a));
		return content;
	}
	
	@RequestMapping("/book/stadium")
	public String bookUniversity(Model model) {
		Account a = accountService.getLoggedInAccount();
        model.addAttribute("stadiums", stadiumService.listTable(a.getUniversity()));
		return "book/stadium";
	}
	
	@RequestMapping("/book/stadium/{id}")
	public String bookStadium(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		List<Stadium> availableStadiums = stadiumService.universityStadiumList(a.getUniversity());
		for (Stadium u : availableStadiums){
			if(u.getId().equals(id)){
				model.addAttribute("stadiumId", id);
				model.addAttribute("pitches", pitchService.listTable(u.getId()));
			}
		}
	   return "/book/pitch";
	}
	
	
	@RequestMapping(value= "/book/pitch/{id}", method=RequestMethod.GET)
	public String bookPitch(@PathVariable("id") Long id, Model model) {
		Account a = accountService.getLoggedInAccount();
		List<Stadium> availableStadiums = stadiumService.universityStadiumList(a.getUniversity());
		for (Stadium u : availableStadiums) {
			List<Pitch> availablePitches = pitchService.pitchStadiumList(u);
			for (Pitch r : availablePitches) {
				if (r.getId().equals(id)) {
					model.addAttribute("pitch", pitchService.findById(id));
				}
			}
		}
		return "/book/calendar";
	}
	
	
	@RequestMapping("/calendar/pitch")
	public String checkUniversity(Model model) {
        model.addAttribute("pitches", pitchService.listTablePublic("KPI"));
		return "pitch";
	}
	
	@RequestMapping(value= "/calendar/pitch/{id}", method=RequestMethod.GET)
	public String checkPitch(@PathVariable("id") Long id, Model model) {
		Pitch r = pitchService.findById(id);
		if(r.getStadium().getUniversity().getName().startsWith("KPI")) {
					model.addAttribute("pitch", r);
		}
		return "/calendar/calendar";
	}
}