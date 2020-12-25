package com.dnd.dndbattle.controllers;

import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.services.SoldierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SoldierController {
    
    private SoldierService soldierService;

    @Autowired
    public void setSoldierService(SoldierService soldierService) {
        this.soldierService = soldierService;
    }

    @RequestMapping("/soldiers")
    public String listSoldiers(Model model){
        model.addAttribute("soldiers",soldierService.listAll());
        return "soldiers";

    }

    @RequestMapping("/soldier/{id}")
    public String getSoldier( @PathVariable Long id, Model model){
        model.addAttribute("soldier",soldierService.getById(id));
        return "soldier";

    }

    @RequestMapping("/soldier/new")
    public String newSoldier(Model model) {

        model.addAttribute("soldier",new Soldier());
        return "soldierForm";
    }

    @RequestMapping("/soldier/edit/{id}")
    public String editSoldier(@PathVariable Long id, Model model) {
        model.addAttribute("soldier",soldierService.getById(id));
        return "soldierForm";
    }

    @RequestMapping(value = "/soldier", method = RequestMethod.POST)
    public String saveOrUpdateSoldier(Soldier soldier){
        Soldier savedChar = soldierService.saveOrUpdate(soldier);
        return "redirect:/soldier/" + savedChar.getId();
    }

    @RequestMapping("/soldier/delete/{id}")
    public String deleteSoldier(@PathVariable Long id){
        soldierService.deleteById(id);
        return "redirect:/soldiers";
    }
}
