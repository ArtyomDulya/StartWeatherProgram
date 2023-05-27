package TeleBot;


import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherBot extends TelegramLongPollingBot implements BaseBot {

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message inMess = update.getMessage();
            String chatId = inMess.getChatId().toString();
            String response = "";
            if (inMess.getText().equals("/start")) {
                response = "Приветствую вас, я могу рассказать вам о погоде, в выбранном вами городе," +
                        "понимаю сообщения на русском и английском языках,ведите название города.";
            } else {
                response = getWeather(inMess.getText());
            }
            SendMessage outMess = new SendMessage();
            outMess.setChatId(chatId);
            outMess.setText(response);
            execute(outMess);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String weatherUrl(String urlAdress) {
        try {
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
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String getWeather(String cityName) {

        try {
            String output = weatherUrl("https://api.openweathermap.org/data/2.5/weather?q=" + cityName +
                    "&appid=" + OPEN_WEATHER_TOKEN + "&units=metric&lang=ru");

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

            return String.format(
                    "Название города: %s\n" +
                    "Температура: %s °C\n" +
                    "Влажность: %s  %%\n" +
                    "Давление: %s  hPa\n" +
                    "Скорость ветра: %s м/с\n" +
                    "Время рассвета: %s ", city, setTemp, setHumidity, setPressure, setSpeed, sdf.format(data));
        } catch (Exception e) {
            return "Вы наверно допустили синтаксическую ошибку, попробуйте еще раз.";
        }
    }
}



