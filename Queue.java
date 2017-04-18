
public interface Queue {
	public void enqueue(Customer x);
	public int dequeue();
	public Customer peek();
	public Customer getTail();
	public Customer getHead();
	public boolean isEmpty();
	public int length();
	
	public static int serviceTime = 0; 
	public static int currentTime = 0; 
	
}
