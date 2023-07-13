package spaceinvaders.entities

import javafx.animation.AnimationTimer
import javafx.scene.Node
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import spaceinvaders.scenes.AbstractScene
import spaceinvaders.scenes.Battleground

class Army(level: Int, val battleground: Battleground): GridPane(), AbstractScene {
    private val numRows = 10
    private val numCols = 5

    private val animation = object: AnimationTimer() {
        private var speed: Double = level.toDouble()

        override fun handle(now: Long) {
            if(this@Army.translateX >= gameWidth - this@Army.width) {
                dropEnemyBomb()
                speed *= -1;
                this@Army.translateY += 20.0
            }

            if(this@Army.translateX <= 0 && speed < 0) {
                dropEnemyBomb()
                speed *= -1
                this@Army.translateY += 20.0
            }

            // roughly in the center of the screen
            val randomDouble = kotlin.random.Random.nextDouble()
            val dropBomb: Boolean = when(level) {
                1 -> randomDouble > 0.995
                2 -> randomDouble > 0.99
                3 -> randomDouble > 0.98
                else -> false
            }

            if(dropBomb) {
                dropEnemyBomb()
            }

            if(this@Army.translateY + this@Army.height > gameHeight - 20) {
                battleground.loseGame()
            }

            this@Army.translateX += speed;
        }

        fun increaseSpeed() {
            if(speed > 0) {
                speed += 0.1 + level / 15
            } else {
                speed -= 0.1 + level / 15
            }
        }

        fun startAttack() {
            start()
        }

        fun stopAttack() {
            stop()
        }
    }

    init {
        hgap = 10.0
        vgap = 10.0

        for(i in 0 until numRows) {
            rowConstraints.add(RowConstraints())
        }

        for(i in 0 until numCols) {
            columnConstraints.add(ColumnConstraints())
        }

        for(row in 0 until numRows) {
            for(column in 0 until numCols) {
                val enemyType = when(column) {
                    0 -> EnemyType.ENEMY_ONE
                    1 -> EnemyType.ENEMY_TWO
                    2 -> EnemyType.ENEMY_TWO
                    3 -> EnemyType.ENEMY_THREE
                    4 -> EnemyType.ENEMY_THREE
                    else -> EnemyType.ENEMY_ONE
                }
                add(Enemy(enemyType, this), row, column)
            }
        }
        autosize()
        translateY = 40.0
    }

    fun startAttack() {
        animation.startAttack()
    }

    fun stopAttack() {
        animation.stopAttack()
    }

    fun dropEnemyBomb() {
        val randomEnemy = children.random() as Enemy?

        if(randomEnemy != null) {
            battleground.children.add(randomEnemy.dropBomb())
        }
    }

    fun kill(child: Node) {
        if(child is Enemy) {
            children.remove(child)
            battleground.getScoreboard().updateScore(10);
            goFaster()

            if(children.size == 0) {
                stopAttack()
                battleground.nextLevelOrWin()
            }
        }
    }

    private fun goFaster() {
        animation.increaseSpeed()
    }
}