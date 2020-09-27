package com.dnd.dndbattle.services.battle;

import com.dnd.dndbattle.model.Severity;

public class Log {
    private final Integer triggeringArmy;
    private final Severity severity;
    private final String text;
    private final int turnNumber;

    public Log(int turnNumber, Integer triggeringArmy, Severity severity, String text) {
        this.turnNumber = turnNumber;
        this.triggeringArmy = triggeringArmy;
        this.severity = severity;
        this.text = text;
    }

    public Integer getTriggeringArmy() {
        return triggeringArmy;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getText() {
        return text;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
