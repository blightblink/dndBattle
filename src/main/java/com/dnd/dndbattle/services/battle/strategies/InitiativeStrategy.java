package com.dnd.dndbattle.services.battle.strategies;

import com.dnd.dndbattle.model.BattleArmy;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public interface InitiativeStrategy {
    SortedMap<Integer,List<Integer>> rollArmyInitiative(Map<Integer, List<BattleArmy>> armies);
}
