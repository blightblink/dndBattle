package com.dnd.dndbattle.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Army {

    @Id
    Long id;

    int player;

    @ElementCollection(targetClass=Integer.class)
    List<Integer> soldiers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public List<Integer> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<Integer> soldiers) {
        this.soldiers = soldiers;
    }
}
