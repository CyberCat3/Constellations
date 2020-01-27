package com.cybercat3.constellation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws InterruptedException {
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double particleDensity = 1920 * 1080 / Data.instance().particles1080p;

        Parent root = new VBox(canvas);
        Scene scene = new Scene(root, 1280, 720);
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        stage.setScene(scene);
        stage.setTitle("Constellation");
        stage.show();
        scene.setCursor(Cursor.NONE);
        stage.setFullScreenExitHint("");

        ObservableMap<String, Boolean> keysPressed = FXCollections.observableHashMap();

        List<Particle> particles = new ArrayList<>();

        scene.setOnKeyPressed(event -> {
            keysPressed.put(event.getCode().getName(), true);
        });
        scene.setOnKeyReleased(event -> {
            keysPressed.put(event.getCode().getName(), false);
        });

        keysPressed.addListener(new MapChangeListener<String, Boolean>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Boolean> change) {

            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.F11)) {
                stage.setFullScreen(!stage.isFullScreen());
            } else if (event.getCode().equals(KeyCode.F5)) {
                particles.clear();
            }
        });
        stage.setFullScreen(true);

        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int targetParticles = (int) (canvas.getWidth() * canvas.getHeight() / particleDensity);
                if (particles.size() < targetParticles) particles.add(new Particle(gc));
                else if (particles.size() > targetParticles) particles.remove(particles.size() - 1);
                Platform.runLater(() -> {
                    gc.setFill(Color.BLACK);
                    for (Particle p : particles) p.update(gc);
                    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
                    for (int i = 0; i < particles.size() - 1; ++i) {
                        for (int j = i + 1; j < particles.size(); ++j) {
                            particles.get(i).drawLine(particles.get(j),gc);
                        }
                    }
                });
            }
        };
        at.start();
    }

    public static void main(String[] args) {
	    launch(args);
    }
}
