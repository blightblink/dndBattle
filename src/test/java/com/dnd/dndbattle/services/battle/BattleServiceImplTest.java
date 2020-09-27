package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.model.Unit;
import com.dnd.dndbattle.services.battle.strategies.AllArmiesAttack;
import com.dnd.dndbattle.services.battle.strategies.NormalRound;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BattleServiceImplTest {

    BattleServiceImpl battleService = new BattleServiceImpl();
    Map<Integer,BattleArmy> armies = new HashMap<>();

    Map<Integer,BattleArmy> basicArmy = new HashMap<>();
    BattleServiceImpl basicBattleService = new BattleServiceImpl();

    @Before
    public void init(){
        createArmies(this.armies, 10);
        createArmies(this.basicArmy, 1);
        battleService.initializeArmies(armies.values()
                .stream().collect(Collectors.toList()));
        battleService.beginTurn();
        battleService.playersInitiative(new NormalRound());
        battleService.playersArmyOrder(new AllArmiesAttack());
    }

    @Test
    public void battleRound() {

        for(;;){
            battleService.battleRound();
            Turn turn = battleService.getCurrentTurn().build();
            if(turn.getArmyMap().values()
                    .stream()
                    .filter(i -> i.getUnits().size() >0)
                    .count() > 1){
                turn.getArmyMap().values().stream().forEach( i-> {
                    System.out.println(" ----- army: " +  i.getId());
                    i.getUnits().values().forEach( n -> System.out.println( "unit hp:" + n.getCurHp()));
                });
            } else{
                break;
            }
        }

    }

    @Test
    public void battlePvP(){
        basicBattleService.initializeArmies(basicArmy.values().stream().collect(Collectors.toList()));
        basicBattleService.beginTurn();
        basicBattleService.playersInitiative(new NormalRound());
        basicBattleService.playersArmyOrder(new AllArmiesAttack());

        for(;;){
            basicBattleService.battleRound();
            Turn turn = basicBattleService.getCurrentTurn().build();


            if(turn.getArmyMap().values()
                    .stream()
                    .filter(i -> i.getUnits().size() >0)
                    .count() > 1){
                /*turn.getArmyMap().values().stream().forEach( i-> {
                    System.out.println(" ----- army: " +  i.getId());
                    i.getUnits().values().forEach( n -> System.out.println( "unit hp:" + n.getCurHp()));
                });*/
            } else{
                TurnLogs y = basicBattleService.getLogs();
                 y.getTurnLogging().forEach(t -> {
                     System.out.println(t.getTriggeringArmy() + " - " +t.getSeverity() + " - " + t.getText());
                 });
                break;
            }
        }
    }

    private void createArmies(Map<Integer,BattleArmy> armies , int numberOfSoldiers){
        BattleArmy ba = new BattleArmy();
        ba.setId(1);
        ba.setPlayerId(1);
        Map<Integer,Unit> l = new HashMap<>();
        for(int i= 0;i<numberOfSoldiers;i++) {
            Soldier s1 = new Soldier();
            s1.setId(1L);
            s1.setName("Fighter");
            s1.setAc(14);
            s1.setBab(1);
            s1.setDamage(8);
            s1.setStr(16);
            s1.setDex(15);
            s1.setCon(14);
            s1.setHd(10);
            s1.setLevel(1);
            Unit u = new Unit(s1);
            l.put(i+1,u);
        }
        ba.setUnits(l);
        armies.put(1,ba);

        BattleArmy ba2 = new BattleArmy();
        ba2.setId(2);
        ba2.setPlayerId(2);
        Map<Integer,Unit> l2 = new HashMap<>();
        for(int i= 0;i<numberOfSoldiers;i++) {
            Soldier s2 = new Soldier();
            s2.setId(1L);
            s2.setName("Fighter");
            s2.setAc(14);
            s2.setBab(1);
            s2.setDamage(8);
            s2.setStr(16);
            s2.setDex(15);
            s2.setCon(14);
            s2.setHd(10);
            s2.setLevel(1);
            Unit u2 = new Unit(s2);
            l2.put(i+1,u2);
        }
        ba2.setUnits(l2);
        armies.put(2,ba2);
    }
}
