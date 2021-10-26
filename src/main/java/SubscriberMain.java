import Controllers.ControllerSubscriber;

import java.util.ArrayList;
import java.util.List;

public class SubscriberMain {
    public static void main(String[] args) {
        List<String> topicNames = new ArrayList<>();
        topicNames.add("test/temperature");
        topicNames.add("test/co2");
        topicNames.add("test/humidity");
        topicNames.add("test/move");
        ControllerSubscriber controllerSubscriber = new ControllerSubscriber(topicNames);
        controllerSubscriber.subscribeData();
    }
}
