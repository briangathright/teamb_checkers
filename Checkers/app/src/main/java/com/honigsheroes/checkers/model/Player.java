package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;

/**
 * TODO: Meera implement Player class
 * Players have a name and player color.
 */
public class Player {
    public Constants.PlayerColor color;
    public String name;
    public Player (Constants.PlayerColor color, String name){
        this.name = name;
        this.color = color;
    }

}
