package com.dnd.dndbattle.Util;

import org.junit.Assert;
import org.junit.Test;

public class DndUtilTest {

    @Test
    public void testGetModifier(){
        Assert.assertTrue(DndUtil.getModifier(7).equals(-2));
        Assert.assertTrue(DndUtil.getModifier(8).equals(-1));
        Assert.assertTrue(DndUtil.getModifier(9).equals(-1));
        Assert.assertTrue(DndUtil.getModifier(10).equals(0));
        Assert.assertTrue(DndUtil.getModifier(11).equals(0));
        Assert.assertTrue(DndUtil.getModifier(12).equals(1));
        Assert.assertTrue(DndUtil.getModifier(13).equals(1));
    }

    @Test
    public void testCalculateMaxHp(){
        Assert.assertTrue(DndUtil.calculateMaxHp(3,10,14) == 28);
        Assert.assertTrue(DndUtil.calculateMaxHp(3,12,14) == 32);
        Assert.assertTrue(DndUtil.calculateMaxHp(1,8,9) == 7);
    }
}
