package com.honigsheroes.checkers.model.test;

import com.honigsheroes.checkers.Constants.PlayerColor;
import com.honigsheroes.checkers.model.CurrentBoard;
import com.honigsheroes.checkers.model.Square;

import junit.framework.TestCase;

/**
 * Created by pulu on 4/23/15.
 */
public class CheckersGameTest extends TestCase {

    private PlayerColor playerTurn;
    private CurrentBoard board;
    private Square[] squares;

    @Override
    protected void setUp() throws Exception {
        playerTurn= PlayerColor.BLACK;
        board=new CurrentBoard(squares);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCheckWinConditions(){
        assertEquals(2,2);
    }

    public void testAA(){
        assertEquals(2,3);
    }


}
