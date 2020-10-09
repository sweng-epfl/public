package ch.epfl.sweng.project.weather;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherReportTest {
    @Test
    public void constructorWorks(){
        double min = 12.5;
        double max = 25.6;
        double avg = (min+max)/2.0;
        double delta = 0.00001;
        String weathType = "Clear";
        String icon = "Sun.icn";
        WeatherReport report = new WeatherReport(avg, min, max, weathType, icon);

    }

    @Test
    public void getterReturnRightValues(){
        // The bug that this test is the first one:
        // in the constructor of
        // WeatherReport, the maximumTemperature parameter value is assigned to the
        // minimumTemperature field. (Bug 1)

        double min = 12.5;
        double max = 25.6;
        double avg = (min+max)/2.0;
        double delta = 0.00001;
        String weathType = "Clear";
        String icon = "Sun.icn";
        WeatherReport report = new WeatherReport(avg, min, max, weathType, icon);

        assertEquals(min, report.minimumTemperature, delta);
        assertEquals(max, report.maximalTemperature, delta);
        assertEquals(avg, report.averageTemperature, delta);
        assertEquals(icon, report.weatherIcon);
        assertEquals(weathType, report.weatherType);
    }
}
