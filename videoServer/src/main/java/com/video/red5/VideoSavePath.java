package com.video.red5;

import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;

public class VideoSavePath implements IStreamFilenameGenerator {
	private String backAuthScope = "backVideoAuth";
	private String frontAuthScope = "videoAuth";//视频录制应用名称
	private String backAuthBackplayPath;

	private String frontAuthRecordPath;//视频录制文件存放路径
	private String frontAuthBackplayPath;//视频播放文件路径
	private boolean absolutePath;//是否绝对路径

	public String generateFilename(IScope scope, String name,
			GenerationType type) {
		return this.generateFilename(scope, name, null, type);
	}

	public String generateFilename(IScope scope, String name, String extension,
			GenerationType type) {
		String filename = null;
		if (backAuthScope.equals(scope.getName())
				&& type == GenerationType.PLAYBACK) {
			filename = backAuthBackplayPath + "/" + name;
		} else if (frontAuthScope.equals(scope.getName())) {
			if (type == GenerationType.RECORD) {
				filename = frontAuthRecordPath + "/" + name;
			} else {
				filename = frontAuthBackplayPath + "/" + name;
			}
		}
		// 默认
		else {
			filename = "/streams/" + name;
		}

		if (extension != null && !"".equals(extension)) {
			filename = filename + extension;
		}
		return filename;
	}

	public boolean resolvesToAbsolutePath() {
		return absolutePath;
	}

	public String getBackAuthScope() {
		return backAuthScope;
	}

	public void setBackAuthScope(String backAuthScope) {
		this.backAuthScope = backAuthScope;
	}

	public String getFrontAuthScope() {
		return frontAuthScope;
	}

	public void setFrontAuthScope(String frontAuthScope) {
		this.frontAuthScope = frontAuthScope;
	}

	public String getBackAuthBackplayPath() {
		return backAuthBackplayPath;
	}

	public void setBackAuthBackplayPath(String backAuthBackplayPath) {
		this.backAuthBackplayPath = backAuthBackplayPath;
	}

	public String getFrontAuthRecordPath() {
		return frontAuthRecordPath;
	}

	public void setFrontAuthRecordPath(String frontAuthRecordPath) {
		this.frontAuthRecordPath = frontAuthRecordPath;
	}

	public String getFrontAuthBackplayPath() {
		return frontAuthBackplayPath;
	}

	public void setFrontAuthBackplayPath(String frontAuthBackplayPath) {
		this.frontAuthBackplayPath = frontAuthBackplayPath;
	}

	public boolean isAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(boolean absolutePath) {
		this.absolutePath = absolutePath;
	}
}
