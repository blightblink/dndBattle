package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.model.Unit;
import com.dnd.dndbattle.services.battle.strategies.AttackOrderStrategy;
import com.dnd.dndbattle.services.battle.strategies.InitiativeStrategy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BattleServiceImpl implements BattleService {

    private Map<Integer, List<BattleArmy>> armies = new HashMap<>();
    private Map<Integer, Integer> initiative;
    private int turn = 0;
    private Turn.TurnBuilder currentTurn;


    @Override
    public void initializeArmies(List<BattleArmy> battleArmies){
        armies = battleArmies.stream()
                .collect(Collectors.groupingBy(BattleArmy::getPlayerId));
    }

    public BattleServiceImpl() {
        turn = 0;
    }

    @Override
    public void beginTurn() {
        currentTurn = new Turn.TurnBuilder(turn + 1);
        currentTurn.setArmyMap(armies.values().stream().flatMap( x-> x.stream()).collect(Collectors.toMap(BattleArmy::getId, Function.identity())));
    }


    @Override
    public void playersInitiative(InitiativeStrategy strategy){
        currentTurn.setInitiativeMap(strategy.rollArmyInitiative(armies));
    }

    @Override
    public void playersArmyOrder(AttackOrderStrategy strategy){
        currentTurn.setAttackingDefendingArmies(strategy.defineAttackingDefendingArmies(armies));
    }

    @Override
    public void battleRound(){
        Turn turn = currentTurn.build();
        SortedMap<Integer, List<Integer>> initiatives = turn.getInitiativeMap();
        Iterator iterator = initiatives.keySet().iterator();

        while (iterator.hasNext()){
            Integer key = (Integer) iterator.next();
            armiesAttacking(initiatives.get(key));
            armies.values().forEach( i-> i.stream().forEach( b ->{
                System.out.println(b.getId() + " " +b.getUnits().size());
            }));
            armiesLoseUnits(initiatives.get(key));
            armies.values().forEach( i-> i.stream().forEach( b ->{
                System.out.println(b.getId() + " " +b.getUnits().size());
            }));
            System.out.println("End of turn \n");
        }
    }

    private void armiesAttacking(List<Integer> armies){
        Turn turn = currentTurn.build();
        Map<Integer, Integer> attackingArmies = turn.getAttackingDefendingArmies();

        //TODO filter with the armies
        attackingArmies.forEach( (k,v) -> damagingArmy(k,v));
    }

    private void damagingArmy(Integer fromArmy, Integer toArmy){
        Turn turn = currentTurn.build();
        final BattleArmy attacker = turn.getArmyMap().get(fromArmy);
        final BattleArmy defender = turn.getArmyMap().get(toArmy);

        final Integer attackingUnits = attacker.calculateAttackingUnits();

        for( int i = 0; i < attackingUnits; i++){
            unitFromAtmyAttack(attacker,defender);
        }

    }

    private void unitFromAtmyAttack(BattleArmy attacker, BattleArmy defender){
        final Unit attackingUnit = attacker.getUnits().values().stream().collect(Collectors.toList()).get(0); //TODO better selection of units
        if(attackingUnit == null){
            return;
        }

        final int attackRoll = attackingUnit.attackRoll();
        final int damageRoll = attackingUnit.damageRoll();

        final BattleArmy damagedArmy = BattleArmy.copyArmy(defender);

        if(defender.getUnits().size() > 0){
            System.out.println("Attack Roll:" + attackRoll + " Damage Roll:" + damageRoll);
            Unit defendingUnit = defender.getAliveUnit();

            if(defendingUnit != null){
                defendingUnit.receivedAttack(attackRoll,damageRoll);
                if(defendingUnit.isDeadUnit()){
                    System.out.println("Unit Died from army !!" + damagedArmy.getId());
                    //damagedArmy.getUnits().remove(defendingUnit);
                }
            }
        }
        currentTurn.addDamagedArmy(damagedArmy);
    }

    private void armiesLoseUnits(List<Integer> armies2) {
        Turn turn = currentTurn.build();
        List<BattleArmy> armies = turn.getArmyMap().values().stream().filter(x -> armies2.contains(x.getId())).collect(Collectors.toList());
        armies.forEach(/* i -> {
            List<Unit> survivingUnits = new ArrayList<>(i.getUnits().stream().filter( y -> !y.isDeadUnit()).collect(Collectors.toList()));
            i.setUnits(survivingUnits);}}*/
            BattleArmy::removeDeadUnits);
    }

    public void endCurrentTurn(){
        Turn turn = currentTurn.build();

    }

    public Turn.TurnBuilder getCurrentTurn() {
        return currentTurn;
    }
}


