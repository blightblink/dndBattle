package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.model.Unit;
import com.dnd.dndbattle.services.battle.strategies.AttackOrderStrategy;
import com.dnd.dndbattle.services.battle.strategies.InitiativeStrategy;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private List<TurnLogs> logs = new ArrayList<>();


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
        currentTurn = new Turn.TurnBuilder(turn++);
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
        Turn newTurn = currentTurn.build();
        BattleSimulator simulator = new BattleSimulator(newTurn);
        simulator.battle();
        turn++;

        logs.add(simulator.getTurnLogs());

    }

    public void endCurrentTurn(){
        Turn turn = currentTurn.build();

    }

    public Turn.TurnBuilder getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public TurnLogs getLogs() {
        return logs.get(logs.size()-1);
    }
}

