package com.frontend.vj5frontend.components.controllers;

import com.frontend.vj5frontend.components.entities.Client;
import com.frontend.vj5frontend.components.services.FrontEndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class FrontEndController {

    @Autowired
    FrontEndService service;

    @GetMapping("/clients")
    public String findPaginated(Model model,
                                @RequestParam(name = "pageNo", defaultValue = "1") int pageNo, @RequestParam(name = "pageSize", defaultValue = "3")int pageSize,
                                @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        List<Client> listClient = null;
        try{
        listClient = service.findPageable(pageNo , pageSize, sortField, sortDirection);}
        catch (Exception ex) { errorMessage(ex); }

        model.addAttribute("listClient", listClient);

        return "clients";
    }

    @GetMapping("/new")
    public String newEntry(Model model)

    @ExceptionHandler(Exception.class)
    public ModelAndView errorMessage(Exception ex)
    {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex.getMessage());
        mav.setViewName("error");

        return mav;
    }

}
