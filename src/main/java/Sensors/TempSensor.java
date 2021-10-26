package Sensors;

import org.decimal4j.util.DoubleRounder;

import java.util.Random;

public class TempSensor implements SensorInterface {
    private double temperature;
    private final String name = "temperature";

    @Override
    public double getData() {
        temperature = Math.abs((float) (new Random().nextGaussian() * 25));
        temperature = DoubleRounder.round(temperature, 1);
        return temperature;
    }
    @Override
    public String getName() {
        return name;
    }

}
