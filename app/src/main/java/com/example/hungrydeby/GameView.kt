package com.example.hungrydeby

import android.view.SurfaceHolder
import android.view.SurfaceView
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var isPlaying: Boolean = false
    private var gameThread: Thread? = null
    private var alvo: Alvo? = null
    private var paint = Paint()
    private var passarinhoPaint = Paint()
    private var  passarinhoX = 0f
    private var passarinhoY = 0f
    private var passarinhoR = 60f
    private var passarinhoVX = 12f
    private var passarinhoVY = -25f
    private final var gravidade = 1.2f
    private final var sensibilidade = 0.1f
    private var xInicial = 0f
    private var yInicial = 0f
    private var isFlying = false

    init {
        holder.addCallback(this)
        passarinhoPaint.color = Color.RED
        passarinhoPaint.isAntiAlias = true
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // A tela de desenho acabou de ser criada
        isPlaying = true
        passarinhoX = 0.1f * width
        passarinhoY = 0.8f * height
        gameThread = Thread {
            while(isPlaying) {
                update()
                render()
            }
        }
        gameThread?.start()
        alvo = Alvo(width.toFloat() * 0.8f,
            height.toFloat() * 0.6f,
            200f,
            100f,
            1)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // A tela mudou de tamanho/orientação
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // A tela foi destruída (ex: app foi minizado ou fechado)
        isPlaying = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun render() {
        // 1. Bloqueamos o Canvas para desenho
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            // Desenhamos coisas no canvas usando o Paint
            canvas.drawColor(Color.WHITE)
            alvo?.paintAlvo(paint, canvas)
            canvas.drawCircle(passarinhoX, passarinhoY, passarinhoR, passarinhoPaint)
            // Libera o Canvas e envia para a tela do celular
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update() {
        if (isFlying) {
            passarinhoVY += gravidade       //Altera veloc Vert
            passarinhoY += passarinhoVY     // Move para baixo
            passarinhoX += passarinhoVX     //Move para direita
        }
        if (passarinhoX > width || passarinhoY > height) {
            passarinhoX = 0.1f * width
            passarinhoY = 0.8f * height
            passarinhoVX = 0f
            passarinhoVY = 0f
            isFlying = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                xInicial = event.x
                yInicial = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.actionMasked == MotionEvent.ACTION_MOVE) {
                    passarinhoX = event.x
                    passarinhoY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                isFlying = true
                val deltaX = event.x - xInicial
                val deltaY = event.y - yInicial
                passarinhoVX = -deltaX * sensibilidade
                passarinhoVY = -deltaY * sensibilidade
            }
        }
        //return super.onTouchEvent(event)      //retorna falso
        return true
    }
}