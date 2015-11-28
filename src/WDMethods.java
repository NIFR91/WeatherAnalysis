
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

/**
 * Esta clase se encarga de manejar los métodos de la aplicación.
 */
public class WDMethods {


    /**
     * Esta función publica estática, se encarga de ordenar los datos en el ArrayList "wdlist" por
     * fecha y por estación, empleando la función "Collections.sort()" con un
     * "Comparator" anónimo.
     */
    public static void orderWDataByDate_Time_and_Station(ArrayList<WeatherData> wdlist)
    {
        // Ordenamos por fecha y estación en orden ascendente
        Collections.sort(wdlist, (o1, o2) -> {

            if (o1 == null || o2 == null)
                throw new NullPointerException();
            else if (o1.getDate().getTimeInMillis()!=o2.getDate().getTimeInMillis())
                return o1.getDate().compareTo(o2.getDate());
            else
                return o1.getStation().compareTo(o2.getStation());
        });
    }

    /**
     * Esta función publica estática se encarga de llenar los espacios de tiempo de la lista "wdlist"
     * recibe como parámetros:
     *
     * wdlist: la lista de datos de clima
     * TIMESTEP: el intervalo de tiempo
     * step: el salto en el tiempo.
     *
     * Para ello emplea un "switch" para seleccionar el intervalo que se requiere.
     */
    public static void fillWData(ArrayList<WeatherData> wdlist,Integer TIMESTEP,Integer step)
    {
        // Verificamos si los parámetros son válidos.
        if (wdlist!=null && TIMESTEP!=null && step!=null)
        {
            // Variables
            GregorianCalendar prevday, nextday, today;
            WeatherData wd;
            ArrayList<WeatherData> newwdlist = new ArrayList<>();
            prevday = new GregorianCalendar();
            nextday = new GregorianCalendar();

            // Agregamos el elemento
            wd = wdlist.get(0);

            // Seleccionamos el caso correspondiente a la escala de tiempo
            switch (TIMESTEP)
            {
                case Calendar.SECOND: {
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),wd.getDate().get(Calendar.SECOND));
                    break;
                }
                case Calendar.MINUTE:{
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),0);
                    break;
                }
                case Calendar.HOUR:{
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    break;
                }
                case Calendar.DAY_OF_MONTH:{
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),0,0,0);
                    break;
                }
                case Calendar.MONTH:{
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),0,0,0,0);
                    break;
                }
                case Calendar.YEAR:{
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),0,0,0,0,0);
                    break;
                }
                default:
                {
                    today = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),wd.getDate().get(Calendar.SECOND));
                    break;
                }
            }

            today.add(TIMESTEP,1);

            /*
            Para cada elemento de la lista tomamos el día previo, el dia siguiente y vamos aumentando el dia en el
             paso seleccionado.
             */
            for (int i=0; i<wdlist.size()-1;i++)
            {
                // Tomamos el día previo.
                wd = wdlist.get(i);
                prevday.setTimeInMillis(wd.getDate().getTimeInMillis());
                // Agregamos el día.
                newwdlist.add(wd);

                // Toamos el siguiente día de la lista.
                wd = wdlist.get(i+1);
                nextday.setTimeInMillis(wd.getDate().getTimeInMillis());

                // Mientras exista un intervalo de tiempo en el cual no existan datos vamos a generar un dato vacio
                if(today.before(nextday)&&today.after(prevday))
                {
                    // Mientras sea antes al día siguiente de la lista.
                    while (today.before(nextday))
                    {
                        switch (TIMESTEP)
                        {
                            case Calendar.SECOND: {
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),
                                        today.get(Calendar.HOUR_OF_DAY),today.get(Calendar.MINUTE),today.get(Calendar.SECOND),wdlist.get(i).getStation()));
                                break;
                            }
                            case Calendar.MINUTE:{
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),
                                        today.get(Calendar.HOUR_OF_DAY),today.get(Calendar.MINUTE),0,wdlist.get(i).getStation()));
                                break;
                            }
                            case Calendar.HOUR:{
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),
                                        today.get(Calendar.HOUR_OF_DAY),0,0,wdlist.get(i).getStation()));
                                break;
                            }
                            case Calendar.DAY_OF_MONTH:{
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),
                                        0,0,0,wdlist.get(i).getStation()));
                                break;
                            }
                            case Calendar.MONTH:{
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),0,0,0,0,wdlist.get(i).getStation()));
                                break;
                            }
                            case Calendar.YEAR:{
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),0,0,0,0,0,wdlist.get(i).getStation()));
                                break;
                            }
                            default:
                            {
                                newwdlist.add(new WeatherData(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),
                                        today.get(Calendar.HOUR_OF_DAY),today.get(Calendar.MINUTE),today.get(Calendar.SECOND),wdlist.get(i).getStation()));
                                break;
                            }
                        }
                        today.add(TIMESTEP,step);
                    }
                }
                today.add(TIMESTEP,step);
            }
            // Agregamos el último elemento de la lista.
            newwdlist.add(wdlist.get(wdlist.size()-1));
            // Limpiamos la lista
            wdlist.clear();
            // Agregamos los elementos de la nueva lista a la original.
            wdlist.addAll(newwdlist);
        }
        else
            throw new NullPointerException();
    }


    /**
     * Esta función publica estática se encarga de promediar los datos obtenidos en minutos si los datos estan en segundos,
     * en horas si los datos estan en minutos, en dias si los datos estan en horas, etc.
     *
     * Recibe como argumentos:
     * wdlist: que es la lista de datos de clima.
     * TIMESTEP: es el intervalo de tiempo.
     * step: es el salto en el tiempo.
     * tolerance: la tolerancia en la cantidad de datos validos.
     * numberofdata: es la cantidad de datos que debe tener.
     *
     * Regresa:
     *
     * ArrayLis<WDAverageData> wdavdlist : el cual es una lista de los objetos de promedio.
     *
     */
    public static ArrayList<WDAverageData> averageWData(ArrayList<WeatherData> wdlist,Integer TIMESTEP, Integer step,Double tolerance,Double numberofdata)
    {
        // Revisamos si los datos introducidos son validos.
        if(wdlist!=null&&TIMESTEP!=null&&step!=null&&tolerance!=null&&numberofdata!=null)
        {
            // Variables
            GregorianCalendar today,prevday,nextday,avday;
            WeatherData wd;
            WDAverageData wdavd;
            Integer cnt=0;
            today = new GregorianCalendar();
            ArrayList<WDAverageData> wdavdlist = new ArrayList<>();
            Double temperature=0d,wind_direction_in_degrees=0d,wind_velocity=0d,humidity=0d,rain=0d,barometer=0d,
                    rain_intensity=0d, wind_chill=0d,heat_index=0d,dew_point=0d,solar_radiation=0d;

            // Obtenemos el primer dato de clima
            wd = wdlist.get(0);

            // Risamos cual es el intervalo de tiempo manejado y creamos los dias previo, siguiente y promedio correspondiente
            switch (TIMESTEP)
            {
                case Calendar.MINUTE: {
                    prevday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),wd.getDate().get(Calendar.SECOND));
                    nextday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),wd.getDate().get(Calendar.SECOND));
                    avday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),0);
                    break;
                }
                case Calendar.HOUR: {
                    prevday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),0);
                    nextday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),wd.getDate().get(Calendar.MINUTE),0);
                    avday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    break;
                }
                case Calendar.DAY_OF_MONTH:
                {
                    prevday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    nextday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            wd.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    avday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    break;
                }
                case Calendar.MONTH: {
                    prevday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    nextday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),wd.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    avday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),0,0,0,0);
                    break;
                }
                default: {
                    prevday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),0,0,0,0);
                    nextday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),wd.getDate().get(Calendar.MONTH),0,0,0,0);
                    avday = new GregorianCalendar(wd.getDate().get(Calendar.YEAR),0,0,0,0,0);
                    break;
                }
            }

            // Dia previo y siguiente.
            prevday.add(TIMESTEP,-step);
            nextday.add(TIMESTEP,step);

            // Para cada elemento de la lista de datos de clima
            for (int i=0; i<wdlist.size(); i++)
            {
                // Obtenemos el dato correspondiente al día.
                wd = wdlist.get(i);
                today.setTimeInMillis(wd.getDate().getTimeInMillis());

                // Si el día se encuentra entre el anterior y el siguiente o es el siguiente
                if((today.before(nextday)&&today.after(prevday))||today.equals(nextday))
                {
                    // Revisamos si el dato es valido promediamos.
                    if (!wd.getIsnullData())
                    {
                        temperature                 += wd.getTemperature();
                        wind_direction_in_degrees   += wd.getWind_direction_in_degrees();
                        wind_velocity               += wd.getWind_velocity();
                        humidity                    += wd.getHumidity();
                        rain                        += wd.getRain();
                        barometer                   += wd.getBarometer();
                        rain_intensity              += wd.getRain_intensity();
                        wind_chill                  += wd.getWind_chill();
                        heat_index                  += wd.getHeat_index();
                        dew_point                   += wd.getDew_point();
                        solar_radiation             += wd.getSolar_radiation();

                        // Aumentamos el contador.
                        cnt                         +=1;
                    }
                }
                else
                {
                    // Creamos el objeto de calendario para el dato de promedio.
                    GregorianCalendar temporalday= new GregorianCalendar();
                    temporalday.setTimeInMillis(avday.getTimeInMillis());

                    // Creamos el dato de promedio con los datos
                    wdavd = new WDAverageData(temporalday,temperature/((double)cnt),wind_direction_in_degrees/((double)cnt),
                            wind_velocity/((double)cnt),humidity/((double)cnt),rain/((double)cnt),rain_intensity/((double)cnt)
                            ,barometer/((double)cnt),wind_chill/((double)cnt), heat_index/((double)cnt),dew_point/((double)cnt),
                            solar_radiation/((double)cnt),wdlist.get(i-1).getStation(),
                            (((double)cnt/numberofdata) >=tolerance)? true: false);
                    // Agregamos el objeto de promedio a la lista de promedio
                    wdavdlist.add(wdavd);

                    // Reiniciamos los valores.
                    if(!wd.getIsnullData())
                    {
                        temperature                 = wd.getTemperature();
                        wind_direction_in_degrees   = wd.getWind_direction_in_degrees();
                        wind_velocity               = wd.getWind_velocity();
                        humidity                    = wd.getHumidity();
                        rain                        = wd.getRain();
                        barometer                   = wd.getBarometer();
                        rain_intensity              = wd.getRain_intensity();
                        wind_chill                  = wd.getWind_chill();
                        heat_index                  = wd.getHeat_index();
                        dew_point                   = wd.getDew_point();
                        solar_radiation             = wd.getSolar_radiation();
                    }
                    else {
                        temperature=0d;wind_direction_in_degrees=0d;wind_velocity=0d;humidity=0d;rain=0d;barometer=0d;
                        rain_intensity=0d; wind_chill=0d;heat_index=0d;dew_point=0d;solar_radiation=0d;
                    }

                    cnt=1;

                    // Nos movemos un día en el tiempo.
                    prevday.add(TIMESTEP,step);
                    nextday.add(TIMESTEP,step);
                    avday.add(TIMESTEP,step);
                }
            }
            // Capturamos el último día.
            GregorianCalendar temporalday= new GregorianCalendar();
            temporalday.setTimeInMillis(avday.getTimeInMillis());
            wdavd = new WDAverageData(temporalday,temperature,wind_direction_in_degrees,
                    wind_velocity,humidity,rain,rain_intensity,barometer,wind_chill,
                    heat_index,dew_point,solar_radiation,wdlist.get(wdavdlist.size()-1).getStation(),
                    (((double)cnt/numberofdata) >=tolerance)? true: false);
            wdavdlist.add(wdavd);

            // Regresamos la lista generada.
            return wdavdlist;
        }
        else    // Si existe algún error mandamos una excepción
            throw new NullPointerException();
    }


    /**
     * Esta función publica estática se encarga de promediar los datos promediados en minutos si los datos estan en segundos,
     * en horas si los datos estan en minutos, en dias si los datos estan en horas, etc.
     *
     * Recibe como argumentos:
     * wdlist1: que es la lista de datos de clima.
     * TIMESTEP: es el intervalo de tiempo.
     * step: es el salto en el tiempo.
     * tolerance: la tolerancia en la cantidad de datos validos.
     * numberofdata: es la cantidad de datos que debe tener.
     *
     * Regresa:
     *
     * ArrayLis<WDAverageData> wdavdlist2 : el cual es una lista de los objetos de promedio.
     */
    public static ArrayList<WDAverageData> averageWAD(ArrayList<WDAverageData> wdavdlist1,Integer TIMESTEP, Integer step,Double tolerance,Double numberofdata)
    {
        if(wdavdlist1!=null&&TIMESTEP!=null&&step!=null&&tolerance!=null&&numberofdata!=null)
        {
            // Variables
            GregorianCalendar today,prevday,nextday,avday;
            WDAverageData wdavd1,wdavd2;
            Integer cnt=0;

            today = new GregorianCalendar();
            ArrayList<WDAverageData> wdavdlist2 = new ArrayList<>();
            Double temperature=0d,wind_direction_in_degrees=0d,wind_velocity=0d,humidity=0d,rain=0d,barometer=0d,
                    rain_intensity=0d, wind_chill=0d,heat_index=0d,dew_point=0d,solar_radiation=0d;

            // Obtenemos el primer dato de clima
            wdavd1 = wdavdlist1.get(0);

            // Risamos cual es el intervalo de tiempo manejado y creamos los dias previo, siguiente y promedio correspondiente
            switch (TIMESTEP)
            {
                case Calendar.MINUTE: {
                    prevday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),wdavd1.getDate().get(Calendar.MINUTE),wdavd1.getDate().get(Calendar.SECOND));
                    nextday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),wdavd1.getDate().get(Calendar.MINUTE),wdavd1.getDate().get(Calendar.SECOND));
                    avday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),wdavd1.getDate().get(Calendar.MINUTE),0);
                    break;
                }
                case Calendar.HOUR: {
                    prevday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),wdavd1.getDate().get(Calendar.MINUTE),0);
                    nextday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),wdavd1.getDate().get(Calendar.MINUTE),0);
                    avday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    break;
                }
                case Calendar.DAY_OF_MONTH:
                {
                    prevday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    nextday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            wdavd1.getDate().get(Calendar.HOUR_OF_DAY),0,0);
                    avday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    break;
                }
                case Calendar.MONTH: {
                    prevday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    nextday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),wdavd1.getDate().get(Calendar.DAY_OF_MONTH),
                            0,0,0);
                    avday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),0,0,0,0);
                    break;
                }
                default: {
                    prevday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),0,0,0,0);
                    nextday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),wdavd1.getDate().get(Calendar.MONTH),0,0,0,0);
                    avday = new GregorianCalendar(wdavd1.getDate().get(Calendar.YEAR),0,0,0,0,0);
                    break;
                }
            }

            // Dia previo y siguiente.
            prevday.add(TIMESTEP,-step);
            nextday.add(TIMESTEP,step);

            // Para cada elemento de la lista de datos de clima
            for (int i=0; i<wdavdlist1.size(); i++)
            {
                // Obtenemos el dato correspondiente al día.
                wdavd1 = wdavdlist1.get(i);
                today.setTimeInMillis(wdavd1.getDate().getTimeInMillis());

                // Si el día se encuentra entre el anterior y el siguiente o es el siguiente
                if((today.before(nextday)&&today.after(prevday))||today.equals(nextday))
                {
                    // Revisamos si el dato es valido promediamos.
                    if (!wdavd1.getValid())
                    {
                        temperature                 += wdavd1.getTemperature();
                        wind_direction_in_degrees   += wdavd1.getWind_direction_in_degrees();
                        wind_velocity               += wdavd1.getWind_velocity();
                        humidity                    += wdavd1.getHumidity();
                        rain                        += wdavd1.getRain();
                        barometer                   += wdavd1.getBarometer();
                        rain_intensity              += wdavd1.getRain_intensity();
                        wind_chill                  += wdavd1.getWind_chill();
                        heat_index                  += wdavd1.getHeat_index();
                        dew_point                   += wdavd1.getDew_point();
                        solar_radiation             += wdavd1.getSolar_radiation();

                        // Aumentamos el contador.
                        cnt                         +=1;
                    }
                }
                else
                {
                    // Creamos el objeto de calendario para el dato de promedio.
                    GregorianCalendar temporalday= new GregorianCalendar();
                    temporalday.setTimeInMillis(avday.getTimeInMillis());

                    // Creamos el dato de promedio con los datos
                    wdavd2 = new WDAverageData(temporalday,temperature/((double)cnt),wind_direction_in_degrees/((double)cnt),
                            wind_velocity/((double)cnt),humidity/((double)cnt),rain/((double)cnt),rain_intensity/((double)cnt)
                            ,barometer/((double)cnt),wind_chill/((double)cnt), heat_index/((double)cnt),dew_point/((double)cnt),
                            solar_radiation/((double)cnt),wdavdlist1.get(i-1).getStation(),
                            (((double)cnt/numberofdata) >=tolerance)? true: false);
                    // Agregamos el objeto de promedio a la lista de promedio
                    wdavdlist2.add(wdavd2);

                    // Reiniciamos los valores.
                    if(!wdavd1.getValid())
                    {
                        temperature                 = wdavd1.getTemperature();
                        wind_direction_in_degrees   = wdavd1.getWind_direction_in_degrees();
                        wind_velocity               = wdavd1.getWind_velocity();
                        humidity                    = wdavd1.getHumidity();
                        rain                        = wdavd1.getRain();
                        barometer                   = wdavd1.getBarometer();
                        rain_intensity              = wdavd1.getRain_intensity();
                        wind_chill                  = wdavd1.getWind_chill();
                        heat_index                  = wdavd1.getHeat_index();
                        dew_point                   = wdavd1.getDew_point();
                        solar_radiation             = wdavd1.getSolar_radiation();
                    }
                    else {
                        temperature=0d;wind_direction_in_degrees=0d;wind_velocity=0d;humidity=0d;rain=0d;barometer=0d;
                        rain_intensity=0d; wind_chill=0d;heat_index=0d;dew_point=0d;solar_radiation=0d;
                    }

                    cnt=1;

                    // Nos movemos un día en el tiempo.
                    prevday.add(TIMESTEP,step);
                    nextday.add(TIMESTEP,step);
                    avday.add(TIMESTEP,step);
                }
            }
            // Capturamos el último día.
            GregorianCalendar temporalday= new GregorianCalendar();
            temporalday.setTimeInMillis(avday.getTimeInMillis());
            wdavd2 = new WDAverageData(temporalday,temperature,wind_direction_in_degrees,
                    wind_velocity,humidity,rain,rain_intensity,barometer,wind_chill,
                    heat_index,dew_point,solar_radiation,wdavdlist1.get(wdavdlist1.size()-1).getStation(),
                    (((double)cnt/numberofdata) >=tolerance)? true: false);
            wdavdlist2.add(wdavd2);

            // Regresamos la lista generada.
            return wdavdlist2;
        }
        else
            throw new NullPointerException();
    }
}
