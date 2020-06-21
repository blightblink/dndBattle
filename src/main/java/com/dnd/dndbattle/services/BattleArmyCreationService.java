package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.model.BattleArmy;

public interface BattleArmyCreationService extends CRUDService<BattleArmy> {
    BattleArmy createOrUpdateBattleArmy(Integer armyId, Soldier soldier, Integer playerId, int size);
}
