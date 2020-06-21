package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Army;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArmyServiceImpl implements ArmyService {

    private Map<Integer,Army> armyMap = new HashMap<>();

    public ArmyServiceImpl() {
        loadArmies();
    }

    @Override
    public List<?> listAll() {
        return new ArrayList<>(armyMap.values());
    }

    @Override
    public Army getById(Integer id) {
        return armyMap.get(id);
    }

    @Override
    public Army saveOrUpdate(Army army) {
        if(army != null) {
            if(army.getId() == null){
                army.setId(getNextId());
            }
            armyMap.put(Math.toIntExact(army.getId()),army);

        } else {
            throw new RuntimeException("Null army !!");
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        armyMap.remove(id);
    }

    private void loadArmies() {

    }

    private int getNextId(){
        return Collections.max(armyMap.keySet()) + 1;
    }
}
