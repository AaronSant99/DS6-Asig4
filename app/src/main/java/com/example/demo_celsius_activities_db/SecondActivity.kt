package com.example.demo_celsius_activities_db

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class SecondActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_WRITE_EXTERNAL_STORAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment_Resultados())
            .commit()

        // Guarda el resultado recibido
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
            R.id.export_csv -> {
                exportDataAsCSV()
                true
            }
            R.id.export_xls -> {
                exportDataAsXLS()
                true
            }
            R.id.export_xlsx -> {
                exportDataAsXLSX()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Exportar como CSV
    private fun exportDataAsCSV() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )
            return
        }
        val dbHelper = DatabaseHelper(this)
        val results = dbHelper.getAllResults()
        if (results.isEmpty()) {
            Toast.makeText(this, "No hay datos para exportar.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val exportDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!exportDir.exists()) exportDir.mkdirs()
            val file = File(exportDir, "conversiones.csv")
            val outputStream = FileOutputStream(file)
            outputStream.write(("ID,Valor,Tipo\n").toByteArray())
            for (result in results) {
                val line = "${result.id},${result.valor},${result.tipo}\n"
                outputStream.write(line.toByteArray())
            }
            outputStream.flush()
            outputStream.close()
            Toast.makeText(
                this,
                "Archivo CSV exportado en Descargas.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error exportando CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Exportar como XLS
    private fun exportDataAsXLS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )
            return
        }
        val dbHelper = DatabaseHelper(this)
        val results = dbHelper.getAllResults()
        if (results.isEmpty()) {
            Toast.makeText(this, "No hay datos para exportar.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val workbook: Workbook = HSSFWorkbook()
            val sheet = workbook.createSheet("Conversiones")
            val header = sheet.createRow(0)
            header.createCell(0).setCellValue("ID")
            header.createCell(1).setCellValue("Valor")
            header.createCell(2).setCellValue("Tipo")
            results.forEachIndexed { index, result ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(result.id.toDouble())
                row.createCell(1).setCellValue(result.valor)
                row.createCell(2).setCellValue(result.tipo)
            }
            val exportDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!exportDir.exists()) exportDir.mkdirs()
            val file = File(exportDir, "conversiones.xls")
            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)
            outputStream.close()
            workbook.close()
            Toast.makeText(
                this,
                "Archivo XLS exportado en Descargas.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error exportando XLS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Exportar como XLSX
    private fun exportDataAsXLSX() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )
            return
        }
        val dbHelper = DatabaseHelper(this)
        val results = dbHelper.getAllResults()
        if (results.isEmpty()) {
            Toast.makeText(this, "No hay datos para exportar.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val workbook: Workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Conversiones")
            val header = sheet.createRow(0)
            header.createCell(0).setCellValue("ID")
            header.createCell(1).setCellValue("Valor")
            header.createCell(2).setCellValue("Tipo")
            results.forEachIndexed { index, result ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(result.id.toDouble())
                row.createCell(1).setCellValue(result.valor)
                row.createCell(2).setCellValue(result.tipo)
            }
            val exportDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!exportDir.exists()) exportDir.mkdirs()
            val file = File(exportDir, "conversiones.xlsx")
            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)
            outputStream.close()
            workbook.close()
            Toast.makeText(
                this,
                "Archivo XLSX exportado en Descargas.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error exportando XLSX: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Permiso para Android 10 o menor
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido. Repite la exportación.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso denegado para escribir archivos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}