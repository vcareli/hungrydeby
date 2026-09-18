package com.example.hungrydeby

import android.view.SurfaceHolder
import android.view.SurfaceView
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import kotlin.text.compareTo

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var isPlaying: Boolean = false
    private var gameThread: Thread? = null
    private var alvo: Alvo? = null
    private var paint_bird = Paint()
    private var paint_alvo = Paint()
    private var bird: Bird? = null
    private final var sensibilidade = 0.1f
    private var xInicial = 0f
    private var yInicial = 0f

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // A tela de desenho acabou de ser criada
        isPlaying = true
        bird = Bird(0.1f * width,
            0.8f * height,
            0f,
            0f,
            1.2f,
            false,
            60f,
            paint_bird)
        alvo = Alvo(width.toFloat() * 0.8f,
            height.toFloat() * 0.6f,
            200f,
            100f,
            1f)
        gameThread = Thread {
            while(isPlaying) {
                update()
                render()
            }
        }
        gameThread?.start()
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
            paint_alvo.color = Color.RED
            alvo?.paintAlvo(paint_alvo, canvas)
            bird?.dessinBird(canvas)
            // Libera o Canvas e envia para a tela do celular
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update() {
        bird?.update()
        bird?.let { b ->
            if (b.posX > width || b.posY > height) {
                b.posX = 0.1f * width
                b.posY = 0.8f * height
                b.passarinhoVX = 0f
                b.passarinhoVY = 0f
                b.isFlying = false
            }
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
                    bird?.posX = event.x
                    bird?.posY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                bird?.isFlying = true
                val deltaX = event.x - xInicial
                val deltaY = event.y - yInicial
                bird?.passarinhoVX = -deltaX * sensibilidade
                bird?.passarinhoVY = -deltaY * sensibilidade
            }
        }
        //return super.onTouchEvent(event)      //retorna falso
        return true
    }
}