package spaceinvaders.scenes

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class OpeningScene(private val stage: Stage, private val battleground: Battleground): VBox(), AbstractScene {
    init {
        padding = Insets(100.0, 0.0, 50.0, 0.0)
        spacing = 20.0
        alignment = Pos.BASELINE_CENTER
        background = Background(BackgroundFill(Color.BLACK, null, null))

        children.addAll(
            ImageView("images/logo.png").apply {
                fitWidth = 300.0
                isPreserveRatio = true
            },
            Text("Instructions").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(50.0)
            },
            Text("ENTER - Start Game").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(20.0)
                onMouseEntered = EventHandler {
                    this.isUnderline = true
                    cursor = Cursor.HAND
                }
                onMouseExited = EventHandler {
                    this.isUnderline = false;
                    cursor = Cursor.DEFAULT
                }
                onMousePressed = EventHandler {
                    val battlegroundScene = Scene(battleground, gameWidth, gameHeight)
                    setScene(stage, battlegroundScene, "Space Invaders - Level 1")
                    battleground.requestFocus()
                }
            },
            Text("A or <, D or > - Move ship left or right").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(20.0)
            },
            Text("SPACE - Fire!").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(20.0)
            },
            Text("Q - Quit Game").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(20.0)
            },
            Text("1, 2, 3 - Start game at a specific level").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(20.0)
            },
        )
    }
}