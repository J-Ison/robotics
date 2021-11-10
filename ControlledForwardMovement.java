package ForwardMovement;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class ControlledForwardMovement {
	
	//based off of MeasureMoves.java in kinematics lecture 
	
	
	public static void main(String[] args) {
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56).offset(93);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56).offset(-93);
		
		Chassis chassis = new WheeledChassis(new Wheel[]{ wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot rov3r = new MovePilot(chassis);
	
		rov3r.setLinearSpeed(40); // unit: mm / sec
		rov3r.setAngularSpeed(45); // unit: degree/sec
		//rov3r.travel(300); // observe the error
		LCD.drawString("Press ENTER", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		//rov3r.rotate(1080); // observe the error
		//rov3r.arc(100, 90);
		
		float averageVoltage = 0;
		float averageVoltageCount = 0;
		float averageCurrent = 0;
		float averageCurrentCount = 0;
		
		rov3r.travel(1000, true);
		
		
		while (rov3r.isMoving()) {
			averageVoltageCount++;
			averageCurrentCount++;
			averageVoltage += Battery.getVoltage();
			averageCurrent += Battery.getBatteryCurrent();
			
			//System.out.println("Motor Current: " + Battery.getMotorCurrent());
		}
		
		System.out.println("Battery Voltage: " + averageVoltage/averageVoltageCount);
		System.out.println("Battery Current: " + averageCurrent/averageCurrentCount);
		
		Motor.A.close();
		Motor.B.close();
		
	}	

}
