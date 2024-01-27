package models;
import java.sql.Time;

public class TimeSlot {
	
	private int Id;
	private Time StartTime;
	private Time EndTime;
	
	public TimeSlot(int Id, Time StartTime,Time EndTime) {
		this.Id=Id;
		this.StartTime=StartTime;
		this.EndTime=EndTime;
	}
	
	public int GetId() {
		return Id;
	}
	public void setId(int id) {
		this.Id=id;
	}
	public Time GetStartTime() {
		return StartTime;
	}
	public void setStartTime(Time Stime) {
		this.StartTime=Stime;
	}
	public Time GetEndTime() {
		return EndTime;
	}
	public void setEndTime(Time eTime) {
		this.EndTime=eTime;
	}

}
