package com.dnd.dndbattle.controllers;

import com.dnd.dndbattle.services.battle.BattleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BattleController {

    private BattleService battleService;

    @Autowired
    public void setBattleService(BattleService battleService) {
        this.battleService = battleService;
    }

    @RequestMapping("/battle")
    public String listSoldiers(Model model){
        return "battleMenu";

    }
}
