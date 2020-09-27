package com.dnd.dndbattle.controllers;

import com.dnd.dndbattle.domain.Army;
import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.dto.ArmyFormDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BattleArmiesController {

    private BattleArmyCreationService battleArmyCreationService;
    private SoldierService soldierService;
    private BattleArmyToArmyFormDto formDto = new BattleArmyToArmyFormDto();

    private BattleService battleService;

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

    @RequestMapping("/armies")
    public String listSoldiers(Model model){
        model.addAttribute("armies",battleArmyCreationService.listAll());
        return "armies";

    }

    @RequestMapping(value = "/army", method = RequestMethod.POST)
    public String saveOrUpdateSoldier(ArmyFormDto armyFormDto){
        BattleArmy army = battleArmyCreationService.createOrUpdateBattleArmy(armyFormDto.getArmyId(),soldierService.getById(armyFormDto.getSoldierId()),armyFormDto.getPlayerId(),armyFormDto.getSize());
        return "redirect:/army/" + army.getId();
    }

    @RequestMapping("/army/{id}")
    public String getSoldier( @PathVariable Integer id, Model model){
        model.addAttribute("battleArmy",battleArmyCreationService.getById(id));
        return "army";

    }

    @RequestMapping("/army/edit/{id}")
    public String editSoldier(@PathVariable Integer id, Model model) {
        BattleArmy army = battleArmyCreationService.getById(id);
        Soldier soldier = null;
        if(army.getUnits().get(1) != null){
            soldier = soldierService.getByName(army.getUnits().get(1).getType());//FIXME the whole first unit thing
        }

        ArmyFormDto dto = formDto.toArmyFormDto(army,soldier,army.getUnits().size());

        model.addAttribute("soldiers",soldierService.listAll());
        model.addAttribute("army",dto);
        return "armyForm";
    }

    @RequestMapping("/army/new")
    public String newSoldier(Model model) {
        model.addAttribute("soldiers",soldierService.listAll());
        model.addAttribute("army",new ArmyFormDto());
        return "armyForm";
    }


    @RequestMapping("/army/delete/{id}")
    public String deleteSoldier(@PathVariable Integer id){
        battleArmyCreationService.deleteById(id);
        return "redirect:/armies";
    }


}
