package com.example.demo_celsius_activities_db


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

/*
* Justin Barrios, Cedula: 8-983-1021
* Luis Monterrosa, Cedula: 8-1014-1095
* Aaron Santamaria, Cedula: 3-742-1763
* */

class MainActivity : AppCompatActivity() {

    private lateinit var boton1: RadioButton
    private lateinit var boton2: RadioButton
    private lateinit var boton3: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        boton1 = findViewById(R.id.radioButton)
        boton2 = findViewById(R.id.radioButton2)
        boton3 = findViewById(R.id.radioButton3)
        radioGroup = findViewById(R.id.radioGroup)
        editText = findViewById(R.id.editText1)

        // Establecer listener
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val celsius = editText.text.toString().toDoubleOrNull()

            if (!(celsius == null)) {
            val resultado = when (checkedId) {
                R.id.radioButton -> celsius_fahrenheit(celsius)
                R.id.radioButton2 -> celsius_kelvin(celsius)
                R.id.radioButton3 -> celsius_rankine(celsius)
                else -> return@setOnCheckedChangeListener
            }
            ConversionType.tipoConv=when (checkedId) {
                R.id.radioButton -> "K"
                R.id.radioButton2 -> "F"
                R.id.radioButton3 -> "R"
                else -> "?"
            }
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("RESULTADO", resultado)
            startActivity(intent)
        }else {
                Toast.makeText( this,"ingrese un numero para convertir", Toast.LENGTH_LONG).show()
        }
        }
    }

    private fun celsius_fahrenheit(celsius: Double): Double {
        val conver=(celsius * 9 / 5) + 32
        return (conver * 100).toInt() / 100.0
    }

    private fun celsius_kelvin(celsius: Double): Double {
        val conver = celsius + 273.15
        return (conver * 100).toInt() / 100.0
    }

    private fun celsius_rankine(celsius: Double): Double {
        val conver=(celsius * 9 / 5) + 491.67
        return  (conver * 100).toInt() / 100.0

    }
}
