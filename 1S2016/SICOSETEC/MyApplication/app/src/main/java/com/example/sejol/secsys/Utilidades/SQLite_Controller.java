package com.example.sejol.secsys.Utilidades;

/**
 * Created by sejol on 4/6/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sejol.secsys.Clases.Ronda;
import com.example.sejol.secsys.Clases.Ruta;
import com.example.sejol.secsys.Clases.Tag;
import com.example.sejol.secsys.Clases.Usuario;

import java.util.ArrayList;

public class SQLite_Controller extends SQLiteOpenHelper {

    public static final String DATABASE_NOMBRE = "SecsysBD.db";
    //_________________________________________________________________
    public static final String TABLE_USUARIO   = "TUsuario";
    public static final String COLUMN_NOMBRE     = "nombre";
    public static final String COLUMN_USUARIO    = "usuario";
    public static final String COLUMN_CONTRASEÑA = "contraseña";

    //_________________________________________________________________
    public static final String TABLE_RONDA   = "TRonda";
    public static final String COLUMN_ID_RND   = "codigo";
    //public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_FECHA    = "fecha";
    public static final String REF_USUARIO     = "usuario";

    //_________________________________________________________________
    public static final String TABLE_TAG_RND = "T_RND_Tag";
    public static final String COLUMN_ID_TAG   = "codigo";
    public static final String COLUMN_HORA     = "hora";
    public static final String REF_RND         = "ronda";

    //_________________________________________________________________
    public static final String TABLE_RUTA    = "TRuta";
    public static final String COLUMN_ID_RUT  = "codigo";

    //_________________________________________________________________
    public static final String TABLE_TAG_RUT  = "T_RUT_Tag";
    //public static final String COLUMN_ID_TAG = "codigo";
    public static final String REF_RUT         = "ruta";

    public SQLite_Controller(Context context) {
        super(context, DATABASE_NOMBRE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_USUARIO +
                        "(" + COLUMN_USUARIO    + " text primary key," +
                              COLUMN_CONTRASEÑA + " text, " +
                              COLUMN_NOMBRE     + " text )");

        db.execSQL(
                "create table " + TABLE_RONDA +
                        "(" + COLUMN_ID_RND   + " text primary key," +
                              COLUMN_NOMBRE     + " text," +
                              COLUMN_FECHA      + " text," +
                              REF_USUARIO       + " text," +
                             " FOREIGN KEY ("+REF_USUARIO+") REFERENCES "+TABLE_USUARIO+"("+COLUMN_USUARIO+"));");

        db.execSQL(
                "create table " + TABLE_TAG_RND +
                        "(" + COLUMN_ID_TAG   + " text primary key," +
                              COLUMN_HORA     + " text," +
                              REF_RND   + " text, " +
                             " FOREIGN KEY ("+REF_RND+") REFERENCES "+TABLE_RONDA+"("+COLUMN_ID_RND+"));");

        db.execSQL(
                "create table " + TABLE_RUTA +
                        "(" + COLUMN_ID_RUT   + " text primary key," +
                              COLUMN_NOMBRE   + " text);" );

        db.execSQL(
                "create table " + TABLE_TAG_RUT +
                        "(" + COLUMN_ID_TAG + " text primary key," +
                        REF_RUT + " text, " +
                        " FOREIGN KEY (" + REF_RUT + ") REFERENCES " + TABLE_RUTA + "(" + COLUMN_ID_RUT + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  {
        db.execSQL("drop table if exists " + TABLE_USUARIO);
        onCreate(db);
    }

    public boolean insertUser(String nombre, String usuario, String contraseña){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_NOMBRE , nombre);
        registro.put(COLUMN_USUARIO, usuario);
        registro.put(COLUMN_CONTRASEÑA, contraseña);
        bd.insert(TABLE_USUARIO, null, registro);
        bd.close();
        return true;
    }

    public Usuario getUsuario(String usuario, String contraseña)
    {
        SQLiteDatabase bd = this.getReadableDatabase();

        Cursor cursor = bd.rawQuery("select * from " + TABLE_USUARIO +
                " where " + COLUMN_USUARIO + " = '" + usuario + "'" +
                " and " + COLUMN_CONTRASEÑA + " = '" + contraseña + "'", null);

        Usuario mae = new Usuario();

        if (cursor.moveToFirst()) {
            do {
                mae.setUsuario (cursor.getString(0));
                mae.setContraseña (cursor.getString(1));
                mae.setNombre  (cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return mae;
    }

    public boolean insertRonda(String codigo, String nombre, String fecha, String usuario){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_ID_RND , codigo);
        registro.put(COLUMN_NOMBRE, nombre);
        registro.put(COLUMN_FECHA, fecha);
        registro.put(REF_USUARIO, usuario);
        bd.insert(TABLE_RONDA, null, registro);
        bd.close();
        return true;
    }

    public boolean insertTagRND(String condigo, String hora, String ronda){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_ID_TAG, condigo);
        registro.put(COLUMN_HORA, hora);
        registro.put(REF_RND, ronda);
        bd.insert(TABLE_TAG_RND, null, registro);
        bd.close();
        return true;
    }

    public ArrayList<Ronda> getRondas(Usuario usuario){
        String selectQuery =
                "SELECT  * FROM " + TABLE_RONDA + " WHERE " +
                        COLUMN_USUARIO + " = " + "'" + usuario.getUsuario() + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Ronda> arrayListData = new ArrayList<Ronda>();
        if (cursor.moveToFirst()) {
            do {
                Ronda ronda = new Ronda();
                ronda.setCodigo (cursor.getString(0));
                ronda.setNombre (cursor.getString(1));
                ronda.setFecha  (cursor.getString(2));
                ronda.setUsuario(cursor.getString(3));
                arrayListData.add(ronda);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

    public boolean insertRuta(String codigo,String nombre){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_ID_RUT , codigo);
        registro.put(COLUMN_NOMBRE , nombre);
        bd.insert(TABLE_RUTA, null, registro);
        bd.close();
        return true;
    }

    public boolean borrarRuta(String nombre){
        SQLiteDatabase bd = this.getWritableDatabase();

        bd.delete(TABLE_TAG_RUT, REF_RUT + "='" + nombre + "'", null);

        bd.delete(TABLE_RUTA, "nombre='" + nombre + "'", null);

        return true;
    }

    public boolean insertTagRUT(String condigo, String ronda){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_ID_TAG , condigo);
        registro.put(REF_RUT, ronda);
        bd.insert(TABLE_TAG_RUT, null, registro);
        bd.close();
        return true;
    }

    public ArrayList<Ruta> getRutas(){
        String selectQuery = "SELECT  * FROM " + TABLE_RUTA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Ruta> arrayListData = new ArrayList<Ruta>();
        if (cursor.moveToFirst()) {
            do {
                Ruta ruta = new Ruta();
                ruta.setCodigo (cursor.getString(0));
                ruta.setNombre (cursor.getString(1));
                arrayListData.add(ruta);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

    public ArrayList<Tag> getTagsDeRuta(){
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_RUT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Tag> arrayListData = new ArrayList<Tag>();
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setCodigo (cursor.getString(0));
                tag.setRonda(cursor.getString(1));
                arrayListData.add(tag);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

    public ArrayList<Tag> getTagsDeRutaPorRuta(String ruta){
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_RUT + " where " + REF_RUT + " = " + "'"+ruta+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Tag> arrayListData = new ArrayList<Tag>();
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setCodigo (cursor.getString(0));
                tag.setRonda(cursor.getString(1));
                arrayListData.add(tag);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

    public Tag getTagsDeRutaPorCodigo(String codigo){
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_RUT + " where " + COLUMN_ID_TAG + " = " + "'"+codigo+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Tag resultado = new Tag();
        if (cursor.moveToFirst()) {
            do {
                resultado.setCodigo(cursor.getString(0));
                resultado.setRonda(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return resultado;
    }

    public ArrayList<Tag> getTagsDeRondaPorRuta(String ruta){
        String selectQuery = "SELECT  * FROM " + TABLE_TAG_RND + " where " + REF_RND + " = " + "'"+ruta+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Tag> arrayListData = new ArrayList<Tag>();
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setCodigo (cursor.getString(0));
                tag.setHora(cursor.getString(1));
                tag.setRonda(cursor.getString(2));
                arrayListData.add(tag);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

    public boolean borrarRonda(String codigo) {
        SQLiteDatabase bd = this.getWritableDatabase();

        bd.delete(TABLE_TAG_RND, REF_RND + "='" + codigo + "'", null);

        return true;
    }
}

