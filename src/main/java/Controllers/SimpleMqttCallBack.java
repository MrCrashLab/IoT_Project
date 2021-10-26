package Controllers;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class SimpleMqttCallBack implements MqttCallback {

    Map<String, String> messages = new HashMap<>();
    int index = 0;

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\t"+ s +" " +new String(mqttMessage.getPayload()) );
        messages.put(s + index, new String(mqttMessage.getPayload()));
        System.out.println(messages.toString());
        index++;
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
