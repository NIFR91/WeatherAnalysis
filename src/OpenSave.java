import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/** Esta clase se encarga de guardar y abrir los archivos de TruDisp*/
public class OpenSave {

    /** Variables */
    private FileChooser fileChooser;    // Objeto encargado de abrir una venta de selección de archivos
    private Stage stage;          // Ventana principal de la cual se despliega el filsechooser.

    /** Constructor */
    public OpenSave(Stage st)
    {
        fileChooser = new FileChooser();
        stage = st;
    }


    /** Función encargada de guardar los archivos. */
    public void Save(ArrayList<WeatherData> data ) throws Exception
    {
        // titulo de la ventana emergente
        fileChooser.setTitle("Save Session");


        // Archivo al que se escribe.
        FileWriter file = null;

            // Obtenemos el archivo al que se escribe a partir de la seleción del filechooser.
            file = new FileWriter(fileChooser.showSaveDialog(stage)+".csv");

            // Objeto para escribir el archivo.

            PrintWriter pw = new PrintWriter(file);

            pw.println("ID,Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_direction_degree,Wind_chill,Heat_index,Dew_point,Date_Adq,Time_Adq,Solar_radiation,forecast");

            // Para cada objeto de la lista se escribe la información.
            data.stream().forEach(dataobject -> {
                pw.println(dataobject);
            });

            // Cerramos el archivo
            file.close();

    }

    /** Función encargada de guardar los archivos. */
    public void Save(ArrayList<WeatherData> data, ArrayList<WDAverageData> hourAverage, ArrayList<WDAverageData> dayAverage, ArrayList<WDAverageData> monthAverage) throws Exception
    {
        // titulo de la ventana emergente
        fileChooser.setTitle("Save Session");

        File path=fileChooser.showSaveDialog(stage);
        // Archivo al que se escribe.
        FileWriter file = null;


        // Obtenemos el archivo al que se escribe a partir de la seleción del filechooser.
        file = new FileWriter(path+".csv");

        // Objeto para escribir el archivo.

        PrintWriter pw = new PrintWriter(file);

        pw.println("ID,Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_direction_degree,Wind_chill,Heat_index,Dew_point,Date_Adq,Time_Adq,Solar_radiation,forecast");

        // Para cada objeto de la lista se escribe la información.
        final PrintWriter finalPw = pw;
        data.stream().forEach(dataobject -> {
            finalPw.println(dataobject);
        });

        // Cerramos el archivo
        file.close();

        file = new FileWriter(path+"_H_Average.csv");

        pw = new PrintWriter(file);
        pw.println("Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_chill,Heat_index,Dew_point,Solar_radiation,IsValid");

        // Para cada objeto de la lista se escribe la información.
        final PrintWriter finalPw1 = pw;
        hourAverage.stream().forEach(dataobject -> {
            finalPw1.println(dataobject);
        });

        // Cerramos el archivo
        file.close();


        file = new FileWriter(path+"_D_Average.csv");

        pw = new PrintWriter(file);
        pw.println("Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_chill,Heat_index,Dew_point,Solar_radiation,IsValid");

        // Para cada objeto de la lista se escribe la información.
        final PrintWriter finalPw2 = pw;
        dayAverage.stream().forEach(dataobject -> {
            finalPw2.println(dataobject);
        });

        // Cerramos el archivo
        file.close();


        file = new FileWriter(path+"_M_Average.csv");

        pw = new PrintWriter(file);
        pw.println("Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,Wind_chill,Heat_index,Dew_point,Solar_radiation,IsValid");

        // Para cada objeto de la lista se escribe la información.
        final PrintWriter finalPw3 = pw;
        monthAverage.stream().forEach(dataobject -> {
            finalPw3.println(dataobject);
        });

        // Cerramos el archivo
        file.close();


    }


    /** Función encargada de abrir los archivos. */
    public ArrayList<WeatherData> Open() throws Exception
    {
        // Titulo de la ventana
        fileChooser.setTitle("Open Data");

        // Lista que contrendrá los distitos objetos de la sesión a cargar.
        ArrayList<WeatherData> arraylistData = new ArrayList<>();

        // Objeto de lectura
        FileReader filereader= null;

            // Abrimos el objeto que se indica mediante el filechooser.
            File file = new File (fileChooser.showOpenDialog(stage).getAbsolutePath());

            // Creamos el objeto para leer.
            filereader = new FileReader(file); // Create a FileReader
            BufferedReader bufferedreader = new BufferedReader(filereader); // Create the BufferedReader

            // Leemos linea por linea y creamos  el objeto.
            String line;
            while((line=bufferedreader.readLine())!=null)
            {
                try {
                    arraylistData.add(new WeatherData(line));
                }
                catch (Exception e)
                {
                    ;
                }

            }

        // REgresamos la lista que contiene todos los objetos
        return arraylistData;
    }
}
