package com.carlospinan.brickbreaker.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.carlospinan.brickbreaker.game.view.BrickBreakerView

/**
 * @author Carlos Piñan
 */
class GameActivity : AppCompatActivity() {

    private lateinit var gameView: BrickBreakerView

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        gameView = BrickBreakerView(this)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(gameView.game)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            gameView.game,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

}