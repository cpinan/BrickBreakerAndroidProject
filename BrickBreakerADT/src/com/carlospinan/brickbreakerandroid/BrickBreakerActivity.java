package com.carlospinan.brickbreakerandroid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.carlospinan.brickbreakerandroid.engine.BrickBreakerView;

/**
 * Created by mac on 14/07/13.
 */
public class BrickBreakerActivity extends MainActivity implements
		SensorEventListener {

	private BrickBreakerView view;
	private SensorManager m_sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_sensor = (SensorManager) getSystemService(SENSOR_SERVICE);

		view = new BrickBreakerView(this);
		setContentView(view);

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				if (view != null) {
					float dx = event.values[1];
					// float dy = event.values[0];
					// float dz = event.values[2];
					view.setAccelerometer(dx, 0, 0);
				}
				break;
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

	}

	@Override
	protected void onPause() {
		super.onPause();

		m_sensor.unregisterListener(this);

		if (view != null)
			view.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		m_sensor.registerListener(this,
				m_sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		if (view != null)
			view.onResume();
	}
}
