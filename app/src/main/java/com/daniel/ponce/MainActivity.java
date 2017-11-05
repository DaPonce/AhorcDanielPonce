package com.daniel.ponce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * El objeto de la clase GestoraInformacion con el que trabaja
     * este JFrame
     */
    private GestoraInformacion gestora;
    /**
     * este objeto es donde se visualizará la imagen
     * del ahorcado correspondiente a los fallos
     */
    private ImageView imagen;
    /**
     * La palabra que tiene que adivinar el usuario
     */
    private String palabraSecreta;
    /**
     * Un array de String que representa las letras del alfabeto
     */
    private String[] letras;
    /**
     * Un array de objetos JButton que se visualizará en el objeto
     * Ventana para que el usuario pueda seleccionar letras del
     * alfabeto
     */
    private Button[] botonesLetras;
    /**
     * Un array de objetos JButton que se visualizará en el objeto
     * Ventana para indicar al usuario cuántas letras tiene la palabra
     * a adivinar y mostrar las letras que va acertando
     */
    private Button[] botonesLetrasPalabraSecreta;
    /**
     * El número de letras de la palabra secreta que el usuario lleva
     * acertadas
     */
    private int aciertos = 0;
    /**
     * El número de letras falladas
     */
    int fallos = 0;
    private LinearLayout jpLaPalabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        gestora = new GestoraInformacion(this);
        generaBotonesYpalabra();
    }

    /**
     * Este método debe empezar estableciendo como icono de jLabel1 el
     * archivo /imagenes/vacio.GIF; a continuación se generará una
     * nueva palabra secreta para dar valor al campo palabraSecreta;
     * se habilitarán los JButton de las letras, se inicializarán a
     * los valores de los campos aciertos y fallos y se crearán los
     * JButton a los que debe referirse el array letrasPalabrasSecreta
     * (deben estar deshabilitados y el texto inicial sobre todos
     * ellos será "_") ¡No olvidar que cuando se añaden o eliminan
     * componentes en un contenedor que se está visualizando hay que
     * invocar al método revalidate() para que se visualicen los
     * componentes actualizados! ¡No olvidar que cuando cambia alguna
     * propiedad que afecta al aspecto de un componente incluido en un
     * contenedor que se está visualizando se debe invocar al método
     * repaint() para que se actualice la vista que del mismo ofrece
     * el contenedor!
     */
    public void mNuevaPalabra() {
        imagen = (ImageView) findViewById(R.id.imagen);
        this.imagen.setImageDrawable(gestora.imagenInicial());
        this.palabraSecreta = gestora.generarNuevaPalabraSecreta();
        if (this.palabraSecreta != null) {
            this.habilitarBotonesLetra();
            aciertos = 0;
            fallos = 0;
            this.generarBotonesLetrasPalabraSecreta();

        } else {
            Toast toast = Toast.makeText(this, this.getResources().getString(R.string.no_palabras), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void generarBotonesLetrasPalabraSecreta() {
        jpLaPalabra = (LinearLayout) this.findViewById(R.id.jpLaPalabra);
        jpLaPalabra.removeAllViewsInLayout();
        this.botonesLetrasPalabraSecreta = new Button[palabraSecreta.length()];
        for (int x = 0; x < palabraSecreta.length(); x++) {
            botonesLetrasPalabraSecreta[x] = new Button(this);
            botonesLetrasPalabraSecreta[x].setText("_");
            botonesLetrasPalabraSecreta[x].setEnabled(false);
            jpLaPalabra.addView(botonesLetrasPalabraSecreta[x]);
        }

    }

    /**
     * habilita todos los JButton de las letras
     */
    private void habilitarBotonesLetra() {
        for (Button boton : this.botonesLetras) {
            if (!boton.isEnabled()) {
                boton.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        Button bGenerar = (Button) this.findViewById(R.id.bGenerar);
        if (arg0 == bGenerar) {
            this.mNuevaPalabra();

        } else {
            this.mComprobar((Button) arg0);

        }

    }

    /**
     * -da valor adecuado a letras -invoca a generarBotones() para
     * generar los Jbutton -define a esta ventana como el objeto
     * escuchador de eventos de acción para los botones generados y
     * también para el JButton bGenerar -invoca a mNuevaPlabra() para
     * generar la plabra a adivinar
     */
    private void generaBotonesYpalabra() {
        this.letras = this.gestora.getLetras();
        this.generarBotones();
        //bGenerar.setaddActionListener(this);
        for (Button unBotonLetra : this.botonesLetras) {
            unBotonLetra.setOnClickListener(this);
        }
        this.mNuevaPalabra();
    }

    /**
     * Genera los botones JButton de las letras, almacena sus
     * referencias en botonesLetras y los añaden al JPanel jpTeclas
     * Para que los botones se vean en más de una fila el Layout puede
     * ser FlowLayout (Diseño de flujo);
     */
    private void generarBotones() {
        GridLayout jpTeclas = (GridLayout) findViewById(R.id.jpTeclas);
        jpTeclas.removeAllViewsInLayout();
        this.botonesLetras = new Button[this.letras.length];
        for (int x = 0; x < letras.length; x++) {
            this.botonesLetras[x] = new Button(this);
            this.botonesLetras[x].setText(this.letras[x]);
            jpTeclas.addView(botonesLetras[x]);
        }
    }

    /**
     * deshabilita el Jbutton boton; comprueba si la letra que se
     * visualiza sobre el JButton boton se corresponde con alguna de
     * las letras de palabraSecreta; - si coincide con alguna cambia
     * el valor del texto de los JButton de letrasPalabraSecreta que
     * correspondan, - también actualiza el valor del campo aciertos y
     * comprueba si ha acertado la palabra completa, en cuyo caso se
     * invocará al método mNuevaPalabra() para generar una nueva
     * palabra después de informar al usuario ; si la letra del
     * JButton que recibe el método no se corresponde con ninguna de
     * las letras de palabraSecreta se actualizará el valor del campo
     * fallos, cargará en jLabel1 la imagen que corresponda, y se
     * comprobará si todavía quedan más oportunidades para adivinar la
     * palabraSecreta, de no ser así, se invocará al método
     * mNuevaPalabra() después de informar al usuario de que ha
     * perdido
     *
     * @param
     */
    void mComprobar(Button boton) {
        boton.setEnabled(false);
        char letraElegida = boton.getText().charAt(0);
        Integer[] posicionesConLetraElegida = gestora.posicionesLetra(letraElegida);
        if (posicionesConLetraElegida != null) {
            this.visualizarLetraAcertada(posicionesConLetraElegida, letraElegida);
            if (aciertos == palabraSecreta.length()) {
                Toast toast = Toast.makeText(this, this.getResources().getString(R.string.acertado) + " " + palabraSecreta, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                this.mNuevaPalabra();
            }
        } else {
            fallos++;
            gestionarFallo();
        }
    }

    private void gestionarFallo() {
        if (fallos <= gestora.maximoFallos()) {
            imagen.setImageDrawable(gestora.imagenFallos(fallos));
            if (fallos == gestora.maximoFallos()) {
                Toast toast = Toast.makeText(this, this.getResources().getString(R.string.fallado) + " " + palabraSecreta, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                mNuevaPalabra();
            }
        }
    }

    private void visualizarLetraAcertada(Integer[] posicionesConLetraElegida, char letraElegida) {
        for (Integer unaPosicion : posicionesConLetraElegida) {
            botonesLetrasPalabraSecreta[unaPosicion].setText(Character.toString(letraElegida));
            aciertos++;
        }

    }

    public void generar(View view) {
        mNuevaPalabra();
    }
}
