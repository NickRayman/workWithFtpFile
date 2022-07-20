package com.javarush.lectures.rest_example.ftipFile;

import com.jcraft.jsch.*;

/**
 * Класс FTP.Sftp для скачивания файла с удаленного сервера и дальнейшего
 * закачивания уже измененного файла.
 */
public class Sftp {
    private static long time0;

    /**
     * Вложенный класс Downloader для скачивания файла
     * с удаленного сервера
     */
    public static class Downloader {

        public static void download(String host, Integer port, String user, String password, String localFile, String sourceFile) {
            try {
                /**
                 * Создаем объект JSch, который будет заходить на сервер по FTP
                 */
                JSch jsch = new JSch();

                /**
                 * Создаем сессию для авторизации на FTP - сервере
                 */
                Session session = jsch.getSession(user, host, port);
                session.setUserInfo(new MyUserInfo(password));
                session.connect();

                System.out.println("Session connected!");
                /**
                 * Если сессия прошла успешно, то будет
                 * открыт защищенный канал для передачи файлов
                 */
                Channel channel = session.openChannel("sftp");
                channel.connect();

                System.out.println("Channel connected!");
                /**
                 * После присоединения канала, будет происходить
                 * скачивание файла с удаленного сервера
                 */
                ChannelSftp channelSftp = (ChannelSftp) channel;

                try {
                    System.out.println("Start");
                    time0 = System.currentTimeMillis();

                    /**
                     * Метод get(), объекта channelSftp
                     * принимает следующие параметры:
                     * файл источник (который будем скачивать);
                     * путь на локальном компьютере, куда будет скачан файл;
                     * прогресс скачивания файла;
                     * будем переписывать файл, если на локальном компьютере такой файл уже есть
                     */
                    channelSftp.get(sourceFile, localFile, new MyProgressMonitor(sourceFile), ChannelSftp.OVERWRITE);
                } catch (SftpException e) {
                    e.printStackTrace();
                }

                channelSftp.exit();

                session.disconnect();

            } catch (JSchException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Вложенный класс Uploader для закачивания файла
     * на удаленный сервер
     */
    public static class Uploader {

        public static void upload(String host, Integer port, String user, String password, String localFile, String destinationFile) {
            try {
                /**
                 * Создаем объект JSch, который будет заходить на сервер по FTP
                 */
                JSch jsch = new JSch();

                /**
                 * Создаем сессию для авторизации на FTP - сервере
                 */
                Session session = jsch.getSession(user, host, port);
                session.setUserInfo(new MyUserInfo(password));
                session.connect();

                System.out.println("Session connected!");
                /**
                 * Если сессия прошла успешно, то будет
                 * открыт защищенный канал для передачи файлов
                 */
                Channel channel = session.openChannel("sftp");
                channel.connect();

                System.out.println("Channel connected!");
                /**
                 * После присоединения канала, будет происходить
                 * закачивание файла на удаленный сервер
                 */
                ChannelSftp channelSftp = (ChannelSftp) channel;

                try {
                    System.out.println("Start");
                    time0 = System.currentTimeMillis();

                    /**
                     * Метод get(), объекта channelSftp
                     * принимает следующие параметры:
                     * файл источник (который будем скачивать);
                     * путь на локальном компьютере, куда будет скачан файл;
                     * прогресс скачивания файла;
                     * будем переписывать файл, если на локальном компьютере такой файл уже есть
                     */
                    channelSftp.put(localFile, destinationFile, new MyProgressMonitor(destinationFile), ChannelSftp.OVERWRITE);
                } catch (SftpException e) {
                    e.printStackTrace();
                }

                channelSftp.exit();

                session.disconnect();

            } catch (JSchException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Вложенный класс MyUserInfo реализующий интерфейсы
     * UserInfo, UIKeyboardInteractive, который будет
     * авторизовываться на сервере
     */
    private static class MyUserInfo implements UserInfo, UIKeyboardInteractive {

        /**
         * Пароль user'а
         */
        private String password;

        public MyUserInfo(String password) {
            this.password = password;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public boolean promptYesNo(String s) {
            return true;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public boolean promptPassword(String s) {
            return true;
        }

        @Override
        public boolean promptPassphrase(String s) {
            return true;
        }

        @Override
        public void showMessage(String s) {

        }

        @Override
        public String[] promptKeyboardInteractive(String s, String s1, String s2, String[] strings, boolean[] booleans) {
            return new String[]{password};
        }
    }

    /**
     * Вложенный класс MyProgressMonitor для отслеживания прогресса скачивания/закачивания файла.
     */
    private static class MyProgressMonitor implements SftpProgressMonitor {
        private String sourceFile;

        private long count;
        private long max;
        private long percent;

        public MyProgressMonitor(String sourceFile) {
            this.sourceFile = sourceFile;
        }

        @Override
        public void init(int op, String src, String dest, long max) {
            this.max = max;
            this.count = 0;
            this.percent = -1;
        }

        /**
         * Счетчик в процентах скачанных байтов.
         *
         * @param count
         * @return
         */
        @Override
        public boolean count(long count) {
            this.count += count;

            if (percent >= this.count * 100 / max) {
                return true;
            }

            percent = this.count * 100 / max;
            System.out.println("Progress: " + percent + " %");

            return true;
        }

        /**
         * Как только скачивание завершиться будет вызван метод end().
         */
        @Override
        public void end() {
            System.out.println("Progress: finished is " + (System.currentTimeMillis() - time0) + "ms");
        }
    }


}
