package com.bootdo.common.utils;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

/**
 * sftp工具。注意：构造方法有两个：分别是基于密码认证、基于秘钥认证。
 * 
 * @see http://xliangwu.iteye.com/blog/1499764
 * @author Somnus
 */
public class SFTPUtil {
	private transient Logger log = LoggerFactory.getLogger(this.getClass());

	private ChannelSftp sftp;

	private Session session;
	/** FTP 登录用户名 */
	private String username;
	/** FTP 登录密码 */
	private String password;
	/** 私钥文件的路径 */
	private String keyFilePath;
	/** FTP 服务器地址IP地址 */
	private String host;
	/** FTP 端口 */
	private int port;
	
	@Value(value="${uploadUrl}")
	private static String uploadUrl;    //uploadUrl = /home/firewarn/tomcat_cherry/webapps/
	
	@Value(value="${uploadImageUrl}")
	private static String uploadImageUrl;   //uploadImageUrl = uploadImage/
	
	/**
	 * 构造基于密码认证的sftp对象
	 * 
	 * @param userName
	 * @param password
	 * @param host
	 * @param port
	 */
	public SFTPUtil(String username, String password, String host, int port) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * 构造基于秘钥认证的sftp对象
	 * 
	 * @param userName
	 * @param host
	 * @param port
	 * @param keyFilePath
	 */
	public SFTPUtil(String username, String host, int port, String keyFilePath) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.keyFilePath = keyFilePath;
	}

	public SFTPUtil() {
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @throws Exception
	 */
	public void login() {
		try {
			JSch jsch = new JSch();
			if (keyFilePath != null) {
				jsch.addIdentity(keyFilePath);// 设置私钥
				log.info("sftp connect,path of private key file：{}",
						keyFilePath);
			}
			log.info("sftp connect by host:{} username:{}", host, username);

			session = jsch.getSession(username, host, port);
			log.info("Session is build");
			if (password != null) {
				session.setPassword(password);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();
			log.info("Session is connected");

			Channel channel = session.openChannel("sftp");
			channel.connect();
			log.info("channel is connected");

			sftp = (ChannelSftp) channel;
			log.info(String.format(
					"sftp server host:[%s] port:[%s] is connect successfull",
					host, port));
		} catch (JSchException e) {
			e.printStackTrace();
			log.error(
					"Cannot connect to specified sftp server : {}:{} \n Exception message is: {}",
					new Object[] { host, port, e.getMessage() });
		}
	}

	/**
	 * 关闭连接 server
	 */
	public void logout() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				log.info("sftp is closed already");
			}
		}
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
				log.info("sshSession is closed already");
			}
		}
	}

	/**
	 * 将输入流的数据上传到sftp作为文件
	 * 
	 * @param directory
	 *            上传到该目录
	 * @param sftpFileName
	 *            sftp端文件名
	 * @param in
	 *            输入流
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, InputStream input)
			throws SftpException {
		try {
			sftp.cd(directory);
		} catch (SftpException e) {
			e.printStackTrace();
			log.warn("directory is not exist");
			sftp.mkdir(directory);
			sftp.cd(directory);
		}
		sftp.put(input, sftpFileName);
		log.info("file:{} is upload successful", sftpFileName);
		System.out.println("file:" + sftpFileName + " is upload successful");
	}

	/**
	 * 上传单个文件
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param uploadFile
	 *            要上传的文件,包括路径
	 * @throws FileNotFoundException
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String uploadFile)
			throws FileNotFoundException, SftpException {
		File file = new File(uploadFile);
		upload(directory, file.getName(), new FileInputStream(file));
	}

	/**
	 * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param byteArr
	 *            要上传的字节数组
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, byte[] byteArr)
			throws SftpException {
		upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
	}

	/**
	 * 将字符串按照指定的字符编码上传到sftp
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param dataStr
	 *            待上传的数据
	 * @param charsetName
	 *            sftp上的文件，按该字符编码保存
	 * @throws UnsupportedEncodingException
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, String dataStr,
			String charsetName) throws UnsupportedEncodingException,
			SftpException {
		upload(directory, sftpFileName,
				new ByteArrayInputStream(dataStr.getBytes(charsetName)));

	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @throws SftpException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void download(String directory, String downloadFile, String saveFile)
			throws SftpException, FileNotFoundException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		File file = new File(saveFile);
		sftp.get(downloadFile, new FileOutputStream(file));
		log.info("file:{} is download successful", downloadFile);
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @return 字节数组
	 * @throws SftpException
	 * @throws IOException
	 * @throws Exception
	 */
	public byte[] download(String directory, String downloadFile)
			throws SftpException, IOException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		InputStream is = sftp.get(downloadFile);

		byte[] fileData = IOUtils.toByteArray(is);

		log.info("file:{} is download successful", downloadFile);
		return fileData;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @return 字节数组
	 * @throws SftpException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream downloadFile(String directory, String downloadFile)
			throws SftpException, IOException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		return sftp.get(downloadFile);
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @throws SftpException
	 * @throws Exception
	 */
	public void delete(String directory, String deleteFile)
			throws SftpException {
		sftp.cd(directory);
		sftp.rm(deleteFile);
	}

	public void deleteDir(String path) {
		try {
			sftp.rmdir(path);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	int i = 0;

	public Vector<?> listFiles(String directory) throws SftpException {
		try {
			sftp.cd(directory);
		} catch (SftpException e) {
			log.warn("directory is not exist");
			sftp.mkdir(directory);
		}
		return sftp.ls(directory);
	}
	
	public boolean isExtsts(String directory){
		try {
			System.out.println(directory);
			sftp.cd(directory);
			return true;
		} catch (SftpException e) {
			log.warn("directory is not exist");
			return false;
		}
	}

	/*
	 * 根据用户所在的学校创建该学校的一个文件夹，将图片上传到服务器地址指定的该学校文件夹
	 */
	public void mkDir(String serverDirString) throws SftpException{
    	sftp.mkdir(serverDirString);
    }

	public void allFile(SFTPUtil sftp, String addr, String classId,String className,String classTitle) {
		Vector<?> vector = null;
		try {
			vector = sftp.listFiles(addr);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<?> enumeration = vector.elements();
		while (enumeration.hasMoreElements()) {
			LsEntry lsEntry = (LsEntry) enumeration.nextElement();
			String id = UUID.randomUUID().toString().replace("-", "");
			String strId = UUID.randomUUID().toString().replace("-", "");
			String fileName = "";
			try {
				fileName = new String(
						(lsEntry.getFilename()).getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!lsEntry.getAttrs().isDir()) {
				i++;
				System.out
						.println("insert into t_news(id,news_title,theme_img,news_type,class_id,news_level,publisher,publish_time,flag,school_id,create_time) values('"
								+ id
								+ "','"+fileName.substring(0, fileName.lastIndexOf("."))+"','upload/tnews/"+className+"/" //学生风采###"+fileName.substring(0, fileName.lastIndexOf("."))+"
								+ fileName
								+ "','8','"+classId+"','2','淮南附小山南校区_管理员',"
								+ "now(),'1','3b392fb722ee40cbbd4ea14f14b5a8fd',now());");
				System.out
				.println("insert into t_news_content(id,news_id,news_content) values('"
						+ strId
						+ "','"
						+id
						+ "','<p style=\"text-align: center;\">"+classTitle+"</p><p><img src=\"http://60.175.97.35:8089/inetsoft-ccard/upload/tnews/"+className+"/"
						+ fileName
						+ "\"/></p>');");
			} else {
				if (".".equals(fileName) || "..".equals(fileName)) {

				} else {
					i++;
					System.out
							.println("insert into staff_cloud_disk(id,cloud_disk_name,the_id,parent_id,file_type,file_addr,create_user,create_time,folder_url,resource_type) values('"
									+ id
									+ "','"
									+ fileName
									+ "','b62bcf5a77584a209a1debcfdf13e712','"
									+ "','1','http://220.178.53.9:8089"
									+ addr
									+ "/"
									+ fileName
									+ "','4718',now(),'"
									+ addr + "','1');");
				}
			}
			//this.ss(str);
		}
		log.info("总数" + i);
	}

	public void ss(String s) {
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File("d:\\dd.txt");
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(s);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 22端口号在 resource-development.properties sftport可以查询到 allFile(SFTPUtil
	 * sftp,String addr,String parentId); 所有地址都需要为全路径名
	 */
	public static void main(String[] args) throws SftpException, IOException {
		SFTPUtil sftp = new SFTPUtil("root", "tlt12346", "172.16.4.10", 22);
		sftp.login();
		//Vector<?> vector = sftp.listFiles("/home/firewarn/tomcat_cherry/webapps/uploadFile/523bc87c2937477bafad347a5dea86ae/523bc87c2937477bafad347a5dea86ae");
		//User user=SecurityContextUtil.getCurrentUser();
	    String schoolName="翠庭园小学";
	    String dir="/xsfc/二3学生风采";
	    /*try {
	    	 sftp.mkDir(dir);
	    	 System.out.println("已经新建了文件夹："+dir);
		} catch (Exception e) {
			System.out.println("-------文件夹已经存在了-------"+dir);
		}*/
	   
	    	// 上传功能
	 		// 上传的目录
	 		/*String directory = dir+"/"+ DateUtil.convertToMin(new Date());
	 		System.out.println("directory:--------------" + directory);
	 		// 要上传的文件
	 		String uploadFile = "E:\\pic\\Tulips.jpg";
	 		//sftp.upload(directory, uploadFile);
	 		File file=new File(uploadFile);
	 		System.out.println("-----1----length"+file.length());
	 		FileInputStream fin = new FileInputStream(file);
            int s= fin.available();
	 		System.out.println("---文件总大小为：  "+s);
	 		sftp.upload(directory, "tips.jpg", fin);*/
		
		 sftp.allFile(sftp,"/xsfc/五5学生风采","96d8aefe-319a-4895-b7c1-069a43283b16","55","五年级5班学生风采");
		// List<String> list = new ArrayList<String>();
		// List<String> returnList = new ArrayList<String>();
		/*
		 * Enumeration<?> enumeration = vector.elements();
		 * while(enumeration.hasMoreElements()){ LsEntry lsEntry =
		 * (LsEntry)enumeration.nextElement(); String id =
		 * UUID.randomUUID().toString().replace("-", "");
		 * if(!lsEntry.getAttrs().isDir()){ System.out.println(
		 * "insert into staff_cloud_disk(id,cloud_disk_name,the_id,parent_id,file_type,file_size,file_addr,create_user,create_time,folder_url,resource_type) values('"
		 * +id+"','"+lsEntry.getFilename()+
		 * "','b62bcf5a77584a209a1debcfdf13e712','','2','"
		 * +lsEntry.getAttrs().getSize()+
		 * "','http://220.178.53.9:8089/uploadFile/合肥市稻香村小学/b62bcf5a77584a209a1debcfdf13e712/2016-2017-2学期/数学组',now(),'/uploadFile/合肥市稻香村小学/b62bcf5a77584a209a1debcfdf13e712/2016-2017-2学期','1');"
		 * ); }else{ if(".".equals(lsEntry.getFilename()) ||
		 * "..".equals(lsEntry.getFilename())){
		 * 
		 * }else{ System.out.println(
		 * "insert into staff_cloud_disk(id,cloud_disk_name,the_id,parent_id,file_type,file_addr,create_user,create_time,folder_url,resource_type) values('"
		 * +id+"','"+lsEntry.getFilename()+
		 * "','b62bcf5a77584a209a1debcfdf13e712','0f3570736f3f4fbfb89d25e7082ae126','1','http://220.178.53.9:8089/uploadFile/合肥市稻香村小学/b62bcf5a77584a209a1debcfdf13e712/2016-2017-2学期/数学组/一年级/"
		 * +lsEntry.getFilename()+
		 * "','66666669502',now(),'/uploadFile/合肥市稻香村小学/b62bcf5a77584a209a1debcfdf13e712/2016-2017-2学期/数学组','1');"
		 * ); } } }
		 */

		// for(String value:list){
		// Vector<?> vector2 =
		// sftp.listFiles("/home/git/resource/tomcat/webapps/uploadHtml"+"/"+value);
		// Enumeration<?> enumeration2 = vector2.elements();
		// while(enumeration2.hasMoreElements()){
		// LsEntry lsEntry = (LsEntry)enumeration2.nextElement();
		// if(lsEntry.getFilename().contains("html")){
		// System.out.println(value+"-->"+lsEntry.getFilename());
		// }
		// }
		// }
		sftp.logout();
		/*
		 * FTPClient ftpClient = new FTPClient(); //连接FTP服务器
		 * ftpClient.connect("112.27.234.68", 21); //登录FTP服务器
		 * ftpClient.login("telit_admin", "tlt@12346"); //验证FTP服务器是否登录成功 int
		 * replyCode = ftpClient.getReplyCode();
		 * ftpClient.changeWorkingDirectory("/userfiles/res/"); FTPFile[]
		 * ftpFiles = ftpClient.listFiles(); for(FTPFile file : ftpFiles){
		 * if("f6b0329f5d79429b89a812f306911c3d.mp4"
		 * .equalsIgnoreCase(file.getName())){
		 * System.out.println(file.getName()); File localFile = new File("d:\\"
		 * + file.getName()); OutputStream os = new FileOutputStream(localFile);
		 * ftpClient.retrieveFile(file.getName(), os); os.close(); } }
		 * ftpClient.logout();
		 */

		// FTPClient ftpClient = new FTPClient();
		// ftpClient.setControlEncoding("utf-8");
		// ftpClient.connect("192.168.1.11", 22); //连接ftp服务器
		// ftpClient.login("firewarn", "tlt12346"); //登录ftp服务器
		// int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
		// System.out.println(replyCode);
		//
		// List<String> list = new ArrayList<String>();
		// list.add("ew");
		// list.add("sff");
		// System.out.println(list.toString().substring(1,list.toString().length()-1));
	}

	
	private static long getFileSize(File f)throws Exception{//取得文件大小{
	        long s=0;
	        if (f.exists()) {
	            FileInputStream fis = null;
	            fis = new FileInputStream(f);
	            s= fis.available();
	        } else {
	            f.createNewFile();
	            System.out.println("文件不存在");
	        }
	        return s;
	     }
	
}
