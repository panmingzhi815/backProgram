package com.donglucard;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.donglucard.domain.OldUser;

public class DatabaseConnector {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);
	
	public static List<OldUser> findAllOldUser(String ip,String port,String username,String password,String databasename) throws Exception{
		LOGGER.debug("数据库参数：ip={},port={},username={},password={},databasename={}",ip,port,username,password,databasename);
		List<OldUser> result = new ArrayList<OldUser>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet executeQuery = null;
		try{
			AppConfigrator appConfigrator = new AppConfigrator();
			appConfigrator.loadProperties();
			
			String url = String.format("jdbc:jtds:sqlserver://%s:%s;DatabaseName=%s",ip,port,databasename);
			connection = DriverManager.getConnection(url,username,password);
			String sql = String.format("select %s,%s from %s", appConfigrator.getKey_userIdentifier(),appConfigrator.getKey_photo(),appConfigrator.getKey_tablename());
			LOGGER.debug("execute sql : {}",sql);
			prepareStatement = connection.prepareStatement(sql);
			ResultSet resultSet = prepareStatement.executeQuery();
			while(resultSet!= null && resultSet.next()){
				String userIdentifier = resultSet.getString(appConfigrator.getKey_userIdentifier());
				String photo = resultSet.getString(appConfigrator.getKey_photo());
				OldUser oldUser = new OldUser();
				oldUser.setUserIdentifier(userIdentifier);
				oldUser.setPhoto(photo);
				result.add(oldUser);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(connection != null){
				try {connection.close();} catch (SQLException e) {}
			}
			if(prepareStatement != null){
				try {prepareStatement.close();} catch (SQLException e) {}
			}
			if(executeQuery != null){
				try {executeQuery.close();} catch (SQLException e) {}
			}
		}
		return result;
	}
	
	public static void updateUserImage(String ip,String port,String username,String password,String databasename,List<OldUser> oldUsers) throws SQLException{
		LOGGER.debug("数据库参数：ip={},port={},username={},password={},databasename={}",ip,port,username,password,databasename);
		String sql = "update CardUser set headImage=? where identifier = ?";
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try{
			AppConfigrator appConfigrator = new AppConfigrator();
			appConfigrator.loadProperties();
			
			String url = String.format("jdbc:jtds:sqlserver://%s:%s;DatabaseName=%s",ip,port,databasename);
			connection = DriverManager.getConnection(url,username,password);
			for (OldUser oldUser : oldUsers) {
				LOGGER.debug("正在处理：用户编号:{},图片地址:{}",oldUser.getUserIdentifier(),oldUser.getPhoto());
				Path path = Paths.get(appConfigrator.getKey_photoPath() + oldUser.getPhoto());
				if(Files.exists(path)){
					try{
						LOGGER.debug("execute sql : {}",sql);
						prepareStatement = connection.prepareStatement(sql);
						prepareStatement.setBytes(1,Files.readAllBytes(path));
						prepareStatement.setString(2, oldUser.getUserIdentifier());
						prepareStatement.executeUpdate();
						LOGGER.info("更新数据：用户编号:{},图片地址:{}",oldUser.getUserIdentifier(),path.toFile().getAbsolutePath());
					}catch (Exception e) {
						LOGGER.error("更新数据失败,用户编号:{}",oldUser.getUserIdentifier());
						LOGGER.error("更新数据失败",e);
					}finally{
						if(prepareStatement != null){
							try {prepareStatement.close();} catch (SQLException e) {}
						}
					}
				}else{
					LOGGER.debug("图片文件不存在：用户编号:{},图片地址:{}",oldUser.getUserIdentifier(),oldUser.getPhoto());
				}
			}
		} catch (SQLException e1) {
			throw e1;
		}finally{
			if(connection != null){
				try {connection.close();} catch (SQLException e) {}
			}
			
		}
	}
	
}
