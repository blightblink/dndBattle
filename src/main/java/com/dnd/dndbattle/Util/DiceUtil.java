package com.dnd.dndbattle.Util;

import java.util.Random;

public class DiceUtil {
    private static Random rand = new Random();


    public static int rollDice(int i){
        return rand.nextInt(i) + 1;
    }

    public static int rollD20(){
        return rollDice(20);
    }

}
