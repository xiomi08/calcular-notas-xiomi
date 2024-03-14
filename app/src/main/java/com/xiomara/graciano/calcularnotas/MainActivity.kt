package com.xiomara.graciano.calcularnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var ingresarNombre : EditText
    private lateinit var ingresarPorcentaje : EditText
    private lateinit var ingresarNota : EditText
    private lateinit var finalizar : Button
    private lateinit var guardar : Button
    private lateinit var progreso : ProgressBar
    private var estudianteActual: Estudiante = Estudiante()
    private lateinit var SiguienteEstudiante : Button
    private lateinit var promedio : TextView
    private lateinit var NotaFinal : TextView


    private var porcentajeAcumulado = 0
    val listaNotas: MutableList<Double> = mutableListOf()
    val listaPorcentaje: MutableList<Int> = mutableListOf()
    val listaEstudiante: MutableList<Estudiante> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ingresarNombre = findViewById(R.id.ingresarNombre)
        ingresarPorcentaje = findViewById(R.id.ingresarPorcentaje)
        ingresarNota = findViewById(R.id.ingresarNota)
        finalizar = findViewById(R.id.finalizar)
        guardar = findViewById(R.id.guardar)
        progreso = findViewById(R.id.progreso)
        SiguienteEstudiante = findViewById(R.id.SiguienteEstudiante)
        promedio = findViewById(R.id.promedio)
        NotaFinal = findViewById(R.id.NotaFinal)

        SiguienteEstudiante.setOnClickListener {
            nuevoEstudiante()
        }

        finalizar.setOnClickListener {
            NotaFinal.text= "nota final :" + estudianteActual.Notafinal()
            promedio.text= "promedio :" + estudianteActual.calcularPromedio()
            SiguienteEstudiante.isEnabled = true

        }


        guardar.setOnClickListener{

            val nota = ingresarNota.text.toString()
            val porcentaje = ingresarPorcentaje.text.toString()
            val nombre = ingresarNombre.text.toString()


            if (nota.isNullOrEmpty() || porcentaje.isNullOrEmpty() || nombre.isNullOrEmpty()){

                Toast.makeText(this,"los datos ingresados no son validos",
                    Toast.LENGTH_LONG ).show()
                return@setOnClickListener
            }

            if( validarNota(nota.toDouble()) && validarPorcentaje(porcentaje.toInt()) && validarNombre
                    (nombre)){

                listaNotas.add(nota.toDouble())

                listaPorcentaje.add(porcentaje.toInt())
                porcentajeAcumulado += porcentaje.toInt()

                ingresarNombre.isEnabled = false
                ingresarNota.text.clear()
                ingresarPorcentaje.text.clear()

                actualizarProgreso(porcentajeAcumulado)

                Toast.makeText(this, "se ingreso la  nota correctamnete",
                    Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this,"los datos ingresados no son validos",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }
    fun actualizarProgreso(porcentaje: Int){

        progreso.progress = porcentaje

        if(porcentaje >= 100){
            finalizar.isEnabled = true
            estudianteActual.nombre = (ingresarNombre.text.toString())
            estudianteActual.porcentajes = listaPorcentaje
            estudianteActual.notas = listaNotas
            listaEstudiante.add(estudianteActual)
        }

    }

    fun  nuevoEstudiante (){
        ingresarNombre.text.clear()
        progreso.progress = 0
        porcentajeAcumulado = 0
        ingresarNota.text.clear()
        ingresarPorcentaje.text.clear()
        promedio.text = ""
        NotaFinal.text = ""

        listaNotas.clear()
        listaPorcentaje.clear()

        finalizar.isEnabled=false
        SiguienteEstudiante.isEnabled=false
        ingresarNombre.isEnabled = true
    }


    fun validarNota(nota : Double): Boolean{

        return  nota >=0 && nota <=5

    }

    fun validarPorcentaje(porcentaje: Int): Boolean{
        return  porcentajeAcumulado + porcentaje <=100

    }

    fun validarNombre(nombre: String): Boolean{
        return !nombre.matches(Regex (".*\\d.*"))
    }

}

      class Estudiante() {
          var nombre: String = ""
          var notas :List<Double> = listOf()
          var porcentajes :List<Int> = listOf()

          fun calcularPromedio (): Double{
              var sumaNotas = 0.0
              for (n in notas){
                  sumaNotas +- n

              }

              return Math.round((sumaNotas/ notas.size) * 1000.0) / 1000.0
          }

          fun Notafinal():Double{
              var notaFinal : Double = 0.0
              var contador = 0

              for (n in notas){
                  notaFinal +- (n + porcentajes[contador]) / 100
                  contador ++
              }
              return Math.round(notaFinal * 1000.0) / 1000.0

          }

      }
