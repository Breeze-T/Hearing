package com.bootdo.common.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.aspect.WebLogAspect;
import com.bootdo.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 2019/6/10.
 */
@Controller
@RequestMapping("/common/dataBase")
public class DataBaseController {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Value("${sftpHost}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
    private String database="basetelitjc";
    @Log("备份数据库")
    @ResponseBody
    @GetMapping("/backup")
    public R backup()  throws Exception{
        backup(dbUrl,username,password,"/home/backup",database,database);
        return R.ok();
    }
    @Log("还原数据库")
    @ResponseBody
    @GetMapping("/recover")
    public R recover()  throws Exception{
        boolean b=recover("/home/backup/"+database+".sql",dbUrl,database,username,password);
        logger.info("结果 : " + b);
        if(!b){
            return R.error("暂无备份的数据，无法还原");
        }
        return R.ok();
    }
    @Log("初始化数据库")
    @ResponseBody
    @GetMapping("/begin")
    public R begin()  throws Exception{
        boolean b= recover("/home/backup/basetelitbegin.sql",dbUrl,database,username,password);
        logger.info("结果 : " + b);
        if(!b){
            return R.error("缺少初始化数据，无法初始化");
        }
        return R.ok();
    }
    /**
     * @param hostIP ip地址，可以是本机也可以是远程
     * @param userName 数据库的用户名
     * @param password 数据库的密码
     * @param savePath 备份的路径
     * @param fileName 备份的文件名
     * @param databaseName 需要备份的数据库的名称
     * @return
     */
    public  boolean backup(String hostIP, String userName, String password, String savePath, String fileName,
                           String databaseName) throws Exception{
        fileName +=".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt");
        //stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
        stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=true");
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                return true;
            }
        } catch (IOException e) {
            logger.info("结果 : " +e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.info("结果 : " +e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param filepath 数据库备份的脚本路径
     * @param ip IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public  boolean recover(String filepath,String ip,String database, String userName,String password) {


        //String stmt1 = "mysqladmin -h "+ip+" -u "+userName+" -p"+password+" create "+database;

        String stmt2 = "mysql "+" -u"+userName+" -p"+password+" "+database+" < " + filepath;

          String[] cmd = { "cmd", "/c", stmt2 };

        try {
            logger.info("stmt1 :" + stmt2);
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec(cmd);
            logger.info("process :" + process);
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                logger.info("waitFor :" + process.waitFor());
                return true;
            }
        } catch (IOException e) {
            logger.info("结果 : " +e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.info("结果 : " +e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
