package ch.epfl.sweng.project.weather;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeatherForecastTest {

    // These three tests show that the report given for each three days are not the wanted one (Bug 2)
    @Test
    public void givesTheRightReportForToday(){
        double delta = 0.00001;
        double todayMin = 14.0;
        double todayMax = 32.4;
        double todayAvg = (todayMax + todayMin)/2.0;
        String todayWeather = "Sunny";
        String todayIcn = "Sun.icn";
        WeatherReport todayReport = new WeatherReport(todayAvg, todayMin, todayMax, todayWeather, todayIcn);
        WeatherReport tomorrowReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherReport dayAfterReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherForecast forecast = new WeatherForecast(new WeatherReport[]{todayReport, tomorrowReport, dayAfterReport});

        assertEquals(todayReport.averageTemperature, forecast.getWeatherReport(WeatherForecast.Day.TODAY).averageTemperature, delta);
        assertEquals(todayReport.minimumTemperature, forecast.getWeatherReport(WeatherForecast.Day.TODAY).minimumTemperature, delta);
        assertEquals(todayReport.maximalTemperature, forecast.getWeatherReport(WeatherForecast.Day.TODAY).maximalTemperature, delta);
        assertEquals(todayReport.weatherType, forecast.getWeatherReport(WeatherForecast.Day.TODAY).weatherType);
        assertEquals(todayReport.weatherIcon, forecast.getWeatherReport(WeatherForecast.Day.TODAY).weatherIcon);
    }

    @Test
    public void givesTheRightReportForTomorrow(){
        double delta = 0.00001;
        double tomorrowMin = 14.0;
        double tomorrowMax = 32.4;
        double tomorrowAvg = (tomorrowMax + tomorrowMin)/2.0;
        String tomorrowWeather = "Sunny";
        String tomorrowIcn = "Sun.icn";
        WeatherReport tomorrowReport = new WeatherReport(tomorrowAvg, tomorrowMin, tomorrowMax, tomorrowWeather, tomorrowIcn);
        WeatherReport todayReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherReport dayAfterReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherForecast forecast = new WeatherForecast(new WeatherReport[]{todayReport, tomorrowReport, dayAfterReport});

        assertEquals(tomorrowReport.averageTemperature, forecast.getWeatherReport(WeatherForecast.Day.TOMORROW).averageTemperature, delta);
        assertEquals(tomorrowReport.minimumTemperature, forecast.getWeatherReport(WeatherForecast.Day.TOMORROW).minimumTemperature, delta);
        assertEquals(tomorrowReport.maximalTemperature, forecast.getWeatherReport(WeatherForecast.Day.TOMORROW).maximalTemperature, delta);
        assertEquals(tomorrowReport.weatherType, forecast.getWeatherReport(WeatherForecast.Day.TOMORROW).weatherType);
        assertEquals(tomorrowReport.weatherIcon, forecast.getWeatherReport(WeatherForecast.Day.TOMORROW).weatherIcon);
    }

    @Test
    public void givesTheRightReportForDayAfterTomorrow(){
        double delta = 0.00001;
        double dayAfterTomorrowMin = 14.0;
        double dayAfterTomorrowMax = 32.4;
        double dayAfterTomorrowAvg = (dayAfterTomorrowMax + dayAfterTomorrowMin)/2.0;
        String dayAfterTomorrowWeather = "Sunny";
        String dayAfterTomorrowIcn = "Sun.icn";
        WeatherReport dayAfterReport = new WeatherReport(dayAfterTomorrowAvg, dayAfterTomorrowMin, dayAfterTomorrowMax, dayAfterTomorrowWeather, dayAfterTomorrowIcn);
        WeatherReport todayReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherReport tomorrowReport = new WeatherReport(0.0, 0.0,0.0, "Test", "test.icn");
        WeatherForecast forecast = new WeatherForecast(new WeatherReport[]{todayReport, tomorrowReport, dayAfterReport});

        assertEquals(dayAfterReport.averageTemperature, forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW).averageTemperature, delta);
        assertEquals(dayAfterReport.minimumTemperature, forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW).minimumTemperature, delta);
        assertEquals(dayAfterReport.maximalTemperature, forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW).maximalTemperature, delta);
        assertEquals(dayAfterReport.weatherType, forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW).weatherType);
        assertEquals(dayAfterReport.weatherIcon, forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW).weatherIcon);
    }
}
