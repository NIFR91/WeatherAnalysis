import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Nieto on 17/11/15.
 */
public class WDAverageData {

    /* *************************************************************************************************************** */
    /* Variables
    /* *************************************************************************************************************** */


    private GregorianCalendar date;
    private Boolean isValid;
    private Double temperature,wind_direction_in_degrees,wind_velocity,humidity,rain,barometer,rain_intensity,
            wind_chill,heat_index,dew_point,solar_radiation;
    private Integer station;


    /* *************************************************************************************************************** */
    /* Constuctores
    /* *************************************************************************************************************** */


    public WDAverageData(GregorianCalendar date)
    {
        this.date = date;
        temperature =0.0;
        wind_direction_in_degrees=0.0;
        wind_velocity=0.0;
        humidity=0.0;
        rain=0.0;
        barometer=0.0;
        station=0;
        rain_intensity=0.0;
        wind_chill=0.0;
        heat_index=0.0;
        dew_point=0.0;
        solar_radiation=0.0;
        isValid=Boolean.TRUE;
    }

    public WDAverageData(GregorianCalendar date,Double temperature,Double wind_direction,Double wind_velocity,
                         Double humidity,Double rain,
                         Double rain_intensity,Double barometer,Double wind_chill,Double heat_index,Double dew_point,
                         Double solar_radiation,Integer station, Boolean isValid)
    {
        this.date = date;
        this.temperature = temperature;
        this.wind_direction_in_degrees = wind_direction;
        this.wind_velocity = wind_velocity;
        this.wind_chill = wind_chill;
        this.humidity = humidity;
        this.rain = rain;
        this.rain_intensity = rain_intensity;
        this.barometer = barometer;
        this.heat_index = heat_index;
        this.dew_point = dew_point;
        this.solar_radiation = solar_radiation;
        this.station =station;
        this.isValid = isValid;
    }

    /* *************************************************************************************************************** */
    /* Métodos Get y Set
    /* *************************************************************************************************************** */

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWind_direction_in_degrees() {
        return wind_direction_in_degrees;
    }

    public void setWind_direction_in_degrees(Double wind_direction_in_degrees) {
        this.wind_direction_in_degrees = wind_direction_in_degrees;
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

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }


    /* *************************************************************************************************************** */
    /* Métodos to String
    /* *************************************************************************************************************** */

    public String toString(){

        return  temperature + "," + wind_direction_in_degrees + "," + wind_velocity + "," + humidity + "," + rain + "," + barometer + "," +
                date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH)+1) + "-" + date.get(Calendar.DAY_OF_MONTH) + "," +
                date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND) + "," +
                station + "," + rain_intensity + ","  + wind_chill + "," + heat_index + "," + dew_point + "," +
                solar_radiation +","+ isValid;
    }

}
