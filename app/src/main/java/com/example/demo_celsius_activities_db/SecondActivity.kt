package com.example.demo_celsius_activities_db

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(findViewById(R.id.toolbar2))

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment_Resultados())
            .commit()

        val resultado = intent.getDoubleExtra("RESULTADO", 0.0)
        val dbHelper = DatabaseHelper(this)
        dbHelper.addConversionResult(resultado)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export_csv -> {  // ← corregido aquí
                exportDataAsCsvAndShare()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exportDataAsCsvAndShare() {
        val results = DatabaseHelper(this).getAllResults()
        if (results.isEmpty()) {
            Toast.makeText(this, "No hay datos para compartir.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val file = File(cacheDir, "conversiones.csv")
            val outputStream = FileOutputStream(file)

            outputStream.write(("ID,Valor,Tipo\n").toByteArray())
            results.forEach {
                outputStream.write("${it.id},${it.valor},${it.tipo}\n".toByteArray())
            }
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(intent, "Compartir CSV"))

        } catch (e: Exception) {
            Toast.makeText(this, "Error compartiendo CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
