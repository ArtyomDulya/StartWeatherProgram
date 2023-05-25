package Weather;

public class MainWeather extends GetUrl {

    public static void main(String[] args) {
        try {
            getWeather();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
