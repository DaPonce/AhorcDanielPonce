package com.daniel.ponce;

/**
 * Created by DamLocal on 05/10/2017.
 */

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;


import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author merche
 */
public class GestoraInformacion {

    private static int recursoLetras = R.array.idioma;
    private static int recursoPalabras = R.array.idioma_palabras;

    //Referencia a la actividad principal
    private MainActivity contexto;
    private static TypedArray imagenes;

    //La palabra a adivinar
    private String palabraSecreta;

    //Letras del alfabeto del idioma en el que estemos usando la aplicación
    private String[] letras;

    //Constructor de la clase
    public GestoraInformacion(MainActivity c){
        contexto=c;
        this.letras=c.getResources().getStringArray(R.array.idioma);
        imagenes=contexto.getResources().
                obtainTypedArray(R.array.imagenes);

    }

    public String[] getLetras() {
        return letras;
    }

    /**
     * Selecciona la primera de las palabras del ArrayList (después de
     * invocar al método estático shuffle(List) de la clase
     * Collections) para dar valor al campo palabraSecreta
     *
     * @return devuelve palabraSecreta
     */
    public String generarNuevaPalabraSecreta() {
       /* ArrayList<String> palabras = this.idioma.devuelvePalabras();
        Collections.shuffle(palabras);
        this.palabraSecreta = palabras.get(0);
        return this.palabraSecreta;*/
        ArrayList<String> palabras = new ArrayList<String>();

//contexto.getResources().getStringArray(this.recursoPalabras) es el objeto String[] que representa al recurso array de string this.recursoPalabras
        Collections.addAll(palabras,contexto.getResources().getStringArray(this.recursoPalabras));
        Collections.shuffle(palabras);
        this.palabraSecreta = palabras.get(0);
        return this.palabraSecreta;

    }
    /**
     * devuelve las posiciones en la palabra secreta de la letra c
     *
     * @param c la letra
     * @return el array de posiciones donde se encuentra c en la
     * palabra secreta o null si la letra c no aparece en  la palabra
     * secreta
     */
    public Integer[] posicionesLetra(char c) {
        if (this.palabraSecreta.indexOf(c) != -1) {
            ArrayList<Integer> intermedio = new ArrayList<>();
            for (int x = 0; x < palabraSecreta.length(); x++) {
                if (palabraSecreta.charAt(x) == c) {
                    intermedio.add(x);
                }
            }
            return intermedio.toArray(new Integer[intermedio.size()]);
        } else {
            return null;
        }
    }

    /**
     *
     * @return el path relativo al archivo vacio.GIF
     */
    public Drawable imagenInicial() {
        //return new Drawable(getClass().getResource("/imagenes/vacio.GIF"));
        //contexto.getResources().getDrawable(R.drawable.vacio) es el objeto Drawable que representa al recurso drawable R.drawable.vacio

        return contexto.getResources().getDrawable(R.drawable.vacio);
    }

    /**
     *
     * @param fallos
     * @return el path relativo al archivo de imagen que corresponde
     * al número de fallos fallos
     */
    public Drawable imagenFallos(int fallos) {
        //return new javax.swing.Drawable(getClass().getResource("/imagenes/" + "ahorcado".substring(0, fallos) + ".GIF"));
        /*un objeto TypedArray representa un array de recursos. El programador debe saber el tipo de recurso que se almacna en cada posición del array. En nuestro caso siempre estamos almacenando recursos drawable. Así que una vez definido el TypedArray se puede invocar a su método getDrawable(int) para obtener un objeto que represente el recurso drawable almacenado en la posición que recibe como parámetro el método*/

        return imagenes.getDrawable(fallos);
    }

    /**
     *
     * @return número máximo de fallos (el nº de letras de la palabra
     * ahorcado)
     */
    public int maximoFallos() {
        return "ahorcado".length();
    }
}
