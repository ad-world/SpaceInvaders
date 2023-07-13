package spaceinvaders.entities

import javafx.animation.AnimationTimer
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import spaceinvaders.scenes.Battleground
import spaceinvaders.utils.SoundEffect
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class PlayerEntity(private val battleground: Battleground): ImageView() {
    private var lastBullet: LocalDateTime = LocalDateTime.now()
    private var numBullets: Int = 0

    private val animation = object: AnimationTimer() {
        private var speed = 0.0

        override fun handle(now: Long) {
            if(this@PlayerEntity.translateX < 50) {
                if(speed > 0) {
                    this@PlayerEntity.translateX += speed;
                }
            } else if(this@PlayerEntity.translateX >= 950.0 - fitWidth) {
                if(speed < 0) {
                    this@PlayerEntity.translateX += speed;
                }
            } else {
                this@PlayerEntity.translateX += speed;
            }

            if(this@PlayerEntity.translateX < 50 || this@PlayerEntity.translateX > 950.0 - fitWidth) {
                speed = 0.0;
            }
        }

        fun animateLeft() {
            speed = -5.0;
            start();
        }

        fun animateRight () {
            speed = 5.0
            start();
        }

        fun stopAnimation() {
            speed = 0.0;
        }
    }

    init {
        image = Image("images/player.png")
        fitWidth = 50.0
        isPreserveRatio = true
    }

    fun movePlayerRight() {
        animation.animateRight()
    }

    fun movePlayerLeft() {
        animation.animateLeft()
    }

    fun stopMoving() {
        animation.stopAnimation()
    }

    fun fireBullet(): PlayerAmmunitionEntity? {
        val currentTime = LocalDateTime.now()
        val period = ChronoUnit.SECONDS.between(lastBullet, currentTime)

        return if(period >= 1) {
            lastBullet = LocalDateTime.now();
            numBullets = 1
            SoundEffect("sounds/shoot.wav")
            PlayerAmmunitionEntity(this.translateX, this.translateY, this.fitWidth, battleground)
        } else {
            if(numBullets > 1) {
                null;
            } else {
                numBullets += 1;
                SoundEffect("sounds/shoot.wav")
                PlayerAmmunitionEntity(this.translateX, this.translateY, this.fitWidth, battleground)
            }
        }

    }
}