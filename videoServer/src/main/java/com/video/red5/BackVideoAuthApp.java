package com.video.red5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;

public class BackVideoAuthApp extends ApplicationAdapter {
	public static final Log logger = LogFactory.getLog(BackVideoAuthApp.class);
	public boolean appConnect(IConnection conn,Object[] args) {
		logger.debug("Client Connecting!");
		return true;
	}
}
