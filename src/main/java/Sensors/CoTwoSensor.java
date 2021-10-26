package Sensors;

import org.decimal4j.util.DoubleRounder;

import java.util.Random;

public class CoTwoSensor implements SensorInterface {
    private double coTwo;
    private final String name = "co2";

    @Override
    public double getData() {
        coTwo = Math.abs(new Random().nextGaussian() * 2500);
        coTwo = DoubleRounder.round(coTwo, 0);
        return coTwo;
    }

    @Override
    public String getName() {
        return name;
    }
}
