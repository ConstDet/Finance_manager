package ru.netology.client;
//класс формирования строки JSON и отправки запроса на сервер

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RecordInServer {

    private Scanner scanner;
    private BufferedReader in;
    private PrintWriter out;

    public RecordInServer(Scanner scanner, BufferedReader in, PrintWriter out) {
        this.scanner = scanner;
        this.in = in;
        this.out = out;
    }

    public void inServer() throws IOException {
        try {
            Record record = new Record();
            System.out.println("Укажите наменование продукта:");
            record.setTitle(scanner.nextLine().toLowerCase());
            System.out.println("Укажите стоимость продукта:");
            String d = scanner.nextLine();
            record.setSum(Double.parseDouble(d));
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            Date date = new Date(System.currentTimeMillis());
            record.setDate(format.format(date));
            Gson gson = new GsonBuilder().create();
            String str = gson.toJson(record);
            out.println(str);//отправили сообщение
            String line = in.readLine();//приняли ответ
            JSONParser jsonParser = new JSONParser();
            try {
                Object obj = jsonParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject maxCategory = (JSONObject) jsonObject.get("maxCategory");
                System.out.println("За весь период: " + maxCategory.get("category"));
                System.out.println("Сумма: " + maxCategory.get("sum"));

                JSONObject maxYearCategory = (JSONObject) jsonObject.get("maxYearCategory");
                System.out.println("За год: " + maxYearCategory.get("category"));
                System.out.println("Сумма: " + maxYearCategory.get("sum"));

                JSONObject maxMonthCategory = (JSONObject) jsonObject.get("maxMonthCategory");
                System.out.println("За месяц: " + maxMonthCategory.get("category"));
                System.out.println("Сумма: " + maxMonthCategory.get("sum"));

                JSONObject maxDayCategory = (JSONObject) jsonObject.get("maxDayCategory");
                System.out.println("За день: " + maxDayCategory.get("category"));
                System.out.println("Сумма: " + maxDayCategory.get("sum"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            System.out.println("При вводе вещественного числа используйте точку \".\" " + e);
        }
    }
}
