package org.simbotics.simbot2015.util;

public class SpeedControl {
	private double maxSpeed;
	private double setVal;
	
	private SimPID pid;
		
	public SpeedControl (double max, double p, double i, double d){
		this.maxSpeed = max;
		this.pid = new SimPID(p,i,d);
	}
	
	public void setGoal(double speed){
		this.setVal = speed / maxSpeed;
		this.pid.setDesiredValue(speed);
	}
	
	public double calculate(double speed){
		return this.setVal + this.pid.calcPID(speed);
	}
}	
