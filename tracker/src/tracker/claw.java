package tracker;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Claw {
	public static void main(String[] args) {
		EV3MediumRegulatedMotor claw = new EV3MediumRegulatedMotor(MotorPort.A);
		Sound.beepSequenceUp();
		while(Button.ESCAPE.isUp())
		{
			Button.waitForAnyPress();
			System.out.println("close");
			claw.rotateTo(75, true);
			Button.waitForAnyPress();
			System.out.println("open");
			claw.rotateTo(-75, true);
		}
		
	}

}
