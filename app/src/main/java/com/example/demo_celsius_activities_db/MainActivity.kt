package com.example.demo_celsius_activities_db

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

/**
 * Justin Barrios, Cédula: 8-983-1021
 * Luis Monterrosa, Cédula: 8-1014-1095
 * Aaron Santamaria, Cédula: 3-742-1763
 */

class MainActivity : AppCompatActivity(), View.OnClickListener  {
    lateinit var boton1 : Button
    lateinit var boton2 : Button
    lateinit var boton3 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton1 = findViewById(R.id.button1)
        boton1.setOnClickListener(this)

        boton2 = findViewById(R.id.button2)
        boton2.setOnClickListener(this)

        boton3 = findViewById(R.id.button3)
        boton3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> {
                val celsius = findViewById<EditText>(R.id.editText1)
                val resultado = celsius_fahrenheit(celsius.text.toString().toDouble())

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("RESULTADO", resultado)
                startActivity(intent)
            }
            R.id.button2 -> {
                val celsius = findViewById<EditText>(R.id.editText1)
                val resultado = celsius_kelvin(celsius.text.toString().toDouble())

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("RESULTADO", resultado)
                startActivity(intent)
            }
            R.id.button3 -> {
                val celsius = findViewById<EditText>(R.id.editText1)
                val resultado = celsius_rankine(celsius.text.toString().toDouble())

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("RESULTADO", resultado)
                startActivity(intent)
            }
        }
    }

    private fun celsius_fahrenheit(celsius:Double): Double {
        val fahrenheit = (celsius * 9/5)+32
        return fahrenheit
    }

    private fun celsius_kelvin(celsius:Double): Double {
        val kelvin = celsius + 273.75
        return kelvin
    }

    private fun celsius_rankine(celsius:Double): Double {
        val rankine = (celsius * 9/5)+491.67
        return rankine
    }

}
