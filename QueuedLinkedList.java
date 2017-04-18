//Linked List class to make Linked List object of Customer Nodes that will be in a Queue format 
public class QueuedLinkedList {
	
	private int customersServed;
	private int totalIdleTime;
	private int longestBreak; 
	private int longestLineLength; 
	private int serviceTime; 

	private Customer tail; // end of queue
	private Customer head; // front of queue
	private int total;
	
	public QueuedLinkedList() {
		this.tail = null;
		this.head = null;
		this.customersServed = 0; 
		this.totalIdleTime =0;
		this.longestBreak = 0; 
		this.longestLineLength = 0; 
		this.serviceTime = 0; 
		
		this.total = 0; 
	}
	
	public boolean isEmpty(){
		return (head == null ? true : false) ;
	}

	public void enqueue(Customer x) { // add at end of queue
		Customer current = tail;
		tail = x;
		total++;
		if (current != null) {
			if (total == 1 )
				head = tail;
			else
				current.setNext(tail);
		} else {
			head = tail;
		}
		
		//System.out.println(x.getID());
		//store longest length of line 
		if (this.length() > this.longestLineLength){
			this.longestLineLength = length() - 1;
		}
		
	}

	public Customer dequeue() { // serves customer in front of line, dequeues them
		Customer current = head; 
		//System.out.println(current.getID());
		if (total == 0)
			throw new java.util.NoSuchElementException();
		head = head.getNext();
		if (--total == 0)
			tail = null;
		customersServed ++; 
		
		
		return current;
	}
	public Customer dequeueWithoutService() { // customer arrives after 5 pm, dequeues them and dismisses them
		Customer current = head; 
		//System.out.println("NO SERVICE "+current.getID());
		if (total == 0)
			throw new java.util.NoSuchElementException();
		head = head.getNext();
		if (--total == 1)
			tail = null;
		
		return current; 
	}
	
	public int length(){
		if (head == null) return 0; 
		return this.total;
	}

	public Customer peek() {
		return this.head;
	}

	public Customer getTail() {
		return this.tail;
	}

	public Customer getHead() {
		return this.head;
	}
	
	public Customer getSecondToLastInLine(){
		Customer current = head; 
		while(current!= null){
			if (current.getNext() == tail){
				return current; 
			}
			current = current.getNext();
		}
		return null;
	}
	public void setServiceTime(int x){
		this.serviceTime = x; 
	}
	public int getServiceTime(){
		return this.serviceTime;
	}
	
	public void setCustomersServed(int customersServed) {
		this.customersServed = customersServed;
	}
	public void setTotalIdleTime(int totalIdleTime) {
		this.totalIdleTime = totalIdleTime;
	}
	public void setLongestBreak(int longestBreak) {
		this.longestBreak = longestBreak;
	}
	public void setLongestLineLength(int longestLineLength) {
		this.longestLineLength = longestLineLength;
	}
	
	public void setTail(Customer tail) {
		this.tail = tail;
	}
	public void setHead(Customer head) {
		this.head = head;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCustomersServed() {
		return customersServed;
	}
	public int getTotalIdleTime() {
		return totalIdleTime;
	}
	public int getLongestBreak() {
		return longestBreak;
	}
	public int getLongestLineLength() {
		return longestLineLength;
	}
	public int getTotal() {
		return total;
	}
	

}
