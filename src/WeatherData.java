import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Nieto on 13/11/15.
 */
public class WeatherData {

    /* *************************************************************************************************************** */
    /* Variables
    /* *************************************************************************************************************** */


    private Double temperature, wind_velocity,humidity,rain,
                    barometer,rain_intensity, wind_direction_in_degrees,
                    wind_chill, heat_index,dew_point, solar_radiation;

    private Integer id,station;

    private GregorianCalendar date,date_of_adquisition;

    private String wind_direction,forecast;

    private Boolean isnullData;

    /* *************************************************************************************************************** */
    /* Constuctores
    /* *************************************************************************************************************** */

    //  ID,Temp,Wind_direction,Wind_velocity,Humidity,Rain,Barometer,Date,Time,Station,Rain_intensity,
    //  Wind_direction_degree,Wind_chill,Heat_index,Dew_point,Date_Adq,Time_Adq,Solar_radiation,forecast
    public WeatherData(String dataset)
    {
        isnullData = false;

        // Creamos un buffer para separar cada uno de los elemntos de la falla.
        String[] buffer = dataset.split(",");
        String[] day,time;

        id      = Integer.parseInt(buffer[0]);
        temperature = Double.parseDouble(buffer[1]);
        wind_direction = buffer[2];
        wind_velocity = Double.parseDouble(buffer[3]);
        humidity= Double.parseDouble(buffer[4]);
        rain    = Double.parseDouble(buffer[5]);
        barometer=Double.parseDouble(buffer[6]);

        // Year-Month-Day Hour:Minute:Second
        day = (buffer[7].split("-").length>1)? buffer[7].split("-"): buffer[7].split("/");
        time = buffer[8].split(":");

        date = new GregorianCalendar(Integer.parseInt(day[0]),Integer.parseInt(day[1])-1,Integer.parseInt(day[2]),
                Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));


        station=Integer.parseInt(buffer[9]);
        rain_intensity=Double.parseDouble(buffer[10]);
        wind_direction_in_degrees =Double.parseDouble(buffer[11]);
        wind_chill = Double.parseDouble(buffer[12]);
        heat_index = Double.parseDouble(buffer[13]);
        dew_point = Double.parseDouble(buffer[14]);

        day = (buffer[15].split("-").length>1)? buffer[15].split("-"): buffer[15].split("/");
        time = buffer[16].split(":");

        date_of_adquisition = new GregorianCalendar(Integer.parseInt(day[0]),Integer.parseInt(day[1])-1,Integer.parseInt(day[2]),
                Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));

        solar_radiation = Double.parseDouble(buffer[17]);
        forecast = buffer[18];
    }

    public WeatherData(Integer y,Integer mnth,Integer d, Integer h,Integer m,Integer sec, Integer st)
    {
        date = new GregorianCalendar(y,mnth,d,h,m,sec);
        station = st;

        isnullData = true;

    }


    /* *************************************************************************************************************** */
    /* Métodos to String
    /* *************************************************************************************************************** */

    @Override
    public String toString() {

        if(!isnullData) {
            return id.toString() + "," + temperature + "," + wind_direction + "," + wind_velocity + "," + humidity + "," + rain + "," + barometer + "," +
                    date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH)+1) + "-" + date.get(Calendar.DAY_OF_MONTH) + "," +
                    date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND) + "," +
                    station + "," + rain_intensity + "," + wind_direction_in_degrees + "," + wind_chill + "," + heat_index + "," + dew_point + "," +
                    date_of_adquisition.get(Calendar.YEAR) + "-" + date_of_adquisition.get(Calendar.MONTH)+1 + "-" + date_of_adquisition.get(Calendar.DAY_OF_MONTH) + "," +
                    date_of_adquisition.get(Calendar.HOUR_OF_DAY) + ":" + date_of_adquisition.get(Calendar.MINUTE) + ":" + date_of_adquisition.get(Calendar.SECOND) + "," +
                    solar_radiation + "," + forecast;
        }
        else{
            return "-" + "," + "-" + "," + "-" + "," + "-" + "," + "-" + "," + "-" + "," + "-" + "," +
                    date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH)+1) + "-" + date.get(Calendar.DAY_OF_MONTH) + "," +
                    date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND) + "," +
                    station + "," + "-" + "," + "-" + "," + "-" + "," + "-" + "," + "-" + "," +
                    date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH)+1) + "-" + date.get(Calendar.DAY_OF_MONTH) + "," +
                    date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND) + "," +
                    "-" + "," + "-";

        }
    }

    /* *************************************************************************************************************** */
    /* Métodos Get y Set
    /* *************************************************************************************************************** */

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWind_velocity() {
        return wind_velocity;
    }

    public void setWind_velocity(Double wind_velocity) {
        this.wind_velocity = wind_velocity;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getBarometer() {
        return barometer;
    }

    public void setBarometer(Double barometer) {
        this.barometer = barometer;
    }

    public Double getRain_intensity() {
        return rain_intensity;
    }

    public void setRain_intensity(Double rain_intensity) {
        this.rain_intensity = rain_intensity;
    }

    public Double getWind_direction_in_degrees() {
        return wind_direction_in_degrees;
    }

    public void setWind_direction_in_degrees(Double wind_direction_in_degrees) {
        this.wind_direction_in_degrees = wind_direction_in_degrees;
    }

    public Double getWind_chill() {
        return wind_chill;
    }

    public void setWind_chill(Double wind_chill) {
        this.wind_chill = wind_chill;
    }

    public Double getHeat_index() {
        return heat_index;
    }

    public void setHeat_index(Double heat_index) {
        this.heat_index = heat_index;
    }

    public Double getDew_point() {
        return dew_point;
    }

    public void setDew_point(Double dew_point) {
        this.dew_point = dew_point;
    }

    public Double getSolar_radiation() {
        return solar_radiation;
    }

    public void setSolar_radiation(Double solar_radiation) {
        this.solar_radiation = solar_radiation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public GregorianCalendar getDate_of_adquisition() {
        return date_of_adquisition;
    }

    public void setDate_of_adquisition(GregorianCalendar date_of_adquisition) {
        this.date_of_adquisition = date_of_adquisition;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public Boolean getIsnullData() {
        return isnullData;
    }

    public void setIsnullData(Boolean isnullData) {
        this.isnullData = isnullData;
    }
}

