package spaceinvaders.scenes

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

class GameOverScene(battleground: Battleground, private val stage: Stage): VBox(), AbstractScene {
    private val scoreboard = battleground.getScoreboard()

    init {
        padding = Insets(100.0, 0.0, 50.0, 0.0)
        spacing = 20.0
        alignment = Pos.BASELINE_CENTER
        background = Background(BackgroundFill(Color.BLACK, null, null))

        onKeyPressed = EventHandler {
            if(it.code == KeyCode.R) {
                startNewGame()
            } else if(it.code == KeyCode.Q) {
                quit()
            }
        }

        children.addAll(
            ImageView("images/logo.png").apply {
                fitWidth = 300.0
                isPreserveRatio = true
            },
            Text("GAME OVER").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(50.0)
            },
            Text("YOU ${if(scoreboard.getLives() == 0) "LOST" else "WON"}!").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(40.0)
            },
            Text("Level: ${scoreboard.getLevel()}").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(40.0)
            },
            Text("High Score: ${scoreboard.getHighscore()}").apply {
                fill = Paint.valueOf("#ffffff")
                font = Font.font(40.0)
            },
            Text("Click here (or click the R key) to restart!").apply {
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
                    startNewGame()
                }
            },
            Text("Click here (or click the Q key) to quit!").apply {
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
                     quit()
                }
            }
        )
    }

    private fun startNewGame() {
        val levelOne = Battleground(1, stage, 0, scoreboard.getHighscore())
        val battlegroundScene = Scene(levelOne, gameWidth, gameHeight)
        setScene(stage, battlegroundScene, "Space Invaders - Level 1")
        levelOne.getArmy().startAttack()
        levelOne.requestFocus()
    }

    private fun quit() {
        stage.close()
        Platform.exit()
    }
}