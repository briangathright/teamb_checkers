package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants.*;

/**
 *
 * Players have a name and player color.
 */
public class Player {
    private PlayerColor color;
    private String name;
    private Player (PlayerColor color, String name){
        this.name = name;
        this.color = color;
    }
    public PlayerColor GetPlayerColor() {return color;}
    public String GetPlayerName(){return name;}

}
