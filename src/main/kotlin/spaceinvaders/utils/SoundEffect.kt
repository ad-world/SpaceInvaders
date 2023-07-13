package spaceinvaders.utils

import javafx.scene.media.AudioClip

class SoundEffect(url: String) {
    private val newUrl = javaClass.classLoader.getResource(url)?.toString()
    private val clip: AudioClip = AudioClip(newUrl).apply {
        volume = 1.0
    }
    init {
        clip.play()
    }
}