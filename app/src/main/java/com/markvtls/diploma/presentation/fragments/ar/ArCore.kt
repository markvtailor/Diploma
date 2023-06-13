package com.markvtls.diploma.presentation.fragments.ar

import android.opengl.EGLConfig
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import cn.easyar.*
import com.google.ar.core.Session
import java.nio.ByteBuffer
import java.util.ArrayList
import javax.microedition.khronos.opengles.GL10

class MainRenderer(private val session: Session): GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        println("Surface created")
        //здесь нужно выполнить инициализацию контекста
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        println("Surface changed")
        //здесь можно обновить контекст (например, размеры сцены)
    }

    override fun onDrawFrame(gl: GL10?) {
        println("Draw frame")
        val frame = session.update()
        //... логика отрисовки
    }
}