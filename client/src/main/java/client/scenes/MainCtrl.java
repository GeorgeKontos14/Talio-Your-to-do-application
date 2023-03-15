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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;
    private BoardsOverviewCtrl overviewCtrl;
    private Scene overview;

    private BoardViewCtrl boardViewCtrl;
    private Scene boardView;

    private CreateListCtrl createListCtrl;
    private Scene createList;
    @SuppressWarnings("unused")
    private CreateBoardViewCtrl createBoardViewCtrl;
    private Scene createBoard;

    public void initialize(Stage primaryStage, Pair<BoardsOverviewCtrl, Parent> overview,
            Pair<BoardViewCtrl, Parent> boardView, Pair<CreateListCtrl, Parent> createList, Pair<CreateBoardViewCtrl, Parent> create) {
        this.primaryStage = primaryStage;

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.boardViewCtrl = boardView.getKey();
        this.boardView = new Scene(boardView.getValue());


        this.createListCtrl = createList.getKey();
        this.createList = new Scene(createList.getValue());

        this.createBoardViewCtrl = create.getKey();
        this.createBoard = new Scene(create.getValue());

        showOverview();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(overview);
        this.overviewCtrl.refresh();
    }

    /**
     * Redirects to the Board View page
     */
    public void showBoardView(Board board) {
        primaryStage.setTitle(board.getName());
        primaryStage.setScene(boardView);
        this.boardViewCtrl.setBoard(board);
    }

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

    @SuppressWarnings("unused")
    public BoardViewCtrl getBoardViewCtrl() {
        return boardViewCtrl;
    }
    public void createBoardView() {
        primaryStage.setTitle("New Board");
        primaryStage.setScene(createBoard);
    }

}