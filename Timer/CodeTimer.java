package Timer;

public class CodeTimer {

	private long executionTime;
	public CodeTimer(){
		this.executionTime=0;
	}
	public void startTimer(){
		this.executionTime =System.currentTimeMillis() / 1000;
	}
	public void stopTimer(){
		this.executionTime = (System.currentTimeMillis() / 1000) - this.executionTime;
	}
	public void resetTimer(){
		this.executionTime=0;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public String toString(){
		String desc = "Current execution time (s) :" + this.executionTime;
		return desc;
	}
	
	
}
