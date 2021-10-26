package Sensors;

import org.decimal4j.util.DoubleRounder;

public class HumidSensor implements SensorInterface {
    private double humid;
    private final String name = "humidity";

    @Override
    public double getData() {
        humid =  (Math.random()*99);
        humid = DoubleRounder.round(humid, 1);
        return humid;
    }

    @Override
    public String getName() {
        return name;
    }
}
