package spaceinvaders.entities

import javafx.animation.AnimationTimer
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import spaceinvaders.scenes.AbstractScene
import spaceinvaders.scenes.Battleground
import spaceinvaders.utils.SoundEffect

class EnemyAmmunition(enemyType: EnemyType, startX: Double, startY: Double, entityWidth: Double, battleground: Battleground): ImageView(),
    AbstractScene {
    private val animation = object: AnimationTimer() {
        private var speed = 4;

        override fun handle(now: Long) {
            val player = battleground.getPlayer()
            val playerBounds = player.boundsInParent
            val bombBounds = this@EnemyAmmunition.boundsInParent

            if(playerBounds.intersects(bombBounds)) {
                battleground.playerRespawnOrDie()
                stop()
                battleground.children.remove(this@EnemyAmmunition)
            }

            if(this@EnemyAmmunition.translateY >= gameHeight + 30) {
                stop()
            }

            this@EnemyAmmunition.translateY += speed
        }
    }

    init {
        val soundUrl = when(enemyType) {
            EnemyType.ENEMY_ONE -> "sounds/fastinvader1.wav"
            EnemyType.ENEMY_TWO -> "sounds/fastinvader2.wav"
            EnemyType.ENEMY_THREE -> "sounds/fastinvader3.wav"
        }

        val imageUrl = when(enemyType) {
            EnemyType.ENEMY_ONE -> "images/bullet1.png"
            EnemyType.ENEMY_TWO -> "images/bullet2.png"
            EnemyType.ENEMY_THREE -> "images/bullet3.png"
        }

        image = Image(imageUrl)
        fitWidth = 10.0
        isPreserveRatio = true

        translateX = startX + entityWidth / 2 - 2;
        translateY = startY;
        SoundEffect(soundUrl)
        animation.start()
    }
}