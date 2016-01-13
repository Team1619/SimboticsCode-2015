package org.simbotics.simbot2015.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimAccelCap {

	double prevOutput = 0.0;
	
	public double calculate(double desiredOutput){
		return calculate(desiredOutput, false);
	}
	
	
	
	
	
	public double calculate(double desiredOutput, boolean goFaster) {
		double output = 0.0;
		double accelCap; 
		double accelVal;
		if(goFaster){
			accelCap = SmartDashboard.getNumber("acceleration Cap: ");
			accelVal = 0.5;
		}else{
			accelCap = SmartDashboard.getNumber("acceleration Cap: ");
			accelVal = SmartDashboard.getNumber("Accel Jump Value: ");
		}
		double sign = Math.abs(desiredOutput) / desiredOutput;
		
		// less than jump-to val, go right to desired output
		if(Math.abs(desiredOutput) < accelVal) {
			output = desiredOutput;
		} 
		// was going slower than the jump-to range, jump up
		else if(Math.abs(this.prevOutput) < accelVal) {
			output = accelVal * sign;
		} else {
			// output capped by either what we want, or previous + ramp
			output = Math.min(Math.abs(desiredOutput), Math.abs(this.prevOutput) + accelCap) * sign; 
		}
		
		this.prevOutput = output;
		
		return output;
	}
	
}
