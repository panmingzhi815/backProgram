package com.donglucard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author panmingzhi
 * @createTime 2015年4月3日
 * @content 配置文件
 */
public class AppConfigrator{

	private String key_sqlserver_ip = "";
	private String key_sqlserver_port = "";
	private String key_username = "";
	private String key_password = "";
	private String key_databasename = "";
	
	private String key_tablename = "";
	private String key_userIdentifier = "";
	private String key_photo = "";
	
	private String key_photoPath = "";
	
	private String propertiesFilePath = "AppConfigrator.properties";
	
	final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if(this.pcs != null)this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if(this.pcs != null)this.pcs.removePropertyChangeListener(listener);
	}
	
	public void loadProperties(){
		File file = new File(propertiesFilePath);
		if(!file.exists()){
			try {
				file.createNewFile();
				writer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try (
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fis,Charset.forName("UTF-8"))){
			Properties properties = new Properties();
			properties.load(inputStreamReader);
			
			setKey_password(properties.getProperty("key_password", "1"));
			setKey_sqlserver_ip(properties.getProperty("key_sqlserver_ip", "127.0.0.1"));
			setKey_sqlserver_port(properties.getProperty("key_sqlserver_port", "1433"));
			setKey_username(properties.getProperty("key_username", "sa"));
			setKey_tablename(properties.getProperty("key_tablename","UserInfo"));
			setKey_userIdentifier(properties.getProperty("key_userIdentifier", "userIdentifier"));
			setKey_photo(properties.getProperty("key_photo", "photo"));
			setKey_photoPath(properties.getProperty("key_photoPath", "D:\\"));
			setKey_databasename(properties.getProperty("key_databasename","KTECM"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writer() {
		try (
			FileOutputStream fos = new FileOutputStream(propertiesFilePath , false);
			PrintWriter out = new PrintWriter(fos);
			) {
			out.println("#sqlserver数据库ip地址");
			out.println(String.format("%s=%s", "key_sqlserver_ip","127.0.0.1"));
			out.println("#sqlserver数据库端口");
			out.println(String.format("%s=%s", "key_sqlserver_port","1433"));
			out.println("#sqlserver数据库登录名");
			out.println(String.format("%s=%s", "key_username","sa"));
			out.println("#sqlserver数据库登录密码");
			out.println(String.format("%s=%s", "key_password","1"));
			
			out.println("#sqlserver老数据名");
			out.println(String.format("%s=%s", "key_databasename","KTECM"));
			
			out.println("#老数据库用户表名");
			out.println(String.format("%s=%s", "key_tablename","UserInfo"));
			out.println("#老数据库用户编号字段名");
			out.println(String.format("%s=%s", "key_userIdentifier","userIdentifier"));
			out.println("#老数据库用户图片字段名");
			out.println(String.format("%s=%s", "key_photo","photo"));
			out.println("#老数据库用户图片photo文件夹父目录");
			out.println(String.format("%s=%s", "key_photoPath","D:\\"));
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getKey_sqlserver_ip() {
		return key_sqlserver_ip;
	}

	public void setKey_sqlserver_ip(String key_sqlserver_ip) {
		this.key_sqlserver_ip = key_sqlserver_ip;
		pcs.firePropertyChange("key_sqlserver_ip", null, null);
	}

	public String getKey_sqlserver_port() {
		return key_sqlserver_port;
	}

	public void setKey_sqlserver_port(String key_sqlserver_port) {
		this.key_sqlserver_port = key_sqlserver_port;
		pcs.firePropertyChange("key_sqlserver_port", null, null);
	}

	public String getKey_username() {
		return key_username;
	}

	public void setKey_username(String key_username) {
		this.key_username = key_username;
		pcs.firePropertyChange("key_username", null, null);
	}

	public String getKey_password() {
		return key_password;
	}

	public void setKey_password(String key_password) {
		this.key_password = key_password;
		pcs.firePropertyChange("key_password", null, null);
	}

	public String getKey_tablename() {
		return key_tablename;
	}

	public void setKey_tablename(String key_tablename) {
		this.key_tablename = key_tablename;
	}

	public String getKey_userIdentifier() {
		return key_userIdentifier;
	}

	public void setKey_userIdentifier(String key_userIdentifier) {
		this.key_userIdentifier = key_userIdentifier;
	}

	public String getKey_photo() {
		return key_photo;
	}

	public void setKey_photo(String key_photo) {
		this.key_photo = key_photo;
	}

	public String getKey_photoPath() {
		return key_photoPath;
	}

	public void setKey_photoPath(String key_photoPath) {
		this.key_photoPath = key_photoPath;
	}

	public String getKey_databasename() {
		return key_databasename;
	}

	public void setKey_databasename(String key_databasename) {
		this.key_databasename = key_databasename;
	}

}
