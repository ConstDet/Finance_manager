package ru.netology.client;

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
import java.util.List;
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
        Record record = new Record();
        System.out.println("Укажите наменование продукта:");
        record.setTitle(scanner.nextLine());
        System.out.println("Укажите стоимость продукта:");
        record.setSum(scanner.nextDouble());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        record.setDate(format.format(date));
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(record);
        out.println(str);//отправили сообщение
        String line = in.readLine();
        JSONParser jsonParser = new JSONParser();
        try {
            //Object obj = jsonParser.parse(line);
            List<Object> = jsonParser.parse(line);
            надо как-то распарсить JSON объект
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject maxCategory = (JSONObject) jsonObject.get("maxCategory");
            System.out.println((String) maxCategory.get("category"));
            System.out.println((Double) maxCategory.get("sum"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
