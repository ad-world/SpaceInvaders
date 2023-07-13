package spaceinvaders.scenes

import javafx.scene.Scene
import javafx.stage.Stage

interface AbstractScene {
    val gameHeight: Double
        get() = 700.0
    val gameWidth: Double
        get() = 1000.0

    fun setScene(stage: Stage, scene: Scene, sceneTitle: String) {
        stage.title = sceneTitle;
        stage.scene = scene
    }
}