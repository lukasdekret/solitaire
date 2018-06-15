/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author lukasdekret
 */
public class Solitaire extends Application {

    private Stage window; // window of a application
    private Pane mainPane, pane1, pane2, pane3, pane4; // panes to display
    private GridPane grid; // main grid
    private Button newG, saveG, loadG, undoG, helpG, exitG; // button
    static int numberOfGames = 1; // how much games I have
    private ImageView largePic, smallPic1, smallPic2, smallPic3, smallPic4; // for pictures
    private Image backgroundImage;
    private Game mainGame, game1, game2, game3, game4;

    public static int numberOfUndo = 0;

    public static Undo undo, undo1, undo2, undo3, undo4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        window.setTitle("Solitaire Klondike");

        largePic = new ImageView();
        backgroundImage = new Image("file:lib/img/background.jpg");
        largePic.setImage(backgroundImage);
        largePic.setFitHeight(635);
        largePic.setFitWidth(1180);

        mainPane = new Pane();
        pane1 = new Pane();
        pane2 = new Pane();
        pane3 = new Pane();
        pane4 = new Pane();
        game1 = new Game(pane1, 2, 0);
        game2 = new Game(pane2, 2, 0);
        game3 = new Game(pane3, 2, 0);
        game4 = new Game(pane4, 2, 0);

        mainPane.setPrefSize(1200, 635);

        mainPane.getChildren().add(largePic);

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setConstraints(mainPane, 0, 1);
        grid.getChildren().add(mainPane);


        /******** HANDLING EXIT ********/
        window.setOnCloseRequest(e -> {
            e.consume(); // consumes close request
            exitGame();
        });

        /********** MENU **********/
        HBox menu = new HBox();
        menu.setPrefSize(600, 50);

        /* NEW */
        newG = new Button("new");
        newG.setOnAction(e -> newGame());

        /* SAVE */
        saveG = new Button("save");
        saveG.setOnAction(e -> {
            try {
                saveGame();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Solitaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /* LOAD */
        loadG = new Button("load");
        loadG.setOnAction(e -> {
            try {
                loadGame();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Solitaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /* UNDO */
        undoG = new Button("undo");
        undoG.setOnAction(e -> undoGame());

        /* HELP */
        helpG = new Button("help");
        helpG.setOnAction(e -> helpGame());

        /* EXIT */
        exitG = new Button("exit");
        exitG.setOnAction(e -> exitGame());

        menu.setSpacing(20); // spacing between buttons


        menu.getChildren().addAll(newG, saveG, loadG, undoG, helpG, exitG); // buttons to menu
        GridPane.setConstraints(menu, 0, 0); // on the top left corner
        grid.getChildren().add(menu); // adding menu to grid

        menu.setVisible(true);
        /************ END MENU *************/


        Scene scene = new Scene(grid, 1200, 700);
        window.setScene(scene);

        window.show();
        
        mainGame = new Game(mainPane, 1); // initialization of main program

    }

    /* handling new button */
    private void newGame() {
        switch (numberOfGames) {
            case 1: {
                grid.getChildren().remove(mainPane); // removing main pane in order to create smaller

                /* handling images */
                smallPic1 = new ImageView();
                smallPic1.setImage(backgroundImage);
                smallPic1.setFitHeight(310);
                smallPic1.setFitWidth(580);

                smallPic2 = new ImageView();
                smallPic2.setImage(backgroundImage);
                smallPic2.setFitHeight(310);
                smallPic2.setFitWidth(580);

                /* creating new panes and extracting oldlist of stacks from main game*/
                pane1.setPrefSize(580, 320);
                pane1.getChildren().clear();
                List<GeneralStack> oldList = mainGame.getList();

                pane2.setPrefSize(580, 320);

                /* adding panes to game */
                pane1.getChildren().add(smallPic1);
                GridPane.setConstraints(pane1, 0, 1);
                grid.getChildren().add(pane1);
                
                pane2.getChildren().add(smallPic2);
                GridPane.setConstraints(pane2, 1, 1);
                grid.getChildren().add(pane2);

                /* creating new games */
                game1 = new Game(pane1, 2, oldList);
                game2 = new Game(pane2, 2);

                this.numberOfGames++;
                break;
            }

            case 2: {
                /* handling image */
                smallPic3 = new ImageView();
                smallPic3.setImage(backgroundImage);
                smallPic3.setFitHeight(310);
                smallPic3.setFitWidth(580);

                /* adding pane to game */
                pane3.setPrefSize(580, 320);
                pane3.getChildren().add(smallPic3);
                GridPane.setConstraints(pane3, 0, 2);
                grid.getChildren().add(pane3);

                /* creating new game */
                game3 = new Game(pane3, 2);
                this.numberOfGames++;
                break;
            }

            case 3: {
                /* handling image */
                smallPic4 = new ImageView();
                smallPic4.setImage(backgroundImage);
                smallPic4.setFitHeight(310);
                smallPic4.setFitWidth(580);

                /* adding pane to game */
                pane4.setPrefSize(580, 320);
                pane4.getChildren().add(smallPic4);
                GridPane.setConstraints(pane4, 1, 2);
                grid.getChildren().add(pane4);

                /* creating new game */
                game4 = new Game(pane4, 2);
                this.numberOfGames++;
                break;
            }
            
            default: {
                AlertBox.display("Error", "Cannot create new instance of game!");
            }

        }
    }

    /* handling save button */
    private void saveGame() throws FileNotFoundException {

        TextInputDialog dialog = new TextInputDialog("save1");
        dialog.setTitle("Save game");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter a file name:");

        Optional<String> result = dialog.showAndWait();
        String res = "dest-client/saves/" + result.get();
        System.out.println(res);

        Save save = new Save(mainGame, game1, game2, game3, game4, numberOfGames, res);
    }

    /* handling load button */
    private void loadGame() throws FileNotFoundException {
        List<String> choices = getSaves();

        ChoiceDialog<String> dialog = new ChoiceDialog<>("example", choices);
        dialog.setTitle("Load game");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose a saved game:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            numberOfUndo = 0;
            if (numberOfGames == 1) {
                mainPane.getChildren().remove(0, 65);
                grid.getChildren().remove(mainPane);
            } else if (numberOfGames == 2) {
                pane1.getChildren().remove(0, 65);
                grid.getChildren().remove(pane1);
                pane2.getChildren().remove(0, 65);
                grid.getChildren().remove(pane2);
            } else if (numberOfGames == 3) {
                pane1.getChildren().remove(0, 65);
                grid.getChildren().remove(pane1);
                pane2.getChildren().remove(0, 65);
                grid.getChildren().remove(pane2);
                pane3.getChildren().remove(0, 65);
                grid.getChildren().remove(pane3);
            } else {
                pane1.getChildren().remove(0, 65);
                grid.getChildren().remove(pane1);
                pane2.getChildren().remove(0, 65);
                grid.getChildren().remove(pane2);
                pane3.getChildren().remove(0, 65);
                grid.getChildren().remove(pane3);
                pane4.getChildren().remove(0, 65);
                grid.getChildren().remove(pane4);
            }
            String fileName = result.get();
            Load load;
            if(fileName == "example")
	load = new Load(mainPane, pane1, pane2, pane3, pane4, mainGame, game1, game2, game3, game4, grid, "examples/example.txt");

            else
	load = new Load(mainPane, pane1, pane2, pane3, pane4, mainGame, game1, game2, game3, game4, grid, "dest-client/saves/"+fileName);

        }

        
    }

    /* handling undo button */
    private void undoGame() {
        if (numberOfUndo == 0) {
            AlertBox.display("Undo not found", "No more undo available!");
            return;
        }
        undo.getUndoState();
        numberOfUndo--;
        shiftUndoStackOut();

    }

    /* handling help button */
    private void helpGame() {
        if (numberOfGames == 1) { // if there is only one game
            Help help = new Help(mainGame);
            if (help.canIHelpYou()) {
                return;
            }
        }
        if (numberOfGames > 1) { // if there are at least 2
            Help help1 = new Help(game1);
            Help help2 = new Help(game2);
            if (!help1.canIHelpYou()) {
                if (help2.canIHelpYou()) {
                    return;
                }
            } else return;
        }
        if (numberOfGames > 2) { // if there are at least 3
            Help help3 = new Help(game3);
            if (help3.canIHelpYou()) {
                return;
            }

        }
        if (numberOfGames > 3) { // if there are 4 games
            Help help4 = new Help(game4);
            if (help4.canIHelpYou()) {
                return;
            }
        }
        
    }

    /* handling exit button */
    private void exitGame() {
        if (ConfirmBox.display("Exiting", "Are you sure you want to quit?")) {
            window.close();
        }
    }

    private void shiftUndoStackOut() {
        undo = undo1;
        undo1 = undo2;
        undo2 = undo3;
        undo3 = undo4;
    }

    public ArrayList getSaves() {
        File f = new File("dest-client/saves/");
        ArrayList<String> saves = new ArrayList<String>(Arrays.asList(f.list()));
        saves.add(0, "example");

        return saves;
    }
}
