package tracker;

import java.util.Arrays;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TrackerLight {
	//motores
	public UnregulatedMotor dir, esq;
	//public EV3MediumRegulatedMotor helice;
	//sensores e recursos
	public EV3ColorSensor sensor;
	public SensorMode sp;
	public float[] sample;
	
	
	public TrackerLight()
	{
		dir = new UnregulatedMotor(MotorPort.C);
		esq = new UnregulatedMotor(MotorPort.B);
		//helice = new EV3MediumRegulatedMotor(MotorPort.A);
		sensor = new EV3ColorSensor(SensorPort.S1);
		
	}
	public Boolean isMoving()
	{
		return this.dir.isMoving()&&this.esq.isMoving();
	}
	//robo possui o sensor como frente igual ao trackercollision
	public void setMotorsEqualPower(int power)
	{
		this.dir.setPower(power);
		this.esq.setPower(power);
	}
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
		//this.helice.stop();
	}
	public void turnRight()
	{
		this.dir.stop();
		this.esq.forward();
	}
	public void turnLeft()
	{
		this.esq.stop();
		this.dir.forward();
	}
	//fecha portas
	public void trackerOff()
	{
		this.dir.close();
		this.esq.close();
		//this.helice.close();
		this.sensor.close();
	}
	/*
	 * metodo que inicia o recebimento e processamento de amostras do sensor*/
	public int getSample()
	{
		this.sp = this.sensor.getColorIDMode();
		this.sample = new float[sp.sampleSize()];
        this.sensor.fetchSample(sample, 0);
        return (int) sample[0];
        /*
		 * -1 nenhum 
		 * 0 vermelho
		 * 1 verde
		 * 2 azul 
		 * 3 amarelo
		 * 4 magenta 
		 * 5 laranja 
		 * 6 branco 
		 * 7 preto 
		 * 8 rosa 
		 * 9 cinza
		 * 10 cinza claro
		 * 11 cinza escuro 
		 * 12 cian 
		 * 13 marrom 
		 */
	}
	public static void main(String[] args) 
	{
		TrackerLight rb = new TrackerLight();
		SensorMode sp;
		float[] sample;
		int cor;

		while (Button.ESCAPE.isUp())
        {
		  Button.waitForAnyPress();	
           cor = rb.getSample();
           if(cor == 7) {
        	   rb.setMotorsEqualPower(30);
        	   rb.setMotorsForward();
           }
           if(cor == 0) {
        	   rb.stop();
           }
           if(cor == 2) {
        	   rb.turnRight();
           }
           if(cor == 6) {
        	   rb.turnLeft();
           }
        }
		rb.stop();
		rb.trackerOff();

	}
}