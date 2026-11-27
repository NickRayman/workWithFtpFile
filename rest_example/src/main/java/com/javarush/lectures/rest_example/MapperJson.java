package com.javarush.lectures.rest_example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lectures.rest_example.DownloadParse.StartDownloadParse;
import com.javarush.lectures.rest_example.model.User;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;

public class MapperJson {

    public static void main(String[] args) throws IOException, ParseException {
        List<User> userList = StartDownloadParse.downloadParse();

        for (User user : userList) {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, user);
            String result = writer.toString();
            System.out.println(result);
        }

    }
}
