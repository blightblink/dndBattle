package com.dnd.dndbattle.services.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.transformation.SortedList;
import javafx.util.Pair;

public class TurnLogs {
    Map<Pair<Integer,Integer>,Pair<Integer,Integer>> deadUnitsMap = new HashMap<>();
    List<Log> turnLogs = new ArrayList<>();


    public void deadUnit(Integer armyNum, Integer unitNum,Integer defArmyNum, Integer defUnitNum) {
        deadUnitsMap.put(new Pair<>(armyNum, unitNum),new Pair<>(defArmyNum, defUnitNum));
    }

    public Map<Pair<Integer,Integer>,Pair<Integer,Integer>> getDeadUnitsMap() {
        return deadUnitsMap;
    }

    public void addLogging(Log log){
        turnLogs.add(log);
    }

    public List<Log> getTurnLogging(){
        return turnLogs;
    }
}
