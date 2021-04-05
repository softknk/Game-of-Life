import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final int SIZE = 800;
    public static final Color ALIVE_COLOR = Color.rgb(71, 250, 35), DEAD_COLOR = Color.rgb(255, 68, 43);

    private final GameOfLife game_of_life;

    {
        game_of_life = new GameOfLife(80);
        game_of_life.random(0.5);
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
                double rectangle_size = SIZE / (double) game_of_life.size();
                Rectangle rectangle = new Rectangle(rectangle_size, rectangle_size);

                rectangle.setFill(game_of_life.getCellStateAt(i, j) ? ALIVE_COLOR : DEAD_COLOR);

                cells[i][j] = rectangle;
                grid.add(rectangle, j, i);
            }
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            game_of_life.update();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells.length; j++) {
                    cells[i][j].setFill(game_of_life.getCellStateAt(i, j) ? ALIVE_COLOR : DEAD_COLOR);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(grid, SIZE - 10, SIZE - 10);

        stage.setScene(scene);
        stage.setTitle("Conway's Game of Life");
        stage.setResizable(false);
        stage.show();
    }
}
