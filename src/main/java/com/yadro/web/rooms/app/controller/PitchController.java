package com.yadro.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.model.Stadium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yadro.web.rooms.app.service.PitchService;
import com.yadro.web.rooms.app.service.StadiumService;

@Controller
public class PitchController {
	
	private Logger log = LoggerFactory.getLogger(PitchController.class);
	
	@Autowired
	private PitchService pitchService;
	
	@Autowired
	private StadiumService stadiumService;
	
	@RequestMapping("/pitch/list")
	public String list(ModelMap map) {
	   map.addAttribute("pitches", pitchService.listTable());
	   return "pitch/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pitch/list/export")
	public String listDownload(HttpServletResponse response) {
		String fileName = "pitchList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(pitchService.listExport());
		return content;
	}
	
	@RequestMapping(value = "/pitch/add", method = RequestMethod.GET)
	public String add(Pitch pitch, Model model) {
		List<Stadium> stadiumList = stadiumService.list();
		model.addAttribute("stadiumList", stadiumList);
	   return "/pitch/add";
	}
	
	@RequestMapping(value = "/pitch/add", method = RequestMethod.POST)
	public String addPost(@Valid Pitch pitch, BindingResult result, Model model) {
		List<Stadium> stadiumList = stadiumService.list();
		model.addAttribute("stadiumList", stadiumList);
	   if (result.hasErrors()) {
	       return "pitch/add";
	   }
	   
	   Pitch registeredPitch = pitchService.add(pitch);
	   if (registeredPitch != null) {
	      return "redirect:/pitch/list";
	   } else {
	       log.error("Pitch already exists: " + pitch.getName());
	       //result.rejectValue("university", "error.alreadyExists", "This university already exists, please try another name.");
	       return "redirect:/pitch/add" + "?error";
	   }
	}
	
	@RequestMapping("/pitch/edit/{id}")
	public String edit(@PathVariable("id") Long id, Pitch pitch, Model model) {
		Pitch r = pitchService.findById(id);
		pitch.setId(r.getId());
		pitch.setName(r.getName());
		pitch.setStadium(r.getStadium());
		pitch.setBall(r.getBall());
		pitch.setManish(r.getManish());
		pitch.setGrass(r.getGrass());
		pitch.setProjector(r.getProjector());
		pitch.setChangingRoom(r.getChangingRoom());
		pitch.setShower(r.getShower());
		pitch.setWc(r.getWc());
		
		List<Stadium> stadiumList = stadiumService.list();
		model.addAttribute("stadiumList", stadiumList);
	   
	   return "/pitch/edit";
	}

	@RequestMapping(value = "/pitch/edit", method = RequestMethod.POST)
	public String editPost(@Valid Pitch pitch, BindingResult result, Model model) {
		 
		List<Stadium> stadiumList = stadiumService.list();
		model.addAttribute("stadiumList", stadiumList);
		
	   if (result.hasFieldErrors("name")) {
	       return "/pitch/edit";
	   }

		Pitch roo = pitchService.update(pitch);
		
		if (roo == null) {
			return "redirect:/pitch/edit/" + pitch.getId() + "?error";
		} else {
			return "redirect:/pitch/edit/" + pitch.getId() + "?updated";
		}
	}
	
	@RequestMapping("/pitch/delete")
	public String delete(Long id) {
		pitchService.delete(id);
	   return "redirect:/pitch/list";
	}
}
