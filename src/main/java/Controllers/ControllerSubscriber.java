package Controllers;

import org.eclipse.paho.client.mqttv3.*;

import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerSubscriber {
    private List<String> topicNames;
    Map<String, String> messages = new HashMap<>();
    private String jdbcUrl = "jdbc:postgresql://localhost:5432/IoT";
    private String psgr_lg = "postgres";
    private String psgr_pw = "SQL_password";
    public ControllerSubscriber(List<String> topicNames) {
        this.topicNames = topicNames;
        clearMap();
    }

    public void subscribeData(){
        System.out.println("== START SUBSCRIBER ==");
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, psgr_lg, psgr_pw);
            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection to MQTT broker lost!");
                }
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    messages.put(s, new String(mqttMessage.getPayload()));
                    System.out.println("hello "+ s);
                    if(!isNullMap()) {
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "INSERT INTO data_iot " +
                                        "(temperature," +
                                        "co2," +
                                        "humidity," +
                                        "move," +
                                        "my_time)" +
                                        "VALUES (?,?,?,?,?)");
                        preparedStatement.setDouble(1, Double.valueOf(messages.get(topicNames.get(0))));
                        preparedStatement.setDouble(2, Double.valueOf(messages.get(topicNames.get(1))));
                        preparedStatement.setDouble(3, Double.valueOf(messages.get(topicNames.get(2))));
                        preparedStatement.setDouble(4, Double.valueOf(messages.get(topicNames.get(3))));
                        preparedStatement.setTime(5, Time.valueOf(LocalTime.now()));
                        preparedStatement.execute();
                        clearMap();
                    }
                }
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("Delivery complete");
                }
            });
            client.connect();
            client.subscribe(topicNames.stream().toArray(String[]::new));
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearMap(){
        for (int i = 0;i<topicNames.size();i++){
            messages.put(topicNames.get(i), null);
        }
    }

    private boolean isNullMap(){
        for (int i = 0;i<topicNames.size();i++) {
            if (messages.get(topicNames.get(i)) == null)
                return true;
        }
        return false;
    }
}
