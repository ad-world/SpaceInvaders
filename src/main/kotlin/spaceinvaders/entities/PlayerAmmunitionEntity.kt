package spaceinvaders.entities

import javafx.animation.AnimationTimer
import javafx.geometry.Bounds
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import spaceinvaders.scenes.AbstractScene
import spaceinvaders.scenes.Battleground
import spaceinvaders.utils.SoundEffect


class PlayerAmmunitionEntity(startX: Double, startY: Double, entityWidth: Double, battleground: Battleground): ImageView(),
    AbstractScene {
    private val animation = object: AnimationTimer() {
        private var speed = 10

        override fun handle(now: Long) {
            val army = battleground.getArmy()
            val enemies = army.children
            val bulletBounds = this@PlayerAmmunitionEntity.boundsInParent

            for(enemy in enemies) {
                val enemyBounds: Bounds = enemy.localToScene(enemy.boundsInLocal)

                if(enemyBounds.intersects(bulletBounds)) {
                    val castEnemy = enemy as Enemy;
                    castEnemy.die()
                    stop()
                    battleground.children.remove(this@PlayerAmmunitionEntity)
                    break;
                }
            }

            if(this@PlayerAmmunitionEntity.translateY <= -30) {
                stop()
            }

            this@PlayerAmmunitionEntity.translateY -= speed;
        }
    }

    init {
        image = Image("images/player_bullet.png")
        fitWidth = 5.0
        isPreserveRatio = true

        translateX = startX + entityWidth / 2 - 2;
        translateY = startY;

        animation.start()
    }

}