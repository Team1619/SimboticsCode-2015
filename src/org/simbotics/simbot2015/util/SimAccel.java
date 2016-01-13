package org.simbotics.simbot2015.util;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimAccel extends BuiltInAccelerometer {
	
	private double xPos;
	private double xVel;
	private double xAcc;
	
	private double yPos;
	private double yVel;	
	private double yAcc;
	
	public SimAccel() {
		super(Range.k8G);
		this.xPos = 0;
		this.yPos = 0;
	
		this.xVel = 0;
		this.yVel = 0;
		
		this.xAcc = 0;
		this.yAcc = 0;
	}
	
	public void update() {
		this.xVel += super.getX();
		this.yVel += super.getY();
		
		this.xPos += this.xVel;
		this.yPos += this.yVel;

//		SmartDashboard.putNumber("Accel: xPos", this.xPos );
//		SmartDashboard.putNumber("Accel: yPos", this.yPos );
//		SmartDashboard.putNumber("Accel: xVel", this.xVel );
//		SmartDashboard.putNumber("Accel: yVel", this.yVel );
//		SmartDashboard.putNumber("Accel: xAcc", this.xAcc );
//		SmartDashboard.putNumber("Accel: yAcc", this.yAcc );
	}
	
	public Vect getPosition() {
		return new Vect(this.xPos, this.yPos);
	}
	
	public Vect getDeltaPosition() {
		return new Vect(this.xVel, this.yVel);
	}
	
	public double getXPos() {
		return this.xPos;
	}
	
	public double getYPos() {
		return this.yPos;
	}
	
}
