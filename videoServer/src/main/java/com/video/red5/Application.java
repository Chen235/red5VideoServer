package com.video.red5;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.video.common.Constant;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends ApplicationAdapter {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private static Map<String, ConnectionBean> conns = new HashMap<String, ConnectionBean>();

	public boolean appStart(IScope app) {
		logger.info("red5 appStart ！");
		return super.appStart(app);
	}
	public void appStop(IScope app) {
		logger.info("red5 appStop ！");
		super.appStop(app);
	}

	public boolean appJoin(IClient client, IScope scope) {
		logger.info("red5 appJoin ！");
		return super.appJoin(client,scope);
	}

	public boolean appConnect(IConnection conn, Object[] args) {
		logger.info("red5 appConnect ！");
		return true;
	}

	public void appDisconnect(IConnection conn) {
		logger.info("red5 appDisconnect ！");
		super.appDisconnect(conn);
	}

	public boolean connect(IConnection conn, IScope scope, Object[] params) {
		logger.info("red5 connect ！");
		return super.connect(conn,scope,params);
	}

	public Application() {
		init();
	}

	public void init() {
		try {
			timer.schedule(new ConnectTimerTask(this), new Date(),
					Constant.RED5_TIMER_EXCUTE_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		logger.info("red5 streamRecordStart !");
		IConnection connection = Red5.getConnectionLocal();
		if (conns.size() >= Constant.RED5_MAX_CONNECTION) {
			String ip = connection.getRemoteAddress();
			if (conns.get(ip) == null) {
				connection.close();
				this.appDisconnect(connection);
				this.disconnect(connection, connection.getScope());
				return;
			}
		}
		ConnectionBean bean = new ConnectionBean(connection);
		conns.put(connection.getRemoteAddress(), bean);

		super.streamRecordStart(stream);
	}

	public static Map<String, ConnectionBean> getConnection() {
		return conns;
	}

	// 定时器,用于间隔一段时间断开超出设置时间的连接
	private Timer timer = new Timer();

	class ConnectTimerTask extends TimerTask {
		Application application;

		public ConnectTimerTask(Application application) {
			this.application = application;
		}

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			Map<String, ConnectionBean> cs = application.getConnection();
			// 过期时间
			long expired = System.currentTimeMillis()
					- Constant.RED5_MAX_SURVIVE_TIME;
			List<String> keyList = new ArrayList<String>();

			// 断开过期的连接
			for (String ip : cs.keySet()) {
				ConnectionBean bean = cs.get(ip);
				if (bean.getCtime() <= expired) {
					IConnection conn = bean.getiConnection();
					if (conn.isConnected()) {
						conn.close();
					}
					application.appDisconnect(conn);
					logger.debug("Disconnected from " + ip);
					keyList.add(ip);
				}
			}

			// 删除缓存
			for (String ip : keyList) {
				cs.remove(ip);
			}
		}
	}
}
