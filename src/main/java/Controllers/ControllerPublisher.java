package Controllers;

import Sensors.SensorInterface;
import org.eclipse.paho.client.mqttv3.*;

import java.util.HashMap;
import java.util.Map;

public class ControllerPublisher {
    private final Map<String, SensorInterface> sensors;
    private final String controllerName;

    public ControllerPublisher(HashMap<String, SensorInterface> sensors,
                               String controllerName) {
        this.sensors = sensors;
        this.controllerName = controllerName + "/";
    }

    public void publicData() {
        try {
            String topicName;
            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.connect();
            for (Map.Entry<String, SensorInterface> entry : sensors.entrySet()) {
                topicName = controllerName + entry.getKey();
                MqttMessage message = new MqttMessage();
                message.setPayload(Double.toString(entry.getValue().getData()).getBytes());
                client.publish(topicName, message);
            }
            Thread.sleep(10000);
            client.disconnect();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttSecurityException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
