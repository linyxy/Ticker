package com.earth.ticker.assist;

public class TasksInfo {
	public String taskName=null;
	public String taskSign=null;
	public int taskImage=0;
	public String folderInfo=null;
	public long taskId=0;
	
	public TasksInfo(String taskName, String taskSign, int taskImage,
			String folderInfo) {
		super();
		this.taskName = taskName;
		this.taskSign = taskSign;
		this.taskImage = taskImage;
		this.folderInfo = folderInfo;
	} 
	
	/**
	 * new standard method to create TaskInfo
	 * this method will store the eventId(taskId)
	 * eventId will be used for future use
	 * @param taskName
	 * @param taskSign
	 * @param taskImage
	 * @param folderInfo
	 * @param taskId
	 */
	public TasksInfo(String taskName, String taskSign, int taskImage,
			String folderInfo, long taskId) {
		super();
		this.taskName = taskName;
		this.taskSign = taskSign;
		this.taskImage = taskImage;
		this.folderInfo = folderInfo;
		this.taskId = taskId;
	}
	

}
