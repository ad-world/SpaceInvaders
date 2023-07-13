package spaceinvaders.entities

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import spaceinvaders.utils.SoundEffect

enum class EnemyType {
    ENEMY_ONE,
    ENEMY_TWO,
    ENEMY_THREE
}

class Enemy(private val enemyType: EnemyType, private val army: Army): ImageView() {
    init {
        val imageUrl = when(enemyType) {
            EnemyType.ENEMY_ONE -> "images/enemy1.png"
            EnemyType.ENEMY_TWO -> "images/enemy2.png"
            EnemyType.ENEMY_THREE -> "images/enemy3.png"
        }

        image = Image(imageUrl)
        fitWidth = 40.0
        isPreserveRatio = true
    }

    fun dropBomb(): EnemyAmmunition {
        return EnemyAmmunition(
            enemyType,
            this.layoutX + army.translateX,
            this.layoutY + army.translateY,
            this.fitWidth,
            battleground = army.battleground);
    }

    fun getEnemyType(): EnemyType {
        return enemyType
    }

    fun die() {
        SoundEffect("sounds/invaderkilled.wav")
        army.kill(this)
    }
}