package com.wkrj.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;


public class DataBackup {

    /**
     * hostIP        ip地址，可以是本机也可以是远程
     * userName      数据库的用户名
     * password      数据库的密码
     * savePath      备份的路径
     * fileName      备份的文件名
     * databaseName  需要备份的数据库的名称
     * mysqldumpPath mysql的bin路径
     * @return
     */
    public static boolean backup(BufferedReader bufferedReader, String savePath, String fileName, String tableName) {
        Properties properties = new Properties();
        try {
            properties.load(bufferedReader);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String mysqldumpPath = properties.getProperty("mysqldumpPath");
        String hostIp = properties.getProperty("hostIP");
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");
        String databaseName = properties.getProperty("databaseName");
        String port = properties.getProperty("port");


        fileName += ".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        StringBuffer command = new StringBuffer();
        command.append(mysqldumpPath).append("mysqldump -u").append(userName).append(" -p").append(password)//密码是用的小p，而端口是用的大P。  
                .append(" -h").append(hostIp).append(" -P").append(port).append(" ").append(databaseName + " " + tableName).append(" -r ").append(savePath + fileName);
        Runtime runtime = Runtime.getRuntime();
        try {
            //System.out.println(command.toString());
            Process process = runtime.exec(command.toString());
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        }

/*
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mysqldumpPath);
        stringBuilder.append("mysqldump").append(" -h").append(hostIP);
        stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=false");
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ")
                .append(" ").append(databaseName+" "+tableName);
        System.out.println(stringBuilder.toString());
        Runtime runtime = Runtime.getRuntime();
        try {
            //调用外部执行exe文件的javaAPI
            Process process = runtime.exec(stringBuilder.toString());
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return false;
    }

    /**
     * @param filepath 数据库备份的脚本路径
     * @return
     */
    public static boolean recover(BufferedReader bufferedReader, String filepath) {
        Properties properties = new Properties();
        try {
            properties.load(bufferedReader);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String mysqldumpPath = properties.getProperty("mysqldumpPath");
        String ip = properties.getProperty("hostIP");//ip地址
        String userName = properties.getProperty("userName");//账号
        String password = properties.getProperty("password");//密码
        String port = properties.getProperty("port");
        String database = properties.getProperty("databaseName");//数据库


        //第一步，获取登录命令语句
        String loginCommand = new StringBuffer().append(mysqldumpPath).append("mysql -h").append(ip).append(" -u").append(userName).append(" -p").append(password)
                .append(" -P").append(port).toString();
        //第二步，获取切换数据库到目标数据库的命令语句  
        String switchCommand = new StringBuffer().append("use ").append(database).toString();
        //第三步，获取导入的命令语句  
        String importCommand = new StringBuffer(" source ").append(filepath).toString();
        //需要返回的命令语句数组           
        String[] commands = new String[]{loginCommand, switchCommand, importCommand};
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(commands[0]);
            java.io.OutputStream os = process.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(commands[1] + "\r\n" + commands[2]);
            writer.flush();
            writer.close();
            os.close();
            //System.out.println("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
