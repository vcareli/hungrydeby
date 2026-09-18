package com.example.hungrydeby

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas

class Bird (
	var posX: Float,
	var posY: Float,
	var passarinhoVX: Float,
	var passarinhoVY: Float,
	val gravidade: Float,
	var isFlying: Boolean,
	val raio: Float,
	val paint: Paint
) {
	fun update() {
		if (isFlying) {
			passarinhoVY += gravidade       //Altera veloc Vert
			posY += passarinhoVY     // Move para baixo
			posX += passarinhoVX     //Move para direita
		}
	}

	fun dessinBird(canvas: Canvas) {
		paint.color = Color.BLUE
		paint.isAntiAlias = true
		canvas.drawCircle(posX, posY, raio, paint)
	}
}