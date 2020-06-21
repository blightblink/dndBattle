package com.dnd.dndbattle.model;

import com.dnd.dndbattle.Util.DiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BattleArmy {
    private Integer id;
    private int playerId;
    private Map<Integer,Unit> units = new HashMap<>();

    public BattleArmy() {
    }
    public BattleArmy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Map<Integer,Unit> getUnits() {
        return units;
    }

    public void setUnits(Map<Integer,Unit> units) {
        this.units = units;
    }

    public Integer calculateAttackingUnits(){
        return units.size() > 8 ? 8 : units.size();
    }

    public Integer calculateDefendingUnits(){
        return units.size() > 8 ? 8 : units.size();
    }

    //TODO FIX ME not deep copy
    public static BattleArmy copyArmy(BattleArmy originalArmy){
        BattleArmy copy = new BattleArmy();
        copy.setPlayerId(originalArmy.getPlayerId());
        copy.setId(originalArmy.getId());
        /*List<Unit> units */
        copy.setUnits(originalArmy.getUnits());
        return copy;
    }

    public Unit getAliveUnit(){

        List<Unit> aliveUnits = units.values().stream().filter(u -> !u.isDeadUnit()).collect(Collectors.toList());

        if(aliveUnits.isEmpty()){
            return null;
        }
        Collections.shuffle(aliveUnits);
        return aliveUnits.get(0);
    }

    public void removeDeadUnits(){
        Set<Integer> deads =  new HashSet<>();
        units.forEach((x,y) -> {
            if(y.isDeadUnit()){
                deads.add(x);
            }
        });
        deads.forEach(x -> units.remove(x));
    }
}
