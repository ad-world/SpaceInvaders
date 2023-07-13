package spaceinvaders

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import spaceinvaders.scenes.AbstractScene
import spaceinvaders.scenes.Battleground
import spaceinvaders.scenes.OpeningScene

class SpaceInvaders : Application(), AbstractScene {
    override fun start(stage: Stage) {
        stage.apply {
            minWidth = gameWidth;
            minHeight = gameHeight;
            maxHeight = gameHeight
            maxWidth = gameWidth
        }
        val battlegroundLevel1 = Battleground(1, stage, 0, 0)
        val openingScene = OpeningScene(stage, battlegroundLevel1).apply {
            setOnKeyPressed {
                when (it.code) {
                    KeyCode.DIGIT1, KeyCode.ENTER -> {
                        val battlegroundScene = Scene(battlegroundLevel1, gameWidth, gameHeight)
                        setScene(stage, battlegroundScene, "Space Invaders - Level 1")
                        battlegroundLevel1.getArmy().startAttack()
                        battlegroundLevel1.requestFocus()
                    }
                    KeyCode.DIGIT2 -> {
                        val level2 = Battleground(2, stage, 0, 0);
                        val battlegroundScene = Scene(level2, gameWidth, gameHeight)
                        setScene(stage, battlegroundScene, "Space Invaders - Level 2")
                        level2.getArmy().startAttack()
                        level2.requestFocus()
                    }
                    KeyCode.DIGIT3 -> {
                        val level3 = Battleground(3, stage, 0, 0);
                        val battlegroundScene = Scene(level3, gameWidth, gameHeight)
                        setScene(stage, battlegroundScene, "Space Invaders - Level 3")
                        level3.getArmy().startAttack()
                        level3.requestFocus()
                    }
                    KeyCode.Q -> {
                        stage.close()
                        Platform.exit()
                    }
                    else -> {}
                }
            }
        }

        val mainScene = Scene(openingScene, gameWidth, gameHeight)
        with(stage) {
            scene = mainScene
            title = "Space Invaders"
            show()
        }

        openingScene.requestFocus()
    }
}

fun main() {
    Application.launch(SpaceInvaders::class.java)
}