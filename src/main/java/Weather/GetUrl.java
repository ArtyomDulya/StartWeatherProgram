package Weather;



import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;



public class GetUrl {

    protected static String weatherUrl(String urlAdress) throws IOException {
        URL url = new URL(urlAdress);
        StringBuffer content = new StringBuffer();
        URLConnection urlConn = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line + "\n");
        }
        bufferedReader.close();

        return content.toString();
    }

    protected static void getWeather() throws IOException {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите город: ");
            String cityName = sc.nextLine();

            String output = weatherUrl("https://api.openweathermap.org/data/2.5/weather?q=" + cityName +
                    "&appid=" + "408af993dd3301318fa884374fa752fd" + "&units=metric&lang=ru");

            JSONObject json = new JSONObject(output);

            String city = (String) json.get("name");
            int setTemp = json.getJSONObject("main").getInt("temp");
            int setHumidity = json.getJSONObject("main").getInt("humidity");
            int setPressure = json.getJSONObject("main").getInt("pressure");
            double setSpeed = json.getJSONObject("wind").getInt("speed");
            long setSunrise = json.getJSONObject("sys").getInt("sunrise");
            Date data = new Date(setSunrise * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm a", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

            System.out.println("Название города: " + city);
            System.out.println("Температура: " + setTemp + "°C");
            System.out.println("Влажность: " + setHumidity + " %");
            System.out.println("Давление: " + setPressure + " hPa");
            System.out.println("Скорость ветра: " + setSpeed + " м/с");
            System.out.println("Время рассвета: " + sdf.format(data));

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
