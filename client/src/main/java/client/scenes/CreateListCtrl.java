package client.scenes;

import client.communication.CardListCommunication;
import client.utils.ServerUtils;
import commons.Board;
import commons.CardList;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class CreateListCtrl {

    private final MainCtrl mainCtrl;
    private ServerUtils server;

    private final CardListCommunication clComm;


    private Board board;

    @FXML
    private TextField name;

    @Inject
    public CreateListCtrl(CardListCommunication clComm, MainCtrl mainCtrl, Board board, ServerUtils server) {
        this.clComm = clComm;
        this.mainCtrl = mainCtrl;
        this.board = board;
        this.server = server;
    }

    public String getName(){
        return name.getText();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private void clearField() {
        name.clear();
    }

    public void createList(){
        try{
            CardList list = new CardList(getName());
            board.addList(list);
            Board b = server.addBoard(board);
            list.setId(b.getList().get(b.getList().size() - 1).getId());
        } catch (WebApplicationException e){
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        clearField();
        mainCtrl.getBoardViewCtrl().refreshRename();
        mainCtrl.showBoardView(this.board);
    }

    public void cancel(){
        clearField();
        mainCtrl.showBoardView(this.board);
    }
}