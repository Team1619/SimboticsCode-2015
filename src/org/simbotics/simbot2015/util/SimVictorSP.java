package org.simbotics.simbot2015.util;

import org.simbotics.simbot2015.io.SensorInput;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimVictorSP extends Victor {
	
	private static double ACCEL_CAP = 0.01;
	private static double ACCEL_VAL = 0.2;
		
	private boolean acceleration = false;
	private double prevOutput = 0.0;
	
	
	public double output;
	
	private SimEncoder enc;
	private SensorInput sensorIn;
	
	
	public SimVictorSP(int channel, boolean acceleration) {
		super(channel);
		this.acceleration = acceleration; 
		SmartDashboard.putNumber("acceleration Cap: ", SimVictorSP.ACCEL_CAP);
		SmartDashboard.putNumber("Accel Jump Value: ", SimVictorSP.ACCEL_VAL);
	}
	
	public SimVictorSP(int channel, boolean acceleration, SimEncoder enc) {
		super(channel);
		this.sensorIn = SensorInput.getInstance();
		this.acceleration = acceleration;
		this.enc = enc;
		SmartDashboard.putNumber("acceleration Cap: ", SimVictorSP.ACCEL_CAP);
		SmartDashboard.putNumber("Accel Jump Value: ", SimVictorSP.ACCEL_VAL);
	}
	
	public SimVictorSP(int channel){
		this(channel,false);
	}
		
	
	public void set(double desiredOutput) {
		output = 0.0;
		
		
		if(!this.acceleration) {
			super.set(desiredOutput);
		} else {
			//double ticksPerMs = enc.speed()/this.sensorIn.getLastTickLength();
			//double speedFraction = ticksPerMs / this.TOP_SPEED_IN_TICKS;
			//super.set(SimLib.limitValue(speed, speedFraction+0.5, speedFraction-0.5));
			
			
			SimVictorSP.ACCEL_CAP = SmartDashboard.getNumber("acceleration Cap: ");
			SimVictorSP.ACCEL_VAL = SmartDashboard.getNumber("Accel Jump Value: ");
			
//			if(this.desiredSpeed > 0 && this.prevOutput < 0 || this.desiredSpeed < 0 && this.prevOutput > 0){ // changed direction 
//				this.output = 0; // jump to 0 to remove some lag
//			}
			/*
			if(Math.abs(this.desiredSpeed) < SimVictorSP.ACCEL_VAL){
				this.output = this.desiredSpeed;
			} else {				
				// direction we are heading
				sign = Math.abs(this.desiredSpeed) / this.desiredSpeed;
				
				if(Math.abs(this.prevOutput) >= this.ACCEL_VAL) {
					this.output += Math.min(SimVictorSP.ACCEL_CAP, Math.abs(this.desiredSpeed - this.output))*this.sign;
				} else {
					this.output = this.ACCEL_VAL*this.sign;
				}
			}*/
			
			double sign = Math.abs(desiredOutput) / desiredOutput;
			
			// less than jump-to val, go right to desired output
			if(Math.abs(desiredOutput) < SimVictorSP.ACCEL_VAL) {
				output = desiredOutput;
			} 
			// was going slower than the jump-to range, jump up
			else if(Math.abs(this.prevOutput) < SimVictorSP.ACCEL_VAL) {
				output = SimVictorSP.ACCEL_VAL * sign;
			} else {
				// output capped by either what we want, or previous + ramp
				output = Math.min(Math.abs(desiredOutput), Math.abs(this.prevOutput) + SimVictorSP.ACCEL_CAP) * sign; 
			}
			
			this.prevOutput = output;
			
			super.set(output);
		}
	}
	
	public void enableAcceleration(boolean acceleration){
		this.acceleration = acceleration;
	}
	
}
