package com.javarush.lectures.rest_example;

import com.javarush.lectures.rest_example.ftipFile.Sftp;
import com.javarush.lectures.rest_example.model.User;
import com.javarush.lectures.rest_example.parse.ExcelParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartParseUser {

    public static void main(String[] args) {
        String sourceHost = "mail.ru";//хост сайта
        Integer sourcePort = 21;//порт сайта
        String sourceUser = "kolya.chechnev@mail.ru";//логин клиента на сайте
        String sourcePassword = "";//пароль клиента на сайте
        String sourceFile = "";//ссылка на файл с сайта
        String localFile = "C:\\Users\\kolya\\Desktop\\Выполняй каждый день\\Работа Сережи\\Проект 2\\workWithFtpFile\\Staff_list (example).xlsx";//ссылка куда скачать файл

        /*try {
            Sftp.Downloader.download(sourceHost, sourcePort, sourceUser, sourcePassword, localFile, sourceFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
        List<String[]> arrList = ExcelParser.parse("C:\\Users\\kolya\\Desktop\\Выполняй каждый день\\Работа Сережи\\Проект 2\\workWithFtpFile\\Staff_list (example).xlsx");
        arrList.remove(0);
        List<User> userList = new ArrayList<User>();

        for (String[] strArr : arrList) {
            User user = new User();
            user.setId(Integer.parseInt(strArr[0]));
            user.setProject(strArr[1]);
            user.setLastName(strArr[2]);
            user.setFirstName(strArr[3]);
            user.setMiddleName(strArr[4]);
            user.setBirthDate(strArr[5]);
            user.setGender(strArr[6]);
            user.setEmail(strArr[7]);
            user.setPosition(strArr[8]);
            user.setDepartment(strArr[9]);
            user.setHiringDate(strArr[10]);
            user.setTerminationDate(strArr[11]);


            userList.add(user);
        }
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }
}
