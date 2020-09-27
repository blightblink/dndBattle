package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.services.battle.strategies.AttackOrderStrategy;
import com.dnd.dndbattle.services.battle.strategies.InitiativeStrategy;

import java.util.List;

public interface BattleService {
    void initializeArmies(List<BattleArmy> battleArmies);

    void beginTurn();

    void playersInitiative(InitiativeStrategy strategy);

    void playersArmyOrder(AttackOrderStrategy strategy);

    void battleRound();

    TurnLogs getLogs();
}
