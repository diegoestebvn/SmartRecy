package com.example.smartrecy;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "SignLog.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE users (email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("CREATE TABLE productos (nombre TEXT PRIMARY KEY, precio REAL, peso REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        try {
            MyDB.execSQL("DROP TABLE IF EXISTS users");
            MyDB.execSQL("DROP TABLE IF EXISTS productos");
            onCreate(MyDB);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error durante la actualización de la base de datos: " + e.getMessage(), e);
        }
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public boolean insertarProducto(String nombre, double precio, double peso) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("precio", precio);
        contentValues.put("peso", peso);
        long result = MyDatabase.insert("productos", null, contentValues);
        return result != -1;
    }

    public Cursor obtenerTodosLosProductos() {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM productos", null);
    }

    public boolean eliminarProducto(String nombre) {
        try (SQLiteDatabase MyDatabase = this.getWritableDatabase()) {
            int result = MyDatabase.delete("productos", "nombre=?", new String[]{nombre});
            if (result > 0) {
                Log.d("EliminarProducto", "Producto eliminado con éxito: " + nombre);
                return true;
            } else {
                Log.d("EliminarProducto", "No se encontró el producto para eliminar: " + nombre);
                return false;
            }
        } catch (Exception e) {
            Log.e("EliminarProducto", "Error al eliminar el producto: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean actualizarPrecioProducto(String nombre, double nuevoPrecio, double nuevoPeso) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("precio", nuevoPrecio);
            contentValues.put("peso", nuevoPeso);
            int result = MyDatabase.update("productos", contentValues, "nombre=?", new String[]{nombre});
            if (result > 0) {
                Log.d("ActualizarPrecio", "Precio y peso del producto actualizados: " + nombre +
                        " - Nuevo precio: " + nuevoPrecio + " - Nuevo peso: " + nuevoPeso);
                return true;
            } else {
                Log.d("ActualizarPrecio", "No se encontró el producto para actualizar: " + nombre);
                return false;
            }
        } catch (Exception e) {
            Log.e("ActualizarPrecio", "Error al actualizar el precio y peso: " + e.getMessage(), e);
            return false;
        } finally {
            MyDatabase.close();
        }
    }

    public ArrayList<String> obtenerTodosLosUsuarios() {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ArrayList<String> userList = new ArrayList<>();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users", null);
        while (cursor.moveToNext()) {
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            userList.add(email + " - Contraseña: " + password);
        }
        cursor.close();
        return userList;
    }
}
