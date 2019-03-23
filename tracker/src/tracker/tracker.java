package tracker;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;


public class Tracker 
{
	//motores
	public UnregulatedMotor dir, esq;
	public EV3MediumRegulatedMotor claw;
	//sensores e seus recursos necessarios
	public EV3IRSensor sensor;
    
	public Tracker()
	{
		dir = new UnregulatedMotor(MotorPort.C);
		esq = new UnregulatedMotor(MotorPort.B);
		claw = new EV3MediumRegulatedMotor(MotorPort.A);
		sensor = new EV3IRSensor(SensorPort.S4);
		
	}
	public Boolean isMoving()
	{
		return this.dir.isMoving()&&this.esq.isMoving();
	}
	//define potencia igual para os dois motores das rodas
	public void setMotorsEqualPower(int power)
	{
		this.dir.setPower(power);
		this.esq.setPower(power);
	}
	//metodos abaixo definem movimento das rodas para frente ou para trás
	public void setMotorsForward()
	{
		this.dir.forward();
		this.esq.forward();
	}
	public void setMotorsBackward()
	{
		this.dir.backward();
		this.esq.backward();
	}
	//para os motores
	public void stop()
	{
		this.dir.stop();
		this.esq.stop();
	}
	//move para direita
	public void turnRight()
	{
		this.dir.setPower(50);
		this.esq.setPower(-50);
		this.setMotorsForward();
	}
	//move para esquerda
	public void turnLeft()
	{
		this.dir.setPower(-50);
		this.esq.setPower(50);
		this.setMotorsForward();
	}
	//fecha as portas abertas
	public void trackerOff()
	{
		this.dir.close();
		this.esq.close();
		this.claw.close();
		this.sensor.close();
	}
/*
 * Robo com duas trilhas para movimento e uma garra na frente
 *  a frente do robô é onde está a garra!
 */
	public static void main(String[] args) 
	{
		System.out.println("\n \n \n Iniciando \n \n \n");
		int controle,power = 30;
		Tracker rb = new Tracker();
		Sound.beepSequenceUp();
		while(Button.ESCAPE.isUp())
		{
			// para o controle remoto não é preciso tratar amostras
			//apenas receber o comando do controle no canal dado como argumento
			controle = rb.sensor.getRemoteCommand(0);
			if(controle == 1)  //TOP-LEFT
			{
				System.out.println("vai reto!");
				rb.setMotorsEqualPower(power);
				rb.setMotorsForward();
			}if(controle==2) //BOTTOM-LEFT
			{
				System.out.println("vai de rÃ©!");
				//rb.setMotorsEqualPower(30);
				rb.setMotorsBackward();
			}
			if(controle==3) //TOP-RIGHT
				System.out.println("DIREITA");
				rb.turnRight();
			if(controle==4) //BOTTOM-RIGHT
				System.out.println("Esquerda");
				rb.turnLeft();
			if(controle==5)//TOP-LEFT + TOP-RIGHT
			{
				System.out.println("segura");
				Sound.beepSequenceUp();
				rb.claw.rotateTo(75, true);
			}
			if(controle==6) //TOP-LEFT + BOTTOM-RIGHT
			{
				System.out.println("solta");
				Sound.beepSequence();
				rb.claw.rotateTo(-75, true);
			}
			if(controle == 9) //BOTTOM-LEFT + BOTTOM-RIGHT
			{
				System.out.println("para");
				rb.stop();
			}
		}
		rb.stop();
		Sound.beep();
		System.out.println("Bye!");
		rb.trackerOff();
	}

}





/* Abaixo parte de código para seguir o controle remoto.
 * não funciona bem pois o raio de detecção é muito curto e controle precisa
 * estar na linha de visão do sensor.
 * SensorMode seguir = rb.sensor.getSeekMode();
		float[] beacon = new float[seguir.sampleSize()];	
		seguir.fetchSample(beacon, 1);
		int direction;
		int distance = (int) beacon[1];
		
		rb.setMotorsEqualPower(80);
		rb.setMotorsFoward();
		Delay.msDelay(2000);
		while(distance > 0) 
		{
			seguir.fetchSample(beacon, 1);
			direction = (int) beacon[0];
			distance = (int) beacon[1];
			Delay.msDelay(2000);
			if(direction > 0)
			{
				rb.turnLeft();
				Delay.msDelay(2000);
			}else if (direction <0)
			{
				rb.turnRight();
				Delay.msDelay(2000);
			}else
			{
				rb.setMotorsFoward();
				Delay.msDelay(2000);
			}
		}
 */
