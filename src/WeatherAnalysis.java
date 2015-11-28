/**
 *
 * Copyright (c) 2015  Nieto Fuentes Ricardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNES FOR A PARTICULAR PURPOSE AND NONINFRIGMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OHTERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING IN THE
 * SOFTWARE.
 *
 **/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nifr.CSV.CSV;

import java.util.*;

/**
 * Clase encargada de realizar el análisis de datos de clima tomado de estaciones de tiempo
 * de las cuales los sensores entregan un archivo CSV con una linea por medición en intervalos
 * de tiempo establecidos.
 *
 * ID,Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,...
 * Wind_direction_degree,Wind_chill,Heat_index,Dew_point,Date_Adq,Time_Adq,Solar_radiation,forecast"
 *
 * El programa ordena los archivos por tiempo y realiza un llenado de datos vacios en los intervalos
 * en los cuales los sensores no adquirieron datos por razones fuera del control del experimentador.
 *
 * Tambien realiza un promedio de las mediciones por hora, día, semana, mes y año.
 *
 *  @author Ricardo Nieto Fuentes
 *  @version 0.1
 *  @since Miércoles 25 de noviembre del 2015
 */
public class WeatherAnalysis extends Application {

    /* ************************************************************************************************************* */
    /* Variables */
    /* ************************************************************************************************************* */

    /* Contenedores */
    private Stage       mainStage;          // Ventana principal
    private Scene       mainScene;          // Escena principal
    private BorderPane  mainBorderPane;     // Layout principal
    private VBox        contentVBox;        // Layout que contendrá todos los botones verticalmente.
    private HBox        calcbuttonsHBox;    // Layout horizontal que tendra los botones encargados de calcular.

    /* Bottones */
    private Button      importButton, exportButton,orderButton, fillDataButton,averageButton;

    /* Barra de estado */
    private Label       statusLabel;

    /* Objetos del programa */

    // Contenedor de los datos de clima
    private ArrayList<WeatherData> weatherDataArrayList;
    // Contenedor de los datos procesados
    private ArrayList<WDAverageData> hourAverageList,dayAverageList,monthAverageList;


    /* ************************************************************************************************************* */
    /* Funciones */
    /* ************************************************************************************************************* */


    /** Función que inicia el programa **/
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Iniciamos los componentes
        this.initcomponents();

        // Título
        mainStage.setTitle("WeatherAnalysis");
        // Definimos las dimensiones de la ventana
        mainStage.setWidth(300);
        mainStage.setHeight(130);
        mainStage.setResizable(false);

        // Agregamos el contenido
        mainStage.setScene(mainScene);

        // Definimos la posición.
        mainStage.setX(((Screen.getPrimary().getVisualBounds().getWidth()-mainStage.getWidth()) / 2));
        mainStage.setY(((Screen.getPrimary().getVisualBounds().getHeight()-mainStage.getHeight()) / 2));

        // Mostramos la aplicación.
        mainStage.show();

    }

    /** Función principal que lanza la aplicación **/
    public static void main(String[] args) {
        launch(args);
    }


    /** Función encargada de iniciar los componentes del programa **/
    void initcomponents()
    {
        /*Contenedores ********************/

        // Ventana principal
        mainStage = new Stage();
        // Contenedor principal
        contentVBox = new VBox();
        // Contenedor de los botones de procesamiento
        calcbuttonsHBox = new HBox();
        //Layout Principal
        mainBorderPane = new BorderPane();
        // Escena principal
        mainScene = new Scene(mainBorderPane);

        /* Botones  I/O****************************/

        // Boton que se encarga de importar los datos alamacenados en un archivo CSV
        importButton = new Button("IMPORTAR");
        importButton.setMaxWidth(Double.MAX_VALUE);
        importButton.setOnAction(event ->{

            try {
                // Creamos el contenedor de datos.
                weatherDataArrayList = new ArrayList<>();
                // Abrimos el archivo CSV y para cada linea creamos un objeto que contiene el dato de clima
                CSV.openFile(this.mainStage).forEach(line-> {

                    try{
                        weatherDataArrayList.add(new WeatherData(line));
                    }
                    catch (Exception e)
                    {;}
                });

                // Desplegamos que se han importado los datos.
                statusLabel.setText("Se han importado los datos");
            } catch ( Exception e) {
                // En caso de que se encontrara algún error desplegamos el mensaje de error.
                statusLabel.setText("_(._.)_ No se pudo abrir el archivo");
                e.printStackTrace();
            }
        });


        // Botón encargado de guardar los datos procesados en un archivo CSV.
        exportButton = new Button("EXPORTAR");
        exportButton.setMaxWidth(Double.MAX_VALUE);
        exportButton.setOnAction(event1 -> {

            try
            {
                // String que contiene el header del arhcivo CSV para los datos
                String headerWD = "ID,Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_direction_degree,Wind_chill,Heat_index,Dew_point,Date_Adq,Time_Adq,Solar_radiation,forecast";
                // String que contiene el header del archivo CSV para los datos de promedios
                String headerAD = "Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_chill,Heat_index,Dew_point,Solar_radiation,IsValid";
                // Obtenemos el nombre del archivo y la ruta en la que lo guardaremos.
                String path = CSV.showSaveDialog(this.mainStage);

                // Si existen los datos de clima
                if(weatherDataArrayList!=null)
                    CSV.saveFile(CSV.convert2Stream(weatherDataArrayList,headerWD),path);

                // Si existen los promedios de hora.
                if (hourAverageList!= null)
                    CSV.saveFile(CSV.convert2Stream(hourAverageList,headerAD),path+"_hour_avg");

                // Si existen los promedios de día
                if(dayAverageList!=null)
                    CSV.saveFile(CSV.convert2Stream(dayAverageList,headerAD),path+"_day_avg");

                // Si existen los promedios de mes.
                if(monthAverageList!=null)
                    CSV.saveFile(CSV.convert2Stream(monthAverageList,headerAD),path+"_month_avg");

                // Desplegamos que se ha guardado con exito.
                statusLabel.setText("Guardado existoso :)");
            } catch (Exception e) {

                // Si ocurre algún error lo desplegamos.
                statusLabel.setText("Ha ocurrido un error al guardar _(._.)_");
                e.printStackTrace();
            }
        });

        /* Botónes de cálculos *************************/

        // Botón encargado de ordenar por fecha los datos.
        orderButton = new Button("ORDENAR");
        orderButton.setMaxWidth(Double.MAX_VALUE);
        orderButton.setOnAction(event -> {
            WDMethods.orderWDataByDate_Time_and_Station(weatherDataArrayList);
            statusLabel.setText("Los archivos estan ordenados");
        });


        // Botón encargado de encontrar los intervalos de tiempo vacios.
        fillDataButton = new Button("LLENAR");
        fillDataButton.setMaxWidth(Double.MAX_VALUE);
        fillDataButton.setOnAction(event -> {
            WDMethods.fillWData(weatherDataArrayList,Calendar.MINUTE,1);
            statusLabel.setText("Hemos encontrado los espacios vacios");
        });

        // Botón encargado de promediar los datos.
        averageButton = new Button("PROMEDIO");
        averageButton.setMaxWidth(Double.MAX_VALUE);
        averageButton.setOnAction(event -> {
            hourAverageList= WDMethods.averageWData(weatherDataArrayList,Calendar.HOUR,1,0.75,60d);
            dayAverageList = WDMethods.averageWAD(hourAverageList,Calendar.DAY_OF_MONTH,1,0.75,24d);
            monthAverageList = WDMethods.averageWAD(hourAverageList,Calendar.MONTH,1,0.75,30d);
            statusLabel.setText("Se ha promediado");
        });


        /* Etiquetas *********************************************************** */
        statusLabel = new Label("¿Qué vamos a procesar?");


        /* Agregamos los elementos a los contenedores.  ************************* */

        // Agregamos los botones de cálculo.
        calcbuttonsHBox.getChildren().addAll(orderButton,fillDataButton,averageButton);
        HBox.setHgrow(orderButton,Priority.ALWAYS);
        HBox.setHgrow(fillDataButton,Priority.ALWAYS);
        HBox.setHgrow(averageButton,Priority.ALWAYS);
        HBox.setHgrow(calcbuttonsHBox, Priority.ALWAYS);
        // Agregamos los botones de I/O
        contentVBox.getChildren().addAll(importButton,calcbuttonsHBox,exportButton,statusLabel);

        // Agregamos los contenedores.
        mainBorderPane.setCenter(contentVBox);
    }

}
