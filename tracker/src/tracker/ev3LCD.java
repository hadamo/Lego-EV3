package tracker;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Ev3LCD {
	/*Código para testar os métodos de desenhar/escrever na tela do ev3
	 * Usar System.Out.Println() é máis fácil.
	 * */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LCD.setAutoRefresh(true);
		LCD.setAutoRefreshPeriod(2000);
		LCD.drawChar('i', 0, 0);
		Delay.msDelay(3000);
		LCD.drawString("Hello MUNDO", 89, 64);
		Delay.msDelay(3000);
		LCD.drawChar('f', 178, 128);
		Delay.msDelay(3000);
		LCD.clear(0);
		
		Button.waitForAnyPress();
//		limpa tela a cada 2s
		
		
//		LCD.setAutoRefresh(false);
		
	}

}
