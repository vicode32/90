

package com.example.myapplicationdemo

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.sqrt



class MainActivity : AppCompatActivity(), SensorEventListener {
    private val sensor: Sensor? = null
    private var sensorManager: SensorManager? = null
    private var mAccelerometerData = FloatArray(3)
    private var mMagnetometerData = FloatArray(3)
    private var dem: TextView? = null
    private var gravity = FloatArray(3)
    private lateinit var fed: TextView
     // component from another activity
     private var lastsaved = System.currentTimeMillis()
    private lateinit var acc: TextView
    private lateinit var  sensorManager1: SensorManager
    private lateinit var sensor1: Sensor
    private var gravity1  = FloatArray(3)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dem = findViewById(R.id.dem)
        acc = findViewById(R.id.acc)
        fed = findViewById(R.id.fed)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager!!.registerListener(this@MainActivity, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this@MainActivity, sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL)
        val sensorManager1 = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager1.registerListener(this,sensorManager1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)
    }

    companion object {
        private var ACCE_MIN_DATA = 1000
    }


    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        val sensorType = event.sensor.type
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            val alpha: Float = 0.8f
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

        } else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {

            mMagnetometerData[0] = event.values[0]
            mMagnetometerData[1] = event.values[1]
            mMagnetometerData[1] = event.values[2]

            val R = FloatArray(9)
            val I = FloatArray(9)

            SensorManager.getRotationMatrix(R, I, mAccelerometerData, mMagnetometerData)
            val A_D: FloatArray = event.values.clone()
            val A_W = FloatArray(3)
            A_W[0] = R[0] * A_D[0] + R[1] * A_D[1] + R[2] * A_D[2]
            A_W[1] = R[3] * A_D[0] + R[4] * A_D[1] + R[5] * A_D[2]
            A_W[2] = R[6] * A_D[0] + R[7] * A_D[1] + R[8] * A_D[2]

            dem!!.text = " x = ${A_W[0]}" + " y = ${A_W[1]}" + "z = ${A_W[2]}"


        }
               if(sensorType == Sensor.TYPE_ACCELEROMETER) {
                   if ((System.currentTimeMillis() - lastsaved) > ACCE_MIN_DATA) {
                       lastsaved = System.currentTimeMillis()
                       val alpha: Float = 0.8f
                       // using low-pass filter
                       gravity1[0] = alpha * gravity1[0] + (1 - alpha) * event.values[0]
                       gravity1[1] = alpha * gravity1[1] + (1 - alpha) * event.values[1]
                       gravity1[2] = alpha * gravity1[2] + (1 - alpha) * event.values[2]

                       // using high-pass filter
                       val x = event.values[0] - gravity1[0]
                       val y = event.values[1] - gravity1[1]
                       val z = event.values[2] - gravity1[2]
                       val t = sqrt((x * x + y * y + z * z).toDouble())
                       acc.text = " x = $x  " + "y = $y" + "z = $z"
                       fed.text = "$t"
                   }
               }
    }



    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {


    }
}



