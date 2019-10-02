package model;

import java.util.concurrent.Semaphore;

public class Monitor extends Thread{
	
	private Semaphore S_monitor;
	private Semaphore S_waitingRoom;
	
	
	
	public Monitor() {
		
	}
	
	@Override
	public void run() {
		while(true) {
			
		}
	}
	
}
