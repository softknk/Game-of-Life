import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final int WINDOW_SIZE = 800;
    public static final int GRID_SIZE = 40;
    public static final double ALIVE_PROBABILITY = 0.5; // used for random cell distribution
    public static final int ANIMATION_SPEED = 200;
    public static final Color ALIVE_COLOR = Color.rgb(99, 222, 51);
    public static final Color DEAD_COLOR = Color.rgb(168, 128, 54);

    private final GameOfLife game_of_life;
    private boolean paused;

    {
        game_of_life = new GameOfLife(GRID_SIZE);
        paused = true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        Button[][] cells = new Button[game_of_life.size()][game_of_life.size()];

        for (int i = 0; i < game_of_life.size(); i++) {
            for (int j = 0; j < game_of_life.size(); j++) {
                double button_size = WINDOW_SIZE / (double) game_of_life.size();

                Button button = new Button();
                button.setPrefSize(button_size, button_size);
                button.setBackground(null);
                button.setStyle("-fx-border-width: 1px; -fx-border-color: black");

                Color button_color = game_of_life.getCellStateAt(i, j) ? ALIVE_COLOR : DEAD_COLOR;
                button.setBackground(new Background(new BackgroundFill(button_color, CornerRadii.EMPTY, Insets.EMPTY)));

                int _i = i, _j = j; // lambda expression: used variables have to be effectively final
                button.setOnAction(event -> game_of_life.changeCellStateAt(_i, _j));

                cells[i][j] = button;
                grid.add(button, j, i);
            }
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_SPEED), e -> {
            if (!paused) game_of_life.update();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells.length; j++) {
                    Color button_color = game_of_life.getCellStateAt(i, j) ? ALIVE_COLOR : DEAD_COLOR;
                    cells[i][j].setBackground(new Background(new BackgroundFill(button_color, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        grid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) paused = !paused;
            else if (event.getCode() == KeyCode.C) game_of_life.clear();
            else if (event.getCode() == KeyCode.R) game_of_life.random(ALIVE_PROBABILITY);
        });

        Scene scene = new Scene(grid, WINDOW_SIZE - 10, WINDOW_SIZE);

        stage.setScene(scene);
        stage.setTitle("Conway's Game of Life");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/favicon.png")));
        stage.show();
    }
}
