package tracker;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TrackerCollision {
	//motores
	public UnregulatedMotor dir, esq;
	public EV3MediumRegulatedMotor claw;
	//sensores e seus recursos necessarios
	public EV3TouchSensor sensor;
	public SampleProvider sp;
	public float[] sample;

	public TrackerCollision()
	{
		dir = new UnregulatedMotor(MotorPort.C);
		esq = new UnregulatedMotor(MotorPort.B);
		claw = new EV3MediumRegulatedMotor(MotorPort.A);
		sensor = new EV3TouchSensor(SensorPort.S4);
		
	}
	public Boolean isMoving()
	{
		return this.dir.isMoving()&&this.esq.isMoving();
	}
	public void setMotorsEqualPower(int power)
	{
		this.dir.setPower(power);
		this.esq.setPower(power);
	}
	//como a frente do robo agora eh o sensor e nao a garra, os movimentos foram
	//invertidos com relacao a classe tracker 
	public void setMotorsForward()
	{
		this.dir.backward();
		this.esq.backward();		
	}
	public void setMotorsBackward()
	{
		this.dir.forward();
		this.esq.forward();

	}
	public void stop()
	{
		this.dir.stop();
		this.esq.stop();
//		this.helice.stop();
	}
	public void turnRight()
	{
		this.dir.setPower(-50);
		this.esq.setPower(50);
		//this.setMotorsForward();
	}
	public void turnLeft()
	{
		this.dir.setPower(50);
		this.esq.setPower(-50);
		this.setMotorsForward();
	}
	public void trackerOff()
	{
		this.dir.close();
		this.esq.close();
		this.claw.close();
		this.sensor.close();
	}
	 /* metodo que inicia o recebimento e processamento de amostras do sensor
	  * de toque
	  * */
	public boolean isPressed()
	{
		boolean collision = false;
		//o SampleProvider recebe o retorno do método que ativa o modo de operação do sensor
		this.sp = sensor.getTouchMode();
		//o vetor de amostras é iniciado com o tamanho das amostras recebidas nesse momento
		this.sample = new float[this.sp.sampleSize()];
		// abaixo metodo que converte as amostras recebidas para float e com offset 0
		this.sp.fetchSample(sample, 0);
		int touch = (int) sample[0];
		if(touch == 1) collision = true;
		return collision;
	}
	/*
	 * Robo que anda reto, detecta ate duas colisoes e vai pra direita
	 * A frente do robo eh o sensor de toque, portanto ele anda inverso ao tracker
	 * comum
	 * */
	public static void main(String[] args) 
	{
//		System.out.println("\n \n \n Pressione qualquer tecla \n \n \n");
		int hit=0,power = 40;
		TrackerCollision rb = new TrackerCollision();
		
//		Button.waitForAnyPress();
		System.out.println("\n \n \n Iniciando \n \n \n");
		//Ao chamar metodos ja definidos de Sound
		Sound.beepSequenceUp();
		rb.setMotorsEqualPower(power);
		rb.setMotorsForward();		
		while(hit < 2 && Button.ESCAPE.isUp())
		{
			if(rb.isPressed())
			{
				Sound.beep();
				System.out.println("Bateu! ");
				rb.stop();
				//Delay de 3s, EV3 espera 3s para executar a proxima instrucao
				//enquanto mantem a atual caso esta seja continua.
				Delay.msDelay(3000);
				Sound.beepSequence();
				System.out.println("Dando re agora");
				rb.setMotorsBackward();
				Delay.msDelay(3000);
				Sound.beep();
				System.out.println("Manobrando para direita!");
				rb.turnRight();
				//ao esperar 1s enquanto vira para direita pode-se virar aprox 90 graus
				//sem utilizar o gyro-sensor
				Delay.msDelay(1000);
				rb.stop();
				System.out.println("Avancando!");
				Delay.msDelay(2000);
				Sound.beepSequenceUp();
				rb.setMotorsEqualPower(power);
				rb.setMotorsForward();
				hit++;
				Sound.beep();
			}
			
		}
		rb.trackerOff();
	}
}
