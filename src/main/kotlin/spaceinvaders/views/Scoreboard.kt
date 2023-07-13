package spaceinvaders.views

import javafx.geometry.Insets
import javafx.scene.layout.HBox
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.Text

class Scoreboard(startingLevel: Int, startingScore: Int, startingLives: Int, startingHighscore: Int): HBox() {
    private var score = startingScore;
    private var level = startingLevel;
    private var lives = startingLives;
    private var highscore = startingHighscore

    private val scoreTextField = Text("Score: $score").apply {
        fill = Paint.valueOf("#ffffff")
        font = Font.font(20.0)
    }
    private val levelTextField = Text("Level: $level").apply {
        fill = Paint.valueOf("#ffffff")
        font = Font.font(20.0)
    }
    private val livesTextField = Text("Lives: $lives").apply {
        fill = Paint.valueOf("#ffffff")
        font = Font.font(20.0)
    }
    private val highscoreTextField = Text("High score: $highscore").apply {
        fill = Paint.valueOf("#ffffff")
        font = Font.font(20.0)
    }

    init {
        padding = Insets(5.0, 0.0, 0.0, 5.0)
        children.addAll(scoreTextField, levelTextField, livesTextField, highscoreTextField)
        spacing = 20.0
    }

    fun updateScore(addToScore: Int) {
        score += addToScore;
        scoreTextField.text = "Score: $score"

        if(score > highscore) {
            highscore = score;
            highscoreTextField.text = "High score: $highscore"
        }
    }

    fun updateLevel(newLevel: Int) {
        level = newLevel
        levelTextField.text = "Level: $level"
    }

    fun decreaseLives() {
        lives -= 1;
        livesTextField.text = "Lives: $lives"
    }

    fun getLives(): Int {
        return lives
    }

    fun getLevel(): Int {
        return level
    }

    fun getScore(): Int {
        return score;
    }


    fun clearLives() {
        lives = 0
    }

    fun getHighscore(): Int {
        return highscore
    }
}