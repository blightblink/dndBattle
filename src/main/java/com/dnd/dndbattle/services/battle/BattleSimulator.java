package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.model.Severity;
import com.dnd.dndbattle.model.Unit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class BattleSimulator {

    private Turn turn;
    private TurnLogs turnLogs;


    public BattleSimulator(Turn turn){
        this.turn = turn;
        this.turnLogs = new TurnLogs();
    }


    public void battle() {
        SortedMap<Integer, List<Integer>> initiatives = turn.getInitiativeMap();
        Iterator iterator = initiatives.keySet().iterator();

        while (iterator.hasNext()){
            turnLogs.addLogging(new Log(turn.getCurrentTurn(),0, Severity.MINOR,"------ Turn Started ------"));
            Integer key = (Integer) iterator.next();
            armiesAttacking(initiatives.get(key));
         /*   armies.values().forEach( i-> i.stream().forEach( b ->{
                System.out.println(b.getId() + " " +b.getUnits().size());
            }));*/
            armiesLoseUnits(initiatives.get(key));
            /*armies.values().forEach( i-> i.stream().forEach( b ->{
                System.out.println(b.getId() + " " +b.getUnits().size());
            }));*/
            turnLogs.addLogging(new Log(turn.getCurrentTurn(),0, Severity.MINOR,"------ Turn Ended ------"));
        }
    }

    private void armiesAttacking(List<Integer> armies){
        Map<Integer, Integer> attackingArmies = turn.getAttackingDefendingArmies();

        //TODO filter with the armies
        attackingArmies.forEach( (k,v) -> damagingArmy(k,v));
    }

    private void damagingArmy(Integer fromArmy, Integer toArmy){
        final BattleArmy attacker = turn.getArmyMap().get(fromArmy);
        final BattleArmy defender = turn.getArmyMap().get(toArmy);

        final Integer attackingUnits = attacker.calculateAttackingUnits();

        for( int i = 0; i < attackingUnits; i++){
            unitFromArmyAttack(attacker,defender);
        }

    }

    private void unitFromArmyAttack(BattleArmy attacker, BattleArmy defender){
        final Unit attackingUnit = attacker.getUnits().values().stream().collect(Collectors.toList()).get(0); //TODO better selection of units
        if(attackingUnit == null){
            return;
        }

        final int attackRoll = attackingUnit.attackRoll();
        final int damageRoll = attackingUnit.damageRoll();


        if(defender.getUnits().size() > 0){
            turnLogs.addLogging(new Log(turn.getCurrentTurn(), attacker.getId(), Severity.MODERATE,"Attack Roll:" + attackRoll + " Damage Roll:" + damageRoll));
            System.out.println("Attack Roll:" + attackRoll + " Damage Roll:" + damageRoll);
            Unit defendingUnit = defender.getAliveUnit();

            if(defendingUnit != null){
                if(defendingUnit.receivedAttack(attackRoll,damageRoll)){
                    turnLogs.addLogging(new Log(turn.getCurrentTurn(), attacker.getId(), Severity.MODERATE, "Successful attack, Defending unit Hp: " + defendingUnit.getCurHp()));
                };
                if(defendingUnit.isDeadUnit()){
                    turnLogs.addLogging(new Log(turn.getCurrentTurn(), attacker.getId(), Severity.MODERATE, "Kills a Unit!"));
                    System.out.println("Unit Died from army !!" + defender.getId());
                }
            }
        }
    }

    private void armiesLoseUnits(List<Integer> armies2) {
        List<BattleArmy> armies = turn.getArmyMap().values().stream().filter(x -> armies2.contains(x.getId())).collect(Collectors.toList());
        armies.forEach(BattleArmy::removeDeadUnits);
    }

    public TurnLogs getTurnLogs() {
        return turnLogs;
    }
}
