package com.dnd.dndbattle.services.battle.strategies;

import com.dnd.dndbattle.model.BattleArmy;

import java.util.List;
import java.util.Map;

public interface AttackOrderStrategy {
    Map<Integer, Integer> defineAttackingDefendingArmies(Map<Integer, List<BattleArmy>> armies);
}
