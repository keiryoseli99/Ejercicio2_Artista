package mx.tecnm.tepic.ladm_u3_ejercicio1_sqliteopenhelper_artista_insertar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var idArtistas = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mostrarArtistasCapturados()
        button.setOnClickListener {
            val artista = Artista(this)

            artista.nombre = campoNombre.text.toString()
            artista.domicilio = campoDomicilio.text.toString()

            val resultado = artista.insertar()
            if (resultado) {
                Toast.makeText(this, "EXITO! SE INSERTO", Toast.LENGTH_LONG).show()
                campoNombre.text.clear()
                campoDomicilio.text.clear()
                mostrarArtistasCapturados()
            } else {
                Toast.makeText(this, "ERROR NO SE INSERTOR", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun mostrarArtistasCapturados() {
        var arregloArtista = Artista(this).consultar()

        listaArtistas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arregloArtista)
        idArtistas.clear()
        idArtistas = Artista(this).obtenerIDs()
        activarEvento(listaArtistas)
    }

    private fun activarEvento(listaArtistas: ListView) {
        listaArtistas.setOnItemClickListener { adapterView, view, indiceSeleccionado, l ->

            val idSeleccionado = idArtistas[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUE DESEA HACER CON EL ARTISTA?")
                .setPositiveButton("EDITAR"){d,i-> actualizar(idSeleccionado)}
                .setNegativeButton("ELIMINAR"){d,i-> eliminar(idSeleccionado)}
                .setNeutralButton("CANCELAR"){d,i-> d.cancel() }
                .show()
        }
    }

    private fun actualizar(idSeleccionado: Int) {
        val intento = Intent(this, MainActivity2::class.java)
        intento.putExtra("idActualizar",idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(this)
            .setMessage("¿DESEAS ACTUALIZAR LA LISTA?")
            .setPositiveButton("SI"){d,i-> mostrarArtistasCapturados()}
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }

    private fun eliminar(idSeleccionado: Int) {
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR ID ${idSeleccionado}?")
            .setPositiveButton("SI"){d,i->
                val resultado = Artista(this).eliminar(idSeleccionado)
                if (resultado) {
                    Toast.makeText(this, "SE ELIMINO CON EXITO", Toast.LENGTH_LONG).show()
                    mostrarArtistasCapturados()
                } else {
                    Toast.makeText(this, "ERROR NO SE LOGRO ELIMINAR", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }

}