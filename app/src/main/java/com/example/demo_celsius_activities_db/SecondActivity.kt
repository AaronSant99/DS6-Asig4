package com.example.demo_celsius_activities_db

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment_Resultados())
            .commit()

        val resultado = intent.getDoubleExtra("RESULTADO", 0.0)
        val dbHelper = DatabaseHelper(this)
        dbHelper.addConversionResult(resultado)

    }

}