package com.example.myapplicationdemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity3 : AppCompatActivity(),SensorEventListener {

    private lateinit var  sensorManager1: SensorManager
    private lateinit var sensor1: Sensor
    private var gravity1  = FloatArray(3)
    private  var lastsaved = System.currentTimeMillis()
    private lateinit var acc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val sensorManager1 = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager1.registerListener(this,sensorManager1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)
        acc = findViewById(R.id.acc)




    }




    override fun onSensorChanged(event: SensorEvent?) {



        val alpha: Float = 0.8f
        // using low-pass filter
        gravity1[0] = alpha * gravity1[0] + (1-alpha) * event!!.values[0]
        gravity1[1] = alpha * gravity1[1] + (1-alpha) * event.values[1]
        gravity1[2] = alpha * gravity1[2] + (1 - alpha) * event.values[2]

        // using high-pass filter
        val x = event.values[0] - gravity1[0]
        val y = event.values[1] - gravity1[1]
        val z = event.values[2] - gravity1[2]

        acc.text = " x = $x  " + "y = $y" + "z = $z"








    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}

private fun SensorManager.registerListener(mainActivity3: MainActivity3, defaultSensor: Sensor?, sensorDelayNormal: Int) {

  }
