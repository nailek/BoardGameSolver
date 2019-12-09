package com.celiunski.board.game.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.celiunski.board.game.utils.Vector3;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(Parameterized.class)
public class BoardNodeTest {

    @Parameterized.Parameter()
    public Vector3 expectedPos;

    @Parameterized.Parameter(1)
    public boolean expectedFilledSetUp;

    @Parameterized.Parameter(2)
    public BoardNode boardNode;

    @Parameterized.Parameters(name = "{index}: expectedResult{0}")
    public static Object[] data() {
        return new Object[][]{
                {new Vector3(1, 2, -3), true, new BoardNode(1,2,true)},
                {new Vector3(3, -3,  0), false, new BoardNode(3,-3,false)}
        };
    }

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void getPosTest() {
        assertEquals(expectedPos, boardNode.getPos());
    }

    @Test
    public void isFilledTest() {
        assertEquals(expectedFilledSetUp, boardNode.isFilled());
    }
}


