package com.dnd.dndbattle.services.battle.strategies;

import com.dnd.dndbattle.model.BattleArmy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NormalRound implements InitiativeStrategy {

    @Override
    public SortedMap<Integer,List<Integer>> rollArmyInitiative(Map<Integer, List<BattleArmy>> armies){

        List<Integer> battleArmies = armies.values().stream().flatMap( list -> list.stream()).map(BattleArmy::getId).collect(Collectors.toList());

        SortedMap<Integer,List<Integer>> initiatives =  new TreeMap<>();
        initiatives.put(999,battleArmies);

        return initiatives;
    }
}
