package main.java.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static main.java.view.ClientView.*;

public class AccessView {
    private GridPane grid;
    private Scene scene;

    AccessView(Stage stage) {
        // create the grid
        this.grid = new GridPane();
        this.grid.setAlignment(javafx.geometry.Pos.CENTER);
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setPadding(new Insets(25, 25, 25, 25));

        // create the scene
        this.scene = new Scene(this.grid, STAGE_COLUMNS, STAGE_ROWS);

        // add the username field
        TextField username_field = new TextField();
        username_field.setPromptText("Username");
        this.grid.add(username_field, 0, 1);

        // add the password text field
        PasswordField password_field = new PasswordField();
        password_field.setPromptText("Password");
        this.grid.add(password_field, 0, 2);

        // add the status label
        Label status = new Label("");
        this.grid.add(status, 0, 4);

        // add the login button
        Button login_button = new Button("Login");
        this.grid.add(login_button, 0, 3);

        // login button event handler
        login_button.setOnAction(event -> {
            String username = username_field.getText();
            String password = password_field.getText();
            new Thread(() -> {
                boolean result = client.loginPlayer(username, password);
                Platform.runLater(() -> {
                    if (result) {
                        client.setUsername(username);
                        client.connectPlayer();
                        game_view = new GameView(stage);
                        client.requireBoardPlayers();
                        stage.setScene(game_view.getScene());
                    } else {
                        status.setText("Invalid login");
                        username_field.clear();
                        password_field.clear();
                    }
                });
            }).start();
        });

        // create the register button
        Button register_button = new Button("Register");
        grid.add(register_button, 1, 3);

        // register button event handler
        register_button.setOnAction(event -> {
            String username = username_field.getText();
            String password = password_field.getText();
            new Thread(() -> {
                boolean result = client.registerPlayer(username, password);
                Platform.runLater(() -> {
                    if (result) {
                        client.setUsername(username);
                        client.connectPlayer();
                        game_view = new GameView(stage);
                        client.requireBoardPlayers();
                        stage.setScene(game_view.getScene());
                    } else {
                        status.setText("Invalid registration");
                        username_field.clear();
                        password_field.clear();
                    }
                });
            }).start();
        });
    }

    public Scene getScene() {
        return this.scene;
    }
}
