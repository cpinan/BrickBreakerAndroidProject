package com.carlospinan.brickbreaker.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
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