package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Army;
import com.dnd.dndbattle.domain.Soldier;
import com.dnd.dndbattle.dto.ArmyFormDto;
import com.dnd.dndbattle.model.BattleArmy;
import com.dnd.dndbattle.model.Unit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BattleArmyCreationServiceImpl implements BattleArmyCreationService, InitializingBean {

    private Map<Integer,BattleArmy> armies = new HashMap<>();

    private String csvPath;
    private String           csvSplitBy = ",";
    private String           line       = "";

    @Value("${csvPath}")
    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*if (armyService == null){
            throw new RuntimeException("null service");
        }*/
    }

    public BattleArmyCreationServiceImpl() {
        loadArmies();
        BattleArmy ba = new BattleArmy();
        ba.setId(1);
        ba.setPlayerId(1);
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
        Map<Integer,Unit> l = new HashMap<>();
        l.put(1,u);
        ba.setUnits(l);
        armies.put(1,ba);

        BattleArmy ba2 = new BattleArmy();
        ba2.setId(2);
        ba2.setPlayerId(2);
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
        Map<Integer,Unit> l2 = new HashMap<>();
        l2.put(1,u2);
        ba2.setUnits(l2);
        armies.put(2,ba2);
    }

    @Override
    public List<?> listAll() {
        return new ArrayList<>(armies.values());
    }

    @Override
    public BattleArmy getById(Long id) {
        return armies.get(id);
    }

    @Override
    public void deleteById(Long id) {
        armies.remove(id);
    }

    @Override
    public BattleArmy saveOrUpdate(BattleArmy battleArmy) {
        if(battleArmy != null){
            if(battleArmy.getId() == null){
                battleArmy.setId(getNextId());
            }
            armies.put(Math.toIntExact(battleArmy.getId()),battleArmy);
            return battleArmy;
        }
        else {
            throw new RuntimeException("Null soldier !!");
        }
    }

    private int getNextId(){
        return Collections.max(armies.keySet()) + 1;
    }


    @Override
    public BattleArmy createOrUpdateBattleArmy(Long armyId, Soldier soldier, Integer playerId, int size) {
        BattleArmy army = armyId != null && getById(armyId) != null ? getById(armyId) : new BattleArmy(getNextId());

        army.setPlayerId(playerId);

        Map<Integer,Unit> units = new HashMap<>();
        for(int i = 0; i < size; i ++){
            Unit unit = new Unit(soldier);
            units.put(i+1,unit);
        }
        army.setUnits(units);

        saveOrUpdate(army);

        return army;
    }

    private void loadArmies() {
        try {
            BufferedReader br =new BufferedReader(new FileReader("C:\\Users\\chatzigeorgiou\\tutorials\\dndbattle\\src\\main\\resources\\csv\\armies.csv"));
            int i=0;
            while ((line = br.readLine()) != null) {
                if(i!= 0){

                    /*BattleArmy ba = new BattleArmy(line.split(csvSplitBy));
                    sysMarketTypeUF marketType = new sysMarketTypeUF(line.split(csvSplitBy));
                    sysMarketUF.add(marketType);*/
                }
                i++;
            }
            File f = new File("armies.csv");
            /*Files.readAllLines(new File(csvPath + "\\armies.csv").toPath()).stream().forEach(l -> {
            });*/
        } catch (Exception e){
            System.out.println("Damn, this" );
        }
    }
}
