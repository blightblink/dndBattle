package com.dnd.dndbattle.dto;

import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.services.BattleArmyCreationService;
import com.dnd.dndbattle.services.SoldierService;

import org.springframework.beans.factory.annotation.Autowired;

public class BattleArmyToArmyFormDto {

    private SoldierService soldierService;


    @Autowired
    public void setSoldierService(SoldierService soldierService) {
        this.soldierService = soldierService;
    }

    public ArmyFormDto toArmyFormDto(BattleArmy battleArmy, Soldier soldier, Integer size){
        ArmyFormDto dto = new ArmyFormDto();
        dto.setArmyId(battleArmy.getId());
        dto.setPlayerId(battleArmy.getPlayerId());
        dto.setSize(size);
        dto.setSoldierId(Math.toIntExact(soldier.getId()));

        return dto;
    }


}
