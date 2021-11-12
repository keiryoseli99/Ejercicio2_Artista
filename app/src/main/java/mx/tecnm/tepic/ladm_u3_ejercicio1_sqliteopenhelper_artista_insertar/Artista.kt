package mx.tecnm.tepic.ladm_u3_ejercicio1_sqliteopenhelper_artista_insertar

import android.content.ContentValues
import android.content.Context
import android.text.BoringLayout

class Artista(p:Context) {
    var nombre = ""
    var domicilio = ""
    val pnt = p

    fun insertar() : Boolean {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("nombre",nombre)
        datos.put("domicilio",domicilio)

        val resultado = tablaArtista.insert("ARTISTA",null,datos)
        if (resultado == -1L){
            return false
        }
        return true
    }

    fun consultar() : ArrayList<String> {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).writableDatabase
        val resultado = ArrayList<String>()
        val cursor = tablaArtista.query("ARTISTA", arrayOf("*"),null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                var dato = cursor.getString(1)+"\n"+cursor.getString(2)+" (${cursor.getInt(0)})"
                resultado.add(dato)
            } while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }

    fun obtenerIDs() : ArrayList<Int> {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaArtista.query("ARTISTA", arrayOf("*"),null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                resultado.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        return resultado
    }

    fun eliminar(idEliminar:Int) : Boolean {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).writableDatabase
        val resultado = tablaArtista.delete("ARTISTA", "ID=?", arrayOf(idEliminar.toString()))
        if (resultado == 0) return false
        return true
    }

    fun consultar(idABuscar: String) : Artista {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).readableDatabase
        val cursor = tablaArtista.query("ARTISTA", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val artista = Artista(MainActivity())
        if (cursor.moveToFirst()) {
            artista.nombre = cursor.getString(1)
            artista.domicilio = cursor.getString(2)
        }
        return artista
    }

    fun actualizar(idActualizar: String) : Boolean {
        val tablaArtista = BaseDatos(pnt,"casaArte",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("nombre",nombre)
        datos.put("domicilio",domicilio)

        val resultado = tablaArtista.update("ARTISTA", datos, "ID=?", arrayOf(idActualizar))
        if (resultado == 0) return false
        return true
    }
}