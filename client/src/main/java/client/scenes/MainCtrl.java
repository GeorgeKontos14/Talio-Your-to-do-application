/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import commons.Board;
import commons.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Board board;
    private Stage primaryStage;
    private BoardsOverviewCtrl overviewCtrl;
    private Scene overview;
    private BoardViewCtrl boardViewCtrl;
    private Scene boardView;
    private Scene addCard;
    private CreateListCtrl createListCtrl;
    private Scene createList;
    private CreateBoardViewCtrl createBoardViewCtrl;
    private Scene createBoard;
    private Scene user;
    private UserCtrl userCtrl;

    private long id;
    private long cardId;
    private ChangeNameCtrl changeListNameCtrl;
    private Scene changeListName;
    private AddCardCtrl addCardCtrl;
    private Scene editCard;
    private EditCardCtrl editCardCtrl;

    public static final DataFormat cardDataFormat = new DataFormat("card");
    public static final DataFormat cardListDataFormat = new DataFormat("cardList");
    private User currentUser;

    /**
     * Initializes the application
     * @param primaryStage the primary stage used
     * @param overview the boardOverview scene
     * @param boardView the boardView scene
     * @param createList the createList scene
     * @param createBoard the createBoard scene
     * @param addCard the addCard scene
     * @param userPage the user log in page
     * @param editCard the editCard scene
     * @param changeListName the changeListName scene
     */
    public void initialize(Stage primaryStage, Pair<BoardsOverviewCtrl, Parent> overview,
            Pair<BoardViewCtrl, Parent> boardView, Pair<CreateListCtrl, Parent> createList,
                           Pair<CreateBoardViewCtrl, Parent> createBoard,
                           Pair<AddCardCtrl,Parent> addCard, Pair<UserCtrl, Parent> userPage,
                           Pair<EditCardCtrl, Parent> editCard,
                           Pair<ChangeNameCtrl, Parent> changeListName) {
        this.primaryStage = primaryStage;

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.boardViewCtrl = boardView.getKey();
        this.boardView = new Scene(boardView.getValue());


        this.createListCtrl = createList.getKey();
        this.createList = new Scene(createList.getValue());

        this.createBoardViewCtrl = createBoard.getKey();
        this.createBoard = new Scene(createBoard.getValue());

        this.changeListNameCtrl = changeListName.getKey();
        this.changeListName = new Scene(changeListName.getValue());

        this.addCardCtrl = addCard.getKey();
        this.addCard = new Scene(addCard.getValue());

        this.editCard = new Scene(editCard.getValue());
        this.editCardCtrl = editCard.getKey();

        this.userCtrl = userPage.getKey();
        this.user = new Scene(userPage.getValue());

        showUserView();
        primaryStage.show();
    }

    /**
     * Setter for the current user
     * @param user the user to be introduced as current user
     */
    public void setCurrentUser (User user) {
        this.currentUser = user;
    }

    /**
     * Getter for the current user
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * SHows an overview of all boards
     */
    public void showOverview() {
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(overview);
        this.overviewCtrl.refresh();
    }

    /**
     * Redirects to the Board View page
     *
     * @param board the board to be shown
     */
    public void showBoardView(Board board) {
        primaryStage.setTitle(board.getName());
        primaryStage.setScene(boardView);

        this.boardViewCtrl.setBoard(board);
        this.boardViewCtrl.refresh();
    }

    /**
     * Shows the add card page
     */
    public void showAddCard() {
        primaryStage.setTitle("Add Card");
        primaryStage.setScene(addCard);
    }

    /**
     * Shows the edit card page
     */
    public void showEditCard() {
        primaryStage.setTitle("Edit Card");
        primaryStage.setScene(editCard);
        editCardCtrl.updateFields();
        //must change later for safety measures

    }


    /**
     * Shows the createList scene
     *
     * @param board the board to which the list is to be added
     */
    public void showCreateList(Board board) {
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(createList);
        this.createListCtrl.setBoard(board);
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("ClientView.fxml"));
//        mainLayout = loader.load();
//
//        ClientViewController cvc = loader.getController();
//        cvc.setClient(client); // Passing the client-object to the ClientViewController
//        this.createListCtrl.setBoard(board);
//
//        Scene scene = new Scene(mainLayout, 900, 600);
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(true);
//        primaryStage.show();
    }

    /**
     * Getter for boardViewCtrl
     *
     * @return the boardViewCtrl
     */
    @SuppressWarnings("unused")
    public BoardViewCtrl getBoardViewCtrl() {
        return boardViewCtrl;
    }

    /**
     * Shows the createBoard scene
     */
    public void createBoardView() {
        primaryStage.setTitle("New Board");
        primaryStage.setScene(createBoard);
    }

    /**
     * Shows the sign-in page
     */
    public void showUserView() {
        primaryStage.setTitle("Sign in");
        primaryStage.setScene(user);
    }

    /** Shows the ChangeListName scene
     * @param id id of the current cardList
     */
    public void showChangeListName(Long id) {
//        primaryStage.setTitle(list.getName());
        Board board = getBoardViewCtrl().getBoard();
        primaryStage.setScene(changeListName);
        this.changeListNameCtrl.setId(id);
        this.changeListNameCtrl.setBoard(board);
//        this.boardViewCtrl.refresh();
    }

    /**
     * @return the current cardlist id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id sets the id of the current cardlist
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the current cardlist id
     */
    public long getCardId() {
        return cardId;
    }

    /**
     * @param cardId sets the id of the current cardlist
     */
    public void setCardId(long cardId) {
        this.cardId = cardId;
    }
}