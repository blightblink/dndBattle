package com.dnd.dndbattle.model;

import com.dnd.dndbattle.Util.DiceUtil;
import com.dnd.dndbattle.Util.DndUtil;
import com.dnd.dndbattle.domain.Soldier;

import java.util.HashMap;
import java.util.Map;

public class Unit {
    private int maxHp;
    private int curHp;
    private int hd;

    private int bab;
    private int baseAc;

    private Map<AbilityScores,Integer> stats = new HashMap<>();
    private Map<AbilityScores,Integer> statModifiers = new HashMap<>();

    public int level;
    private String type;

    private int baseWeapon;

    public Unit(Soldier soldier) {
        hd = soldier.getHd();
        level = soldier.getLevel();
        bab = soldier.getBab();
        setStats(soldier);
        curHp = maxHp = DndUtil.calculateMaxHp(soldier.getLevel(),soldier.getHd(),soldier.getCon());

        baseWeapon = soldier.getDamage();
        baseAc = soldier.getAc();

        type = soldier.getName();
    }

    private void setStats(Soldier soldier){
        stats.put(AbilityScores.STR, soldier.getStr());
        stats.put(AbilityScores.DEX, soldier.getDex());
        stats.put(AbilityScores.CON, soldier.getCon());
        statModifiers.put(AbilityScores.STR, DndUtil.getModifier(soldier.getStr()));
        statModifiers.put(AbilityScores.DEX, DndUtil.getModifier(soldier.getDex()));
        statModifiers.put(AbilityScores.CON, DndUtil.getModifier(soldier.getCon()));
    }

    public int attackRoll(){
        return DiceUtil.rollD20() + bab + statModifiers.get(AbilityScores.STR);
    }

    public int damageRoll(){
        return DiceUtil.rollDice(baseWeapon) + statModifiers.get(AbilityScores.STR);
    }

    public boolean receivedAttack(int attackRoll, int damageRoll){
        if(attackRoll >= baseAc){
            curHp = curHp - damageRoll;
            System.out.println("curHp = " + curHp);
            return true;
        }
        return false;
    }

    public boolean isDeadUnit(){
        return curHp < 1;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurHp() {
        return curHp;
    }

    public int getHd() {
        return hd;
    }

    public int getBab() {
        return bab;
    }

    public int getBaseAc() {
        return baseAc;
    }

    public Map<AbilityScores, Integer> getStats() {
        return stats;
    }

    public Map<AbilityScores, Integer> getStatModifiers() {
        return statModifiers;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseWeapon() {
        return baseWeapon;
    }

    public String getType() {
        return type;
    }
}
