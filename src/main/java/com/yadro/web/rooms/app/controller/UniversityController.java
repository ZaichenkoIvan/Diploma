package com.yadro.web.rooms.app.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.service.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UniversityController {

    private Logger log = LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    private UniversityService universityService;

    @RequestMapping("/university/list")
    public String list(ModelMap map) {
        map.addAttribute("universities", universityService.listTable());
        return "university/list";
    }

    @ResponseBody
    @RequestMapping(value = "/university/list/export")
    public String listDownload(HttpServletResponse response) {
        String fileName = "universityList.json";
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String content = gson.toJson(universityService.listExport());
        return content;
    }

    @RequestMapping(value = "/university/add", method = RequestMethod.GET)
    public String add(University university) {
        return "/university/add";
    }

    @RequestMapping(value = "/university/add", method = RequestMethod.POST)
    public String addPost(@Valid University university, BindingResult result) {
        if (result.hasErrors()) {
            return "university/add";
        }

        University registeredUniversity = universityService.add(university);
        if (registeredUniversity != null) {
            return "redirect:/university/list";
        } else {
            log.error("University already exists: " + university.getName());
            //result.rejectValue("university", "error.alreadyExists", "This university already exists, please try another name.");
            return "redirect:/university/add" + "?error";
        }
    }

    @RequestMapping("/university/edit/{id}")
    public String edit(@PathVariable("id") Long id, University university) {
        University o = universityService.findById(id);
        university.setId(o.getId());
        university.setName(o.getName());

        return "/university/edit";
    }

    @RequestMapping(value = "/university/edit", method = RequestMethod.POST)
    public String editPost(@Valid University university, BindingResult result) {

        if (result.hasFieldErrors("name")) {
            return "/university/edit";
        }

        University org = universityService.update(university);

        if (org == null) {
            return "redirect:/university/edit/" + university.getId() + "?error";
        } else {
            return "redirect:/university/edit/" + university.getId() + "?updated";
        }
    }

    @RequestMapping("/university/delete")
    public String delete(Long id) {
        universityService.delete(id);
        return "redirect:/university/list";
    }

}
