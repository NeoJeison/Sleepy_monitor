package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			//Semáforo utilizado para simular las acciones del monitor, sólo un permiso pues el monitor atiende un
			//estudiante a la vez.
			Semaphore S_monitor = new Semaphore(1, true);
			//Semáfoto utilizado para simular la sala de espera, cuenta con 3 permisos, uno por cada silla.
			Semaphore S_waitingRoom = new Semaphore(3, true);
			
			//Random utilizado para los tiempos de espera en algunas acciones.
			long seed = 257L;
			Random random = new Random(seed);
			
			//Se realiza la lectura de la cantidad n de estudiantes que se desean para el programa.
			System.out.println("Por favor ingrese el número de estudiantes");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			boolean isInt = false;
			int numStudents = 0;
			while(!isInt) {
				try {
					numStudents = Integer.parseInt(br.readLine());
					isInt = true;
				}catch(Exception e) {
					System.out.println("Por favor ingrese un valor válido");
				}
			}
			
			//Se inicializa la ista de estudiantes.
			ArrayList<Student> students = new ArrayList<Student>();
			
			//Se crean los n estudiantes.
			for(int i = 0; i < numStudents; i++) {
				Student student = new Student(S_monitor, S_waitingRoom, random);
				students.add(student);
			}
			
			//Se inicializan los hilos estudiantes.
			for (int i = 0; i < numStudents; i++) {
				students.get(i).start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
