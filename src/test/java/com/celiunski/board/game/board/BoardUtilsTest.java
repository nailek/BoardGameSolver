package com.celiunski.board.game.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockitoAnnotations;

import com.celiunski.board.game.Exception.BoardNodeNotFoundException;
import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Parameterized.class)
public class BoardUtilsTest {

    @Parameterized.Parameter()
    public Vector3 middleVector;

    @Parameterized.Parameter(1)
    public Vector3 vector3A;

    @Parameterized.Parameter(2)
    public Vector3 vector3B;

    @Parameterized.Parameter(3)
    public Utils.Axis axis;

    @Parameterized.Parameters(name = "{index}: expectedResult{0}")
    public static Object[] data() {
        return new Object[][]{
                {new Vector3(1, -1, 0), new Vector3(0, 0, 0), new Vector3(2, -2, 0), Utils.Axis.X},
                {new Vector3(1, 1, -2), new Vector3(2, 1, -3), new Vector3(0,1,-1), Utils.Axis.Z},
                {new Vector3(3, -4, 1), new Vector3(2, -3, 1), new Vector3(4,-5,1), Utils.Axis.X}
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMiddleNodeTest() throws BoardNodeNotFoundException {
        assertEquals(middleVector, BoardUtils.getMiddleNode(vector3A, vector3B));
    }

    @Test
    public void isAddjacent1AwayTrueTest() {
        assertTrue(BoardUtils.isAdjacentKAway(vector3A, middleVector, 1));
    }

    @Test
    public void isAddjacent2AwayTrueTest() {
        assertTrue(BoardUtils.isAdjacentKAway(vector3A, vector3B, 2));
    }

    @Test
    public void isAddjacent1AwayFalseTest() {
        assertFalse(BoardUtils.isAdjacentKAway(vector3A, vector3B,1));
    }

    @Test
    public void isAddjacent2AwayFalseTestA() {
        assertFalse(BoardUtils.isAdjacentKAway(vector3A, middleVector,2));
    }

    @Test
    public void isAddjacent2AwayFalseTestB() {
        assertFalse(BoardUtils.isAdjacentKAway(vector3B, middleVector,2));
    }
}