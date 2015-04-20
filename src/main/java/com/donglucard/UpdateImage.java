package com.donglucard;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.core.util.TimeUtil;
import com.donglucard.domain.OldUser;

public class UpdateImage {

	public static void main(String[] args) throws Exception {
		AppConfigrator appConfigrator = new AppConfigrator();
		appConfigrator.loadProperties();
		
		String ip = appConfigrator.getKey_sqlserver_ip();
		String port = appConfigrator.getKey_sqlserver_port();
		String username = appConfigrator.getKey_username();
		String password = appConfigrator.getKey_password();
		String databasename = appConfigrator.getKey_databasename();
		
		List<OldUser> findAllOldUser = DatabaseConnector.findAllOldUser(ip, port, username, password, databasename);
		DatabaseConnector.updateUserImage(ip, port, username, password, "onecard", findAllOldUser);
		TimeUnit.SECONDS.sleep(3);
	}
	
}
