//Customer class to make customer node objects to be stored in linked list
public class Customer {
	private int waitTime; 
	private int ID; 
	
	//to store arrival time 
	private int hour;
	private int minute; 
	private int second; 
	private int arrivalTime; 
	private Customer next; 
	private Customer previous;
	private int departureTime; 

	public Customer(){
		this.waitTime = 0; 
		this.ID = 0; 
	}
	public Customer(int ID, int hour, int minute, int second ){
		this.ID = ID; 
		if (hour > 0 && hour < 6){  //convert to military time
			this.hour = hour + 12;
		} else{
			this.hour = hour; 
		}
		this.minute = minute; 
		this.second = second; 
		this.waitTime = 0; 
		this.next = null;
		this.previous = null;
		this.arrivalTime = this.hour*3600 + this.minute*60 + this.second;
		this.departureTime = this.arrivalTime + 300;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
	
	public void getTime(){
		System.out.printf("Arrival Time: %d:%d:%d",this.hour,this.minute,this.second);
	}
	
	public int getArrivalTimeinSecond(){
		return this.arrivalTime;
	}
	
	public int getDepartureTime(){
		return this.departureTime; 
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getSecond() {
		return second;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public int getID() {
		return ID;
	}
	public void setNext(Customer next) {
		this.next = next;
	}
	public void setPrevious(Customer previous) {
		this.previous = previous;
	}
	public Customer getNext() {
		return next;
	}
	public Customer getPrevious() {
		return previous;
	}
	
	

	
}
