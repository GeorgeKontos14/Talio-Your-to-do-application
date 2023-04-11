package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EditBoardNameCtrlTest {
    private MainCtrl mainCtrlMock;
    private ServerUtils serverUtilsMock;
    private BoardViewCtrl boardViewCtrlMock;
    private EditBoardNameViewCtrl sut;
    private Board board;
    private User user;

    /**
     * Setup for tests
     */
    @BeforeEach
    public void setup() {
        mainCtrlMock = Mockito.mock(MainCtrl.class);
        serverUtilsMock = Mockito.mock(ServerUtils.class);
        boardViewCtrlMock = Mockito.mock(BoardViewCtrl.class);
        user = new User("User");
        board = new Board(user, "Board");
        sut = new EditBoardNameViewCtrl(serverUtilsMock, mainCtrlMock, board);
        Mockito.when(mainCtrlMock.getBoardViewCtrl()).thenReturn(boardViewCtrlMock);
        Mockito.when(boardViewCtrlMock.getBoard()).thenReturn(board);
    }

    /**
     * Test for Constructor
     */
    @Test
    public void constructorTest() {
        sut = new EditBoardNameViewCtrl(serverUtilsMock, mainCtrlMock, board);
        assertNotNull(sut);
    }

    /**
     * Test of setBoard
     */
    @Test
    public void setBoardTest() {
        Board b1 = new Board(new User("1"), "U");
        sut.setBoard(b1);
        assertEquals(b1, sut.getBoard());
    }

    /**
     * Test for additionalHelp
     */
    @Test
    public void additionalHelpTest() {
        assertEquals(sut.additionalHelp(), "Edit Board Name specific shortcuts:\n"
                + "Enter - Submit the name change\n"
                + "Escape - Close the page");
    }
}
