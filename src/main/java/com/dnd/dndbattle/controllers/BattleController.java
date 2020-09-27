package com.dnd.dndbattle.controllers;

import com.dnd.dndbattle.dto.BattleArmyToArmyFormDto;
import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.services.BattleArmyCreationService;
import com.dnd.dndbattle.services.SoldierService;
import com.dnd.dndbattle.services.battle.BattleService;
import com.dnd.dndbattle.services.battle.strategies.AllArmiesAttack;
import com.dnd.dndbattle.services.battle.strategies.NormalRound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BattleController {

    private BattleService battleService;
    private BattleArmyCreationService battleArmyCreationService;
    private SoldierService soldierService;
    private BattleArmyToArmyFormDto formDto = new BattleArmyToArmyFormDto();


    @Autowired
    public void setBattleService(BattleService battleService) {
        this.battleService = battleService;
    }

    @Autowired
    public void setBattleArmyCreationService(BattleArmyCreationService battleArmyCreationService) {
        this.battleArmyCreationService = battleArmyCreationService;
    }

    @Autowired
    public void setSoldierService(SoldierService soldierService) {
        this.soldierService = soldierService;
    }


    @RequestMapping("/battle")
    public String listSoldiers(Model model){
        model.addAttribute("armies",battleArmyCreationService.listAll());
        battleService.initializeArmies((List<BattleArmy>) battleArmyCreationService.listAll());
        battleService.beginTurn();
        battleService.playersInitiative(new NormalRound());
        battleService.playersArmyOrder(new AllArmiesAttack());

        return "battleMenu";

    }

    @RequestMapping("/battleRound")
    public String battleRound(Model model){
        battleService.battleRound();
        model.addAttribute("armies",battleArmyCreationService.listAll());
        model.addAttribute("logs",battleService.getLogs().getTurnLogging());

        return "battleRound";

    }


    @RequestMapping("/battles")
    public String battle(){
        battleService.initializeArmies((List<BattleArmy>) battleArmyCreationService.listAll());
        battleService.beginTurn();
        battleService.playersInitiative(new NormalRound());
        battleService.playersArmyOrder(new AllArmiesAttack());
        battleService.battleRound();

        return "armies";
    }
}
