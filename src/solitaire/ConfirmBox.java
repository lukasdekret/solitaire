/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;

/**
 *
 * @author lukasdekret
 */
public class ConfirmBox {

    static boolean answer;
    public static boolean help = true;

    public static boolean display(String title, String message) {
        help = true;
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); // can not interact with others
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(110);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));

        Pane pane = new Pane();
        label.setTranslateX(20);
        label.setTranslateY(0);
        label.setPrefSize(230, 50);

        pane.getChildren().add(label);
        GridPane.setConstraints(pane, 0, 0);
        grid.getChildren().add(pane);

        HBox layout = new HBox(10);
        layout.getChildren().addAll(yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        
        GridPane.setConstraints(layout, 0, 1);
        grid.getChildren().add(layout);

        Scene scene = new Scene(grid);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                answer = help;
                window.close();
            }
            if (e.getCode() == KeyCode.TAB) {
                help = !help;
            }
        });

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
