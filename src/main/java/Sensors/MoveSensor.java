package Sensors;

public class MoveSensor implements SensorInterface {
    private double move;
    private final String name = "move";

    @Override
    public double getData() {
        move = Math.round(Math.random());
        return move;
    }

    @Override
    public String getName() {
        return name;
    }
}
