package com.javarush.lectures.rest_example.ftipFile;

public class Starter {

    public static void main(String[] args) {
        String sourceHost = "host1.com";
        Integer sourcePort = 22;
        String sourceUser = "user1";
        String sourcePassword = "password1";
        String sourceFile = "/home/user1/file1.txt";

        String localFile = "C:\\Users\\local_file.txt";

        String remoteHost = "host2.com";
        Integer remotePort = 22;
        String remoteUser = "user2";
        String remotePassword = "password2";
        String remoteFile = "/home/user2/file2.txt";

        try {
            Sftp.Downloader.download(sourceHost, sourcePort, sourceUser, sourcePassword, localFile, sourceFile);
            Sftp.Uploader.upload(remoteHost, remotePort, remoteUser, remotePassword, localFile, remoteFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
