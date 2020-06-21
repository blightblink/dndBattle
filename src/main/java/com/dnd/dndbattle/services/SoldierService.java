package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Soldier;

public interface SoldierService extends CRUDService<Soldier>{


    Soldier getByName(String name);
}
