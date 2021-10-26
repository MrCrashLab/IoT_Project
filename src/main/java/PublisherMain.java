import Controllers.ControllerPublisher;
import Sensors.*;

import java.util.HashMap;

public class PublisherMain {
    public static void main(String[] args) {
        HashMap<String, SensorInterface> map = new HashMap<>();
        map.put("temperature", new TempSensor());
        map.put("co2", new CoTwoSensor());
        map.put("humidity", new HumidSensor());
        map.put("move", new MoveSensor());
        ControllerPublisher controllerPublisher = new ControllerPublisher(map,"test");
        while(true)
            controllerPublisher.publicData();
    }
}
