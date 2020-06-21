package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.BattleArmy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Turn {
    /**
     *  the turn id - number, always positive
     */
    private final int currentTurn;

    /**
     * A full map with the army id as key and the army info
     */
    private final Map<Integer, BattleArmy>  armyMap;

    /**
     * A map with the Initiative as key, and a list of the armies with that initiative
     */
    private final SortedMap<Integer, List<Integer>> initiativeMap;

    /**
     * A map with the id of the attack army, and the id of the defending army
     */
    private final Map<Integer, Integer> attackingDefendingArmies;

    /**
     * A full map with the army id as key and the army info that received damage
     */
    private final Map<Integer, BattleArmy> damagedArmyMap;


    public Turn(TurnBuilder turnBuilder) {
        this.currentTurn = turnBuilder.currentTurn;
        this.armyMap = turnBuilder.armyMap;
        this.initiativeMap = turnBuilder.initiativeMap;
        this.attackingDefendingArmies = turnBuilder.attackingDefendingArmies;
        this.damagedArmyMap = turnBuilder.damagedArmyMap;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public Map<Integer, BattleArmy> getArmyMap() {
        return armyMap;
    }

    public SortedMap<Integer, List<Integer>> getInitiativeMap() {
        return initiativeMap;
    }

    public Map<Integer, Integer> getAttackingDefendingArmies() {
        return attackingDefendingArmies;
    }

    public Map<Integer, BattleArmy> getDamagedArmyMap() {
        return damagedArmyMap;
    }

    public static class TurnBuilder {
        private int currentTurn;
        private Map<Integer, BattleArmy> armyMap = new HashMap<>();
        private SortedMap<Integer, List<Integer>> initiativeMap = new TreeMap<>();
        private Map<Integer, Integer> attackingDefendingArmies = new HashMap<>();
        private Map<Integer, BattleArmy> damagedArmyMap = new HashMap<>();

        public TurnBuilder(int turn){
            this.currentTurn = turn;
        }

        public TurnBuilder setArmyMap(Map<Integer, BattleArmy> armyMap){
            this.armyMap = armyMap;
            return this;
        }

        public TurnBuilder setInitiativeMap(SortedMap<Integer, List<Integer>> initiativeMap){
            this.initiativeMap = initiativeMap;
            return this;
        }

        public TurnBuilder setAttackingDefendingArmies(Map<Integer, Integer> attackingDefendingArmies){
            this.attackingDefendingArmies = attackingDefendingArmies;
            return this;
        }

        public TurnBuilder addDamagedArmy(BattleArmy battleArmy){
            this.damagedArmyMap.put(battleArmy.getId(),battleArmy);
            return this;
        }

        public Turn build(){
            Turn turn = new Turn(this);

            return turn;
        }

    }
}
