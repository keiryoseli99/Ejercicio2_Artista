package mx.tecnm.tepic.ladm_u3_ejercicio1_sqliteopenhelper_artista_insertar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!

        //RECUPERAR DATA
        val artista = Artista(this).consultar(id)
        actualizarNombre.setText(artista.nombre)
        actualizarDomicilio.setText(artista.domicilio)

        button2.setOnClickListener {
            val artistaActualizar = Artista(this)
            artistaActualizar.nombre = actualizarNombre.text.toString()
            artistaActualizar.domicilio = actualizarDomicilio.text.toString()

            val resultado = artistaActualizar.actualizar(id)
            if (resultado) {
                Toast.makeText(this, "EXITO SE ACTUALIZO", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "ERROR! NO SE LOGRO ACTUALIZAR", Toast.LENGTH_LONG).show()
            }
        }

        button3.setOnClickListener {
            finish()
        }
    }
}