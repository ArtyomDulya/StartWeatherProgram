package Weather;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class Main extends GetUrl {

    public static void main(String[] args) {
        try {
            getWeather();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
