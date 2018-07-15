package com.video.red5;

import org.red5.server.api.IConnection;

public class ConnectionBean {
	private IConnection iConnection;
	private String ip;
	private long ctime;
	
	public ConnectionBean(IConnection iConnection) {
		super();
		this.iConnection = iConnection;
		this.ip = iConnection.getRemoteAddress();
		this.ctime = System.currentTimeMillis();
	}
	public IConnection getiConnection() {
		return iConnection;
	}
	public String getIp() {
		return ip;
	}
	public long getCtime() {
		return ctime;
	}
	public void setiConnection(IConnection iConnection) {
		this.iConnection = iConnection;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	
}
