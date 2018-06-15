/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author lukasdekret
 */
public class Load {

    public Load (Pane mainPane, Pane pane1, Pane pane2, Pane pane3, Pane pane4, Game mainGame, Game game1, Game game2, Game game3, Game game4, GridPane grid, String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        
        int numOfGames = Integer.parseInt(scan.nextLine());
        Image backgroundImage = new Image("file:lib/img/background.jpg");

        if (numOfGames == 1) {
            Solitaire.numberOfGames = 1;
            ImageView largePic = new ImageView();
            largePic.setImage(backgroundImage);
            largePic.setFitHeight(635);
            largePic.setFitWidth(1180);

            mainPane.setPrefSize(1200, 635);

            mainPane.getChildren().add(largePic);

            grid.setPadding(new Insets(10, 10, 10, 10));
            GridPane.setConstraints(mainPane, 0, 1);
            grid.getChildren().add(mainPane);
            loadGame(mainPane, mainGame, scan);

        } else if (numOfGames > 1) {

            Solitaire.numberOfGames = 2;
            ImageView smallPic1 = new ImageView();
            smallPic1.setImage(backgroundImage);
            smallPic1.setFitHeight(310);
            smallPic1.setFitWidth(580);

            ImageView smallPic2 = new ImageView();
            smallPic2.setImage(backgroundImage);
            smallPic2.setFitHeight(310);
            smallPic2.setFitWidth(580);

            /* creating new panes and extracting oldlist of stacks from main game*/
            pane1.setPrefSize(580, 320);
            pane1.getChildren().clear();
            pane2.setPrefSize(580, 320);

            /* adding panes to game */
            pane1.getChildren().add(smallPic1);
            GridPane.setConstraints(pane1, 0, 1);
            grid.getChildren().add(pane1);

            pane2.getChildren().add(smallPic2);
            GridPane.setConstraints(pane2, 1, 1);
            grid.getChildren().add(pane2);
            loadGame(pane1, game1, scan);
            loadGame(pane2, game2, scan);

            if (numOfGames > 2) {
                Solitaire.numberOfGames = 3;

                /* handling image */
                ImageView smallPic3 = new ImageView();
                smallPic3.setImage(backgroundImage);
                smallPic3.setFitHeight(310);
                smallPic3.setFitWidth(580);

                /* adding pane to game */
                pane3.setPrefSize(580, 320);
                pane3.getChildren().add(smallPic3);
                GridPane.setConstraints(pane3, 0, 2);
                grid.getChildren().add(pane3);

                loadGame(pane3, game3, scan);

                if (numOfGames > 3) {
                    
                    Solitaire.numberOfGames = 4;
                    
                    /* handling image */
                    ImageView smallPic4 = new ImageView();
                    smallPic4.setImage(backgroundImage);
                    smallPic4.setFitHeight(310);
                    smallPic4.setFitWidth(580);

                    /* adding pane to game */
                    pane4.setPrefSize(580, 320);
                    pane4.getChildren().add(smallPic4);
                    GridPane.setConstraints(pane4, 1, 2);
                    grid.getChildren().add(pane4);
                    loadGame(pane4, game4, scan);
                }
            }
        } 

        //while(scan.hasNextLine()) {
        //    String line = scan.nextLine();
        //}
    }

    private void loadGame (Pane pane, Game game, Scanner scan) {
        List<GeneralStack> list = new ArrayList<>();

        int count = Integer.parseInt(scan.nextLine()); // hiddenHelpStack
        list.add(new HiddenHelpStack(1, count));
        Stack<Card> s = list.get(0).getStack();
        for (int i = 0; i < count; i++) {
            s.push(stringToCard(scan.nextLine()));
        }

        count = Integer.parseInt(scan.nextLine()); // shownHelpStack
        list.add(new ShownHelpStack(2, count));
        s = list.get(1).getStack();
        for (int i = 0; i < count; i++) {
            s.push(stringToCard(scan.nextLine()));
        }
        
        for (int i = 2; i < 9; i++) {
            count = Integer.parseInt(scan.nextLine()); // hiddenWorkingStack
            list.add(new HiddenWorkingStack(i+5, count));
            s = list.get(i).getStack();
            for (int j = 0; j < count; j++) {
                s.push(stringToCard(scan.nextLine()));
            }
        }

        for (int i = 2; i < 9; i++) {
            count = Integer.parseInt(scan.nextLine()); // shownWorkingStack
            int onTopValue = Integer.parseInt(scan.nextLine()); // onTopValue
            String onTopColor = scan.nextLine();
            list.add(new ShownWorkingStack(i+5, count, onTopValue, onTopColor));
            s = list.get(i+7).getStack();
            for (int j = 0; j < count; j++) {
                s.push(stringToCard(scan.nextLine()));
            }
        }

        for (int i = 3; i < 7; i++) {
            count = Integer.parseInt(scan.nextLine()); // finalStack
            String symbol = scan.nextLine();
            list.add(new FinalStack(i, count, findSymbol(symbol)));
            s = list.get(i+13).getStack();
            FinalStack ss = (FinalStack) list.get(i+13);
            for (int j = 0; j < count; j++) {
                //s.push(stringToCard(scan.nextLine()));
                //ss.setOnTopValue(count);
                ss.putingCard(stringToCard(scan.nextLine()));
            }
        }
        game.setList(list);
    }

    private Card.Symbol findSymbol (String symbol) {
        if ("H".equals(symbol)){
                return Card.Symbol.HEARTS;
            }
            if ("S".equals(symbol)){
                return Card.Symbol.SPADES;
            }
            if ("C".equals(symbol)){
                return Card.Symbol.CLUBS;
            }
            if ("D".equals(symbol)){
                return Card.Symbol.DIAMONDS;
            }
            return Card.Symbol.NONE;
    }

    private Card stringToCard (String name) {
        int leng;
            leng = name.length();
            int position = 0;
            String symbol = "";
            Card.Symbol cSymbol = Card.Symbol.NONE;

            String value;
            int value1;
            String id;
            int id1;
            for(int i = 0 ;i < leng ;i++){
                char c = name.charAt(i);
                if (c == 'H' || c == 'S' || c == 'C' || c == 'D'){
                    position = name.indexOf(Character.toString(c));
                }
            }
            value = name.substring( 0, position);
            value1 = Integer.parseInt(value);

            symbol = name.substring( position, position+1);
            if ("H".equals(symbol)){
                cSymbol = Card.Symbol.HEARTS;
            }
            if ("S".equals(symbol)){
                cSymbol = Card.Symbol.SPADES;
            }
            if ("C".equals(symbol)){
                cSymbol = Card.Symbol.CLUBS;
            }
            if ("D".equals(symbol)){
                cSymbol = Card.Symbol.DIAMONDS;
            }

            //Card.symbol cSymbol;
            id = name.substring( position + 1, leng);
            id1 = Integer.parseInt(id);
            Card card1 = new OneCard(value1, cSymbol, id1, true);
            return card1;
    }

}
