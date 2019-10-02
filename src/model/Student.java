package model;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Student extends Thread{
	
	private Semaphore S_monitor;
	private Semaphore S_waitingRoom;
	private String name;
	private Random random;
	
	public Student() {
		
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("El estudiante " + name + " est� programando en la sala de c�mputo.");
				sleep((random.nextInt(6)+5)*1000);
				System.out.println("El estudiante intentar� pedir la ayuda del monitor.");
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
