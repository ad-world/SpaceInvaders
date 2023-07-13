package spaceinvaders.scenes

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import spaceinvaders.entities.Army
import spaceinvaders.entities.PlayerEntity
import spaceinvaders.utils.SoundEffect
import spaceinvaders.views.Scoreboard

class Battleground(level: Int, private val stage: Stage, startingScore: Int, highscore: Int): Pane(), AbstractScene {
    private var army: Army
    private val scoreboard: Scoreboard
    private var player: PlayerEntity
    init {
        padding = Insets(0.0, 0.0, 10.0, 0.0)
        background = Background(BackgroundFill(Color.BLACK, null, null))

        army = Army(level, this)
        scoreboard = Scoreboard(level, startingScore, 3, highscore)

        player = PlayerEntity(this).apply {
            translateX = gameWidth / 2 - this.fitWidth/2;
            translateY = gameHeight - 70;
        }

        setOnKeyPressed {
            when (it.code) {
                KeyCode.A, KeyCode.LEFT -> {
                    player.movePlayerLeft()
                }
                KeyCode.D, KeyCode.RIGHT -> {
                    player.movePlayerRight()
                }
                KeyCode.ESCAPE -> {
                    army.stopAttack()
                }
                KeyCode.SPACE -> {
                    val bullet = player.fireBullet();
                    if(bullet != null) {
                        children.add(bullet)
                    }
                }
                KeyCode.Q -> {
                    stage.close()
                    Platform.exit()
                }
                else -> {}
            }
            it.consume()
        }

        setOnKeyReleased {
            if(it.code == KeyCode.A || it.code == KeyCode.LEFT) {
                player.stopMoving()
            } else if(it.code == KeyCode.D || it.code == KeyCode.RIGHT) {
                player.stopMoving()
            }
            it.consume()
        }
        children.add(scoreboard)
        children.add(player)
        children.add(army)
    }

    fun getArmy(): Army {
        return this.army
    }

    fun getScoreboard(): Scoreboard {
        return this.scoreboard
    }

    fun getPlayer(): PlayerEntity {
        return this.player
    }

    fun playerRespawnOrDie() {
        val numLives = scoreboard.getLives()
        SoundEffect("sounds/explosion.wav")
        if(numLives == 0) {
            loseGame()
        } else {
            children.remove(player);
            player = PlayerEntity(this).apply {
                translateX = (25 until (gameWidth / 2 - this.fitWidth/2).toInt()).random().toDouble();
                translateY = gameHeight - 70;
            }
            children.add(player);
            scoreboard.decreaseLives()
        }
    }

    fun nextLevelOrWin() {
        val currentLevel = scoreboard.getLevel()

        if (currentLevel == 3) {
            army.stopAttack()
            val winner = GameOverScene(this, stage)
            val winnerScene = Scene(winner, gameWidth, gameHeight)
            setScene(stage, winnerScene, "Game Over - You Won!")
            winner.requestFocus()
        } else {
            val newLevel = currentLevel + 1;
            children.remove(army)
            army = Army(newLevel, this)
            children.add(army)
            scoreboard.updateLevel(newLevel)
            stage.title = "Space Invaders - Level $newLevel"
            army.startAttack()
        }
    }

    fun loseGame() {
        children.clear();
        army.stopAttack()
        scoreboard.clearLives()
        val loser = GameOverScene(this, stage)
        val loserScene = Scene(loser, gameWidth, gameHeight)
        setScene(stage, loserScene, "Game Over - You lost!")
        loser.requestFocus()
    }
}