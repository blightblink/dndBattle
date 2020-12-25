package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Soldier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@Service
*/
public class SoldierServiceImpl implements SoldierService{

    private Map<Long, Soldier> soldiers = new HashMap<>();


    public SoldierServiceImpl() {
        loadSoldiers();
    }

    @Override
    public List<?> listAll() {
        return new ArrayList<>(soldiers.values());
    }

    @Override
    public Soldier getById(Long id) {
        return soldiers.get(id);
    }

    @Override
    public Soldier saveOrUpdate(Soldier soldier) {
        if(soldier != null){
            if(soldier.getId() == null){
                soldier.setId((long) getNextId());
            }
            soldiers.put(soldier.getId(),soldier);
            return soldier;
        }
        else {
            throw new RuntimeException("Null soldier !!");
        }
    }

    @Override
    public void deleteById(Long id) {
        soldiers.remove(id);
    }

    @Override
    public Soldier getByName(String name) {
        return soldiers.values().stream().filter(i -> !i.getName().equals(name)).findFirst().orElse(null);
    }

    private void loadSoldiers() {
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
        soldiers.put(1L,s1);

        Soldier s2 = new Soldier();
        s2.setId(2L);
        s2.setName("Warrior");
        s2.setAc(14);
        s2.setBab(1);
        s2.setDamage(8);
        s2.setStr(16);
        s2.setDex(15);
        s2.setCon(14);
        s2.setHd(10);
        s2.setLevel(1);
        soldiers.put(2L,s2);

    }

    private long getNextId(){
        return Collections.max(soldiers.keySet()) + 1;
    }
}
