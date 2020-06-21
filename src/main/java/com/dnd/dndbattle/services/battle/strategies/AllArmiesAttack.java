package com.dnd.dndbattle.services.battle.strategies;

import com.dnd.dndbattle.model.BattleArmy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  A round robin attack between the players
 *  A random army target between the armies - x will attack y and y will attack x
 *
 *  TODO add a surplus method if one side has more armies
 *
 */
public class AllArmiesAttack implements AttackOrderStrategy{

    @Override
    public Map<Integer, Integer> defineAttackingDefendingArmies(Map<Integer, List<BattleArmy>>  armies){
        Map<Integer, Integer> attackingDefendingArmies = new HashMap<>();
        List<BattleArmy> attackers = armies.get(1);
        List<BattleArmy> defenders = armies.get(2);

        //TODO add the other armies
        attackingDefendingArmies.put(attackers.get(0).getId(),defenders.get(0).getId());
        attackingDefendingArmies.put(defenders.get(0).getId(),attackers.get(0).getId());

        return attackingDefendingArmies;
    }
}
