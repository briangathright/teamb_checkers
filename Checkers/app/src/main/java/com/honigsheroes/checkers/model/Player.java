package com.honigsheroes.checkers.model;

import com.honigsheroes.checkers.Constants;
import com.honigsheroes.checkers.Constants.*;

/**
 *
 * Players have a name and player color.
 */
public class Player {
    private PlayerColor color;
    private String name;

    public Player (PlayerColor color){
        this.color = color;
    }

    private Player (PlayerColor color, String name){
        this.name = name;
        this.color = color;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
