package com.dnd.dndbattle.Util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceUtilTest {

    @Test
    public void rollDice() {
        boolean loadedDice = false;
        int roll = 0;
        for (int i=0; i<100;i++){
            roll = DiceUtil.rollDice(20);
            if(roll>20 || roll<1){
                loadedDice = true;
            }
        }
        Assert.assertFalse(loadedDice);
    }
}
