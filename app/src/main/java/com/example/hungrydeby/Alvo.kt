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
	fun paintAlvo (paint: Paint, canvas: Canvas) {
		canvas.drawRect(this.x, this.y, this.x + this.largura, this.y + this.altura, paint)
	}
}
