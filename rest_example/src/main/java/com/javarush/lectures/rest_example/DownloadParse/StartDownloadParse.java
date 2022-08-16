package com.javarush.lectures.rest_example.DownloadParse;

import com.javarush.lectures.rest_example.DownloadParse.DownloadFtp.Sftp;
import com.javarush.lectures.rest_example.DownloadParse.parse.ExcelParser;
import com.javarush.lectures.rest_example.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StartDownloadParse {

    public static List<User> downloadParse() throws ParseException {
        String sourceHost = "mail.ru";//хост сайта
        Integer sourcePort = 21;//порт сайта
        String sourceUser = "kolya.chechnev@mail.ru";//логин клиента на сайте
        String sourcePassword = "";//пароль клиента на сайте
        String sourceFile = "";//ссылка на файл с сайта

        String localFile = "C:\\Users\\kolya\\Desktop\\Выполняй каждый день\\Работа Сережи\\Staff_list (example).xlsx";//ссылка куда скачать файл


        /*try {
            Sftp.Downloader.download(sourceHost, sourcePort, sourceUser, sourcePassword, localFile, sourceFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
        /**
         * Получили файл, парсим
         */
        List<String[]> arrList = ExcelParser.parse("C:\\Users\\kolya\\Desktop\\Выполняй каждый день\\Работа Сережи\\Staff_list (example).xlsx");
        arrList.remove(0);
        List<User> userList = new ArrayList<User>();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());



        for (String[] strArr : arrList) {
            User user = new User();
            user.setId(null);
            user.setLogin(getRandomString(6));
            user.setEmploymentNumber(strArr[0]);
            user.setProject(null);
            user.setLastName(strArr[2]);
            user.setFirstName(strArr[3]);
            user.setMiddleName(strArr[4]);

            Date date = oldDateFormat.parse(strArr[5]);
            String result = newDateFormat.format(date);
            user.setBirthDate(result);

            if (strArr[6].equals("Женский"))
                user.setGender("77b5343f-4c86-4e1b-b019-f01c8ed66b48");
            else if (strArr[6].equals("Мужской"))
                user.setGender("663d2733-e5b4-40a8-956c-c8ac39ecb7c9");

            user.setEmail(strArr[7]);
            user.setPosition(strArr[8]);
            user.setDepartment(null);

            date = oldDateFormat.parse(strArr[10]);
            result = newDateFormat.format(date);
            user.setHiringDate(result);

            user.setTerminationDate(strArr[11]);


            userList.add(user);
        }
        return userList;
    }

    // длина Длина строки, требуемой пользователем
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
