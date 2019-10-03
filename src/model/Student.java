package model;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Student extends Thread{
	
	//Sem�foro utilizado para simular las acciones del monitor
	private Semaphore S_monitorHelp;
	//Sem�foto utilizado para simular la sala de espera.
	private Semaphore S_waitingRoom;
	//Random utilizado para los tiempos de espera en algunas acciones.
	private Random random;
	
	//Constructor de la clase,
	public Student(Semaphore s_monitorHelp, Semaphore s_waitingRoom, Random random) {
		S_monitorHelp = s_monitorHelp;
		S_waitingRoom = s_waitingRoom;
		this.random = random;
	}
	
	@Override
	public void run() {
		//Mensaje de creaci�n del estudiante.
		System.out.println("Estudiante creado. Se encuentra programando en la sala de c�mputo.");
		while(true) {
			try {
				
				//El hilo duerme entre 30 a 45 segundos.
				//Esto es, un estudiante se demora en pedir la ayuda de un monitor entre 10 a 20 segundos.
				sleep((random.nextInt(16)+30)*1000);
				
				System.out.println("Un estudiante intentar� pedir la ayuda del monitor.");
				
				//Se verifica si el monitor est� ocupado.
				if(S_monitorHelp.availablePermits() == 0) {
					//Si est� ocupado se verifica disponibilidad de sillas en la sala de espera.
					if(S_waitingRoom.availablePermits() == 0) {
						//Monitor ocupado y sin sillas disponibles en sala de espera.
						System.out.println("El monitor est� ocupado y no hay sillas en la sala de espera. "
								+ "El estudiante se devuelve a la sala de c�mputo y pedir� ayuda en otro momento.");						
					}else {
						// El estudiante toma una silla en la sala de espera.
						S_waitingRoom.acquire();
						
						System.out.println("El monitor est� ocupado as� que el estudiante"
								+ " toma una de las sillas de la sala de espera.");
						
						//Mensaje que muestra la cantidad de sillas disponibles una vez el estudiante entra
						//a la sala de espera y toma una.
						System.out.println("Sillas disponibles: " + S_waitingRoom.availablePermits());
						
						//El estudiante espera a que el monitor lo atienda.
						S_monitorHelp.acquire();
						
						//Una vez lo est� atentiendo desocupa la silla.
						S_waitingRoom.release();
						
						System.out.println("Un estudiante entra a ser ayudado por el monitor."); 
						
						//Mensaje que muestra la cantidad de sillas disponibles una vez el estudiante entra
						//a la sala del monitor.
						System.out.println("Sillas disponibles: " + S_waitingRoom.availablePermits());
						
						
						//El hilo duerme entre 15 a 25 segundos.
						//Esto es, el monitor se demora dando la ayuda a un estudiante entre 15 a 25 segundos.
						
						sleep((random.nextInt(11)+15)*1000);
						
						//Una vez solucionadas las dudas al estudiante el monitor se desocupa.
						S_monitorHelp.release();
						
						System.out.println("El monitor termin� de ayudar a un estudiante.");
						
					}
				}else {
					//Si el monitor est� desocupado, se encuentra dormido.
					System.out.println("El monitor est� dormido. Un estudiante lo despierta para pedir ayuda.");
					
					//El estudiante despierta al monitor para pedir ayuda.
					S_monitorHelp.acquire();
					
					System.out.println("Un estudiante entra a ser ayudado por el monitor.");
					
					//El hilo duerme entre 15 a 25 segundos.
					//Esto es, el monitor se demora dando la ayuda a un estudiante entre 15 a 25 segundos.
					
					sleep((random.nextInt(11)+15)*1000);
					
					
					//Una vez el monitor termina de ayudar al estudiante lo libera
					//para que otra persona pueda ser ayudada.
					S_monitorHelp.release();
					
					System.out.println("El monitor termin� de ayudar a un estudiante.");
					
				}
				
				//Si el monitor termin� de ayudar a un estudiante (se desocup�)
				//y no hay nadie en la sala de espera, puede volver a dormir.
				if(S_monitorHelp.availablePermits() == 1 && S_waitingRoom.availablePermits() == 3) {
					System.out.println("El monitor no tiene trabajo. Vuelve a dormir.");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
