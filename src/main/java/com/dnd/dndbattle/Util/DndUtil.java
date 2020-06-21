package com.dnd.dndbattle.Util;

public class DndUtil {

    public static Integer getModifier(Integer ability){
        if(ability == null) {
            return null;
        }
        if(ability >= 10) {
            return (ability - 10) / 2;
        } else {
            return (( ability) / 2) - 5;
        }

    }

    public static int calculateMaxHp(int level, int hd, int con){
        final int conMod = DndUtil.getModifier(con);
        return (hd + conMod) + ((level -1 ) *((hd/2) + 1 + conMod));
    }


}
