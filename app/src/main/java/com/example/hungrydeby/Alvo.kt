package com.example.hungrydeby
import android.graphics.Paint
import android.graphics.Canvas

class Alvo (
	var x: Float,
	var y: Float,
	val altura: Float,
	val largura: Float,
	val massa: Float
) {
	var wasHit: Boolean = false

	fun paintAlvo (paint: Paint, canvas: Canvas) {
		if (!wasHit) {
			canvas.drawRect(this.x, this.y, this.x + this.largura, this.y + this.altura, paint)
		}
	}

	fun verificaColisao(bird: Bird): Boolean {
		if (wasHit) return false
		val deltaX = bird.posX - bird.posX.coerceIn(x, x + largura) //Raio - posicao mais proxima
		val deltaY = bird.posY - bird.posY.coerceIn(y, y + altura)  //do bird com o alvo
		val dist: Float = kotlin.math.sqrt(deltaX * deltaX + deltaY * deltaY)
		if (dist <= bird.raio) wasHit = true
		return wasHit
	}

	fun reset() {
		wasHit = false
	}
}
