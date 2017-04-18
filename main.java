import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		//Service Counter Object
		QueuedLinkedList store = new QueuedLinkedList(); 
		
		//Temporarily feed customers in to store from this object
		QueuedLinkedList customers = new QueuedLinkedList();

		//Permanent storage for customer objects, as they will be dequeued from both lists above 
		Customer[] array = new Customer[12];


		//Read 
		Scanner input = new Scanner(System.in);
		File file = new File(args[0]);
		
		try {
			Scanner inputFile = new Scanner(file);
			int count = 0;
			while (inputFile.hasNextLine()) { // scan through file

				String line = inputFile.nextLine();

				if (line.length() > 0) { // ignore blank lines

					if (count < 1) { // get first line which is service time
						int servtime = Integer.parseInt(line);
						store.setServiceTime(servtime);
					}

					else {

						String line2 = inputFile.nextLine(); //read 2nd line to pair with 1st line read

						// 1. extract ID Number from first line
						int id = 0;
						char[] arr = line.toCharArray();
						if (arr.length == 13) { //
							id = Integer.parseInt(String.valueOf(arr[12]));
						}
						if (arr.length == 14) {
							id = 10 + Integer.parseInt(String.valueOf(arr[13]));
						}

						// 2. extract arrival time from line 2
						int hr = 0;
						int min = 0;
						int sec = 0;
						char[] arr2 = line2.toCharArray();

						if (arr2.length == 22) { // hour is 10, 11, or 12 in
													// nonmilitary time
							hr = 10 + Integer.parseInt(String.valueOf(arr2[15]));
							min = 10 * Integer.parseInt(String.valueOf(arr2[17]))
									+ Integer.parseInt(String.valueOf(arr2[18]));
							sec = 10 * Integer.parseInt(String.valueOf(arr2[20]))
									+ Integer.parseInt(String.valueOf(arr2[21]));
						} else if (arr2.length == 21) { // hour is 1 to 9 in
														// nonmilitary time
							hr = Integer.parseInt(String.valueOf(arr2[14]));
							min = 10 * Integer.parseInt(String.valueOf(arr2[16]))
									+ Integer.parseInt(String.valueOf(arr2[17]));
							sec = 10 * Integer.parseInt(String.valueOf(arr2[19]))
									+ Integer.parseInt(String.valueOf(arr2[20]));
						} else {
							System.err.println("Wrong");
						}

						// 3. Create customer object and add it to the customer list, FIFO
						Customer customer = new Customer(id, hr, min, sec);
						customers.enqueue(customer);

					}

					count++;
				}

			}
			inputFile.close();
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		// go through all the customers and continue until there are no more customers left and the store is empty
		while (customers.length() > 0 && store.length() >= 0) {
			

			//enqueue
			//enqueue if there is no line or if next customer to be queued should be queued before store head is dequeued
			if (store.isEmpty() || store.getHead().getDepartureTime() > customers.getHead().getArrivalTimeinSecond()) {
				//System.out.print("Enqueuing ");
				
				store.enqueue(customers.dequeue()); //FIFO

				//!! Calculate Departure Time of each customer to Determine Wait Time -- 2 Options : 
				//A.) No queue, departure time will be arrival time + Service Time (5 min)
				if (store.length() == 1){
					
					//Pre-Req: Customer arrives BEFORE 9 am and there is no line
					if (store.getHead().getArrivalTimeinSecond() < 9*3600){
						int p = store.getHead().getArrivalTimeinSecond()+store.getServiceTime()+(9*3600-store.getHead().getArrivalTimeinSecond());
						//System.out.printf("Supposed Departure: %d %d:%d:%d \n",p,p/3600,(p/60)%60,p%60);
						store.getHead().setDepartureTime(p);
					}
					//Customer arrives AFTER 9 am and there is no line
					else{
						store.getHead().setDepartureTime(store.getHead().getArrivalTimeinSecond()+store.getServiceTime());
					}
					
					//int y = store.getTail().getDepartureTime();
					//System.out.printf("Departure Time: %d  %d:%d:%d \n",y, y/3600,(y/60)%60,y%60);
				}
				//B.) There is a queue, departure time will be Departure Time of Person Before them + Service Time (5 min)
				else { 
					
					//not needed, ignore this
					if (store.getSecondToLastInLine() == null){
						
					}
					//Pre-Condition: Customer arrives before 9 am and there IS a line
					if (store.getTail().getArrivalTimeinSecond() < 9*3600){
						store.getTail().setDepartureTime(store.getSecondToLastInLine().getDepartureTime()+store.getServiceTime());
					}
					//Customer arrives after 9 am and there IS a line
					else{
						store.getTail().setDepartureTime(store.getSecondToLastInLine().getDepartureTime()+store.getServiceTime());
					}
					//int y = store.getTail().getDepartureTime();
					//System.out.printf("Departure Time: %d:%d:%d \n", y/3600,(y/60)%60,y%60);
				}
				
				//!! Calculate Break-Time To See Longest Break Time and Total Idle Time 
				//if Bill arrives after Anne leaves, there is break time 
				if (customers.length() != 0 && !(customers.getHead().getArrivalTimeinSecond()<store.getTail().getDepartureTime())){
					int brk = customers.getHead().getArrivalTimeinSecond() - store.getTail().getDepartureTime();
					if (brk > store.getLongestBreak()){
						store.setLongestBreak(brk);
					}
					store.setTotalIdleTime(store.getTotalIdleTime()+brk);
				}
				
						
			} 
			
			//dequeue
			//dequeuewithoutservice
			else {
				//System.out.print("Dequeueing ");
				array[store.getHead().getID()-1] = store.getHead(); //copy to array of customers
				//Customer arrives after 5 pm, dismiss them without service 
				if(store.getHead().getArrivalTimeinSecond()>17*3600){
					store.dequeueWithoutService();
				}
				//Customer arrives before 5 pm, but doesn't get to be served until after 5 pm, dismiss them without service
				else if (store.getHead().getDepartureTime()>17*3600+store.getServiceTime()){
					store.dequeueWithoutService();
				}
				else{
					store.dequeue();
				}
			}
		

		
		}
		
		//Deal with Stragglers separately
		//get remaining people in line out (~5 pm people)
		while (store.length()>0){
			//System.out.print("Dequeueing ");
			int x = store.getHead().getDepartureTime();
			array[store.getHead().getID()-1] = store.getHead();

			//System.out.printf("%d:%d:%d \n", x/3600,(x/60)%60,x%60);
			//Customer arrives after 5 pm, dismiss them without service 
			if(store.getHead().getArrivalTimeinSecond()>17*3600){
				store.dequeueWithoutService();
			}
			//Customer arrives before 5 pm, but doesn't get to be served until after 5 pm, dismiss them without service
			else if (store.getHead().getDepartureTime()>(17*3600+store.getServiceTime())){
				store.dequeueWithoutService();
			}
			else{
				store.dequeue();
			}
		}
		
		
		
		//initialize waiting time of each customer
		//Waiting Time = Departure Time - Arrival Time - Service Time
		for (Customer c : array){
			c.setWaitTime(c.getDepartureTime()-c.getArrivalTimeinSecond()-store.getServiceTime());
		}
		
		
		
		//Ready to write all data to file 
		File file2 = new File(args[1]);
	
		
		int[] numID = new int[4];
		int ii = 0;
	    try {
	    	//Read Querie to understand what it is asking
	    	Scanner inp = new Scanner(file2);
			int counter=0; 
			while(inp.hasNextLine()){
				//assume first 4 lines are always the same queries
				if(counter < 4){
					inp.nextLine();
				}
				else {
					String ln = inp.nextLine();
					char[] fill = ln.toCharArray();
					if(fill[17]==':'){
						numID[ii]= Integer.parseInt(String.valueOf(fill[16]));
					} else{
						numID[ii]= Integer.parseInt(String.valueOf(fill[17])) + 10;
					}
					ii++;
				}
				
				counter++;
			}
			
			
			
			
	    	PrintWriter myWriter = new PrintWriter(file2);
	    	Scanner input1 = new Scanner(file2);
	        // put this String into our file
	        myWriter.printf("NUMBER-OF-CUSTOMERS-SERVED: %d\n",store.getCustomersServed());
	        myWriter.printf("LONGEST-BREAK-LENGTH: %d\n",store.getLongestBreak());
	        myWriter.printf("TOTAL-IDLE-TIME: %d\n",store.getTotalIdleTime());
	        myWriter.printf("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: %d\n",store.getLongestLineLength());
	        myWriter.printf("WAITING-TIME-OF %d: %d\n",numID[0],array[numID[0]-1].getWaitTime());
	        myWriter.printf("WAITING-TIME-OF %d: %d\n",numID[1],array[numID[1]-1].getWaitTime());
	        myWriter.printf("WAITING-TIME-OF %d: %d\n",numID[2],array[numID[2]-1].getWaitTime());
	        myWriter.printf("WAITING-TIME-OF %d: %d\n",numID[3],array[numID[3]-1].getWaitTime());
	        
	        myWriter.close();
	        
	        //print results 
	        while(input1.hasNextLine()){
	        	String line = input1.nextLine();
	        	System.out.println(line);
	        }
	    }
	    catch (FileNotFoundException e) {
	      System.out.println("Issue opening the file in question!");
	    }
	    
	    
	  
		
		

	}

}
