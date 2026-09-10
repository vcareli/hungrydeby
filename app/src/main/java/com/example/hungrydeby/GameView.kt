package com.example.hungrydeby

import android.view.SurfaceHolder
import android.view.SurfaceView
import android.content.Context

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var isPlaying: Boolean = false
    private var gameThread: Thread? = null

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        // A tela de desenho acabou de ser criada
        isPlaying = true
        gameThread = Thread {
            while(isPlaying) {
                //Update
                //Render
            }
        }
        gameThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        // A tela mudou de tamanho/orientação
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        // A tela foi destruída (ex: app foi minizado ou fechado)
        isPlaying = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}