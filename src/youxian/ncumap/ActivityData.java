package youxian.ncumap;


import java.io.Serializable;


public class ActivityData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int ActivityId;
	private String UserName;
	private String UploadTime;
	private String StartTime;
	private String EndTime;
	private String Title;
	private String Address;
	private String Website;
	private String Lat;
	private String Lng;
	private String Context;
	private String Type;
	private byte[]  FileBytes;
	public ActivityData(int ActivityId,
	String UserName,
	String UploadTime,
	String StartTime,
	String EndTime,
	String Title,
	String Address,
	String Website,
	String Lat,
	String Lng,
	String Context,
	String Type,
	byte[]  FileBytes){
		this.ActivityId= ActivityId;
		this.UserName=UserName;
		this.UploadTime=UploadTime;
		this.StartTime=StartTime;
		this.EndTime=EndTime;
		this.Title=Title;
		this.Address=Address;
		this.Website=Website;
		this.Lat=Lat;
		this.Lng=Lng;
		this.Context=Context;
		this.Type=Type;
		this.FileBytes=FileBytes;
	}
	public int getActivityId(){
		return ActivityId;
	}
	public String getUserName(){
		return UserName;
	}
	public String getUploadTime(){
		return UploadTime;
	}
	public String getStartTime(){
		return StartTime;
	}
	public String getEndTime(){
		return EndTime;
	}
	public String getTitle(){
		return Title;
	}
	public String getAddress(){
		return Address;
	}
	public String getWebsite(){
		return Website;
	}
	public String getLat(){
		return Lat;
	}
	public String getLng(){
		return Lng;
	}
	public String getContext(){
		return Context;
	}
	public String getType(){
		return Type;
	}
	public byte[] getFileBytes(){
		return FileBytes;
	}
	public void setFileBytes(byte[] b){
		FileBytes=b;
	}
}