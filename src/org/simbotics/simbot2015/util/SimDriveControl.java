package org.simbotics.simbot2015.util;

import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimDriveControl {
	
	private RobotOutput robotOut;
	private SensorInput sensorIn;
	
	private Vect leftVect;
	private Vect rightVect;
	private Vect backVect;
	
	private double pVal = 0.02;
	private double iVal = 0.0;
	private double dVal = 0.1;
	
	private double turningSpeed; 
	
	private SimPID gyroControl;
	private boolean setAngle;
	
	private boolean inAuto = false;
	private double turningMagnitude = 0.0;
	private boolean setAngleFlag = false;
	
	public SimDriveMode driveMode = SimDriveMode.GYRO;

	
	public SimDriveControl() {
		
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
		
		this.leftVect = Vect.fromAngle(150);
		this.rightVect = Vect.fromAngle(30);
		this.backVect = Vect.fromAngle(270);
		
		this.pVal = SmartDashboard.getNumber("Gyro P: ");
		this.iVal = SmartDashboard.getNumber("Gyro I: ");
		this.dVal = SmartDashboard.getNumber("Gyro D: ");
		
		//this.gyroControl = new SimPID(0.02,0,0.1); 
		if(!this.inAuto){
			this.gyroControl = new SimPID(this.pVal, this.iVal, this.dVal);
			this.gyroControl.setDesiredValue(sensorIn.getAngle());
			this.setAngle = true;
		}else{
			this.gyroControl = new SimPID(0.015, this.iVal, this.dVal);
			this.gyroControl.setDesiredValue(sensorIn.getAngle());
			this.setAngle = true;
		}
	}
	
	public void setDriveMode(SimDriveMode mode){
		this.driveMode = mode;
	}
	
	
	public void drive(Vect translate, double rotate) {
		if(this.driveMode == SimDriveMode.OUTPUT){
			this.outputDrive(translate, rotate);
			System.out.println("Output Mode");
		} else if(this.driveMode == SimDriveMode.GYRO){
			this.gyroDriveOutputTurning(translate, rotate);
			System.out.println("Gyro Mode");
			//this.gyroDrive(translate, rotate);
		}
	}
	
	
	public void outputDrive(Vect translate, double rotate) {
		this.turningSpeed = SmartDashboard.getNumber("Turning Speed: ");
		double leftOutput = translate.scalarProjectOnto(this.leftVect) + rotate*(this.turningSpeed);
		double rightOutput = translate.scalarProjectOnto(this.rightVect) + rotate*(this.turningSpeed);
		double backOutput = translate.scalarProjectOnto(this.backVect) + rotate*(this.turningSpeed);
											
		this.robotOut.setDriveLeft(leftOutput);
		this.robotOut.setDriveRight(rightOutput);
		this.robotOut.setDriveBack(backOutput);
											
		SmartDashboard.putNumber("L Out: ", leftOutput);
		SmartDashboard.putNumber("R Out: ", rightOutput);
		SmartDashboard.putNumber("B Out: ", backOutput);
		
	}
	
	
	private void gyroDrive(Vect translate, double rotation){
		this.pVal = SmartDashboard.getNumber("Gyro P: ");
		this.iVal = SmartDashboard.getNumber("Gyro I: ");
		this.dVal = SmartDashboard.getNumber("Gyro D: ");
		this.turningMagnitude = SmartDashboard.getNumber("Turning Magnitude: ");
		
		if(!this.inAuto){
			this.gyroControl.setConstants(this.pVal, this.iVal, this.dVal);
		}else{
			this.gyroControl.setConstants(this.pVal, this.iVal, this.dVal); // P was hard coded at 0.04 by mistake 
		}
        if(setAngle){
            gyroControl.setDesiredValue(sensorIn.getAngle());
            setAngle = false;
        } else if (Math.abs(rotation) > 0.02) {
            gyroControl.setDesiredValue(gyroControl.getDesiredVal()+rotation*4.5);	//was 4.5  //was was 6
        }

        double gyroCorrection = gyroControl.calcPID(sensorIn.getAngle());
        double leftComp = translate.scalarProjectOnto(this.leftVect);
		double rightComp = translate.scalarProjectOnto(this.rightVect);
		double backComp = translate.scalarProjectOnto(this.backVect);
		
		double maxOut = SimLib.max(leftComp, rightComp, backComp);

		//System.out.println("MAX OUT: "+maxOut);
		
		double strafeMagnitude = translate.mag();
		
		if(maxOut > 0 || maxOut < 0) {
			leftComp /= maxOut;
			rightComp /= maxOut;
			backComp /= maxOut;
		}
		if(this.inAuto) {
			if(gyroCorrection > this.turningMagnitude){
				gyroCorrection = this.turningMagnitude;
			}else if(gyroCorrection < -this.turningMagnitude){
				gyroCorrection = -this.turningMagnitude;
			}
		}
		
		leftComp = SimLib.limitValue(leftComp * strafeMagnitude + gyroCorrection);
		rightComp = SimLib.limitValue(rightComp * strafeMagnitude + gyroCorrection);
		backComp = SimLib.limitValue(backComp * strafeMagnitude + gyroCorrection);
		
		this.robotOut.setDriveLeft(leftComp);
		this.robotOut.setDriveRight(rightComp);
		this.robotOut.setDriveBack(backComp);
		
		SmartDashboard.putNumber("L Out: ", leftComp);
		SmartDashboard.putNumber("R Out: ", rightComp);
		SmartDashboard.putNumber("B Out: ", backComp);
    }
	
	private void gyroDriveOutputTurning(Vect translate, double rotation){
		
		this.pVal = SmartDashboard.getNumber("Gyro P: ");
		this.iVal = SmartDashboard.getNumber("Gyro I: ");
		this.dVal = SmartDashboard.getNumber("Gyro D: ");
		this.turningMagnitude = SmartDashboard.getNumber("Turning Magnitude: ");
		
		if(!this.inAuto){
			this.gyroControl.setConstants(this.pVal, this.iVal, this.dVal);
		}else{
			this.gyroControl.setConstants(0.04, this.iVal, this.dVal); // P was hard coded at 0.04 and works better
		}
        if(setAngle){
            gyroControl.setDesiredValue(sensorIn.getAngle());
            setAngle = false;
        } else if (Math.abs(rotation) > 0.02 && !this.inAuto) {
            //gyroControl.setDesiredValue(gyroControl.getDesiredVal()+rotation*4.5);	//was 4.5  //was was 6
			outputDrive(translate, rotation);
			System.out.println("Output inside of Gyro");
			this.setAngleFlag = true;
			return; 
			
        }else if (Math.abs(rotation) > 0.02){
			gyroControl.setDesiredValue(gyroControl.getDesiredVal()+rotation*4.5);
			
		}
        
        if(this.setAngleFlag){
        	if(Math.abs(this.sensorIn.getGyroSpeed()) < 0.6){ // was 0.2
        		gyroControl.setDesiredValue(sensorIn.getAngle());
        		this.setAngleFlag = false; //stop setting heading since we have slow down 
        	}
        	gyroControl.setDesiredValue(sensorIn.getAngle());
        	System.out.println("Resetting Desired heading");
        	
        }

        double gyroCorrection = gyroControl.calcPID(sensorIn.getAngle());
        double leftComp = translate.scalarProjectOnto(this.leftVect);
		double rightComp = translate.scalarProjectOnto(this.rightVect);
		double backComp = translate.scalarProjectOnto(this.backVect);
		
		double maxOut = SimLib.max(leftComp, rightComp, backComp);

		//System.out.println("MAX OUT: "+maxOut);
		
		double strafeMagnitude = translate.mag();
		
		if(maxOut > 0 || maxOut < 0) {
			leftComp /= maxOut;
			rightComp /= maxOut;
			backComp /= maxOut;
		}
		if(this.inAuto) {
			if(gyroCorrection > this.turningMagnitude){
				gyroCorrection = this.turningMagnitude;
			}else if(gyroCorrection < -this.turningMagnitude){
				gyroCorrection = -this.turningMagnitude;
			}
		}
		
		leftComp = SimLib.limitValue(leftComp * strafeMagnitude + gyroCorrection);
		rightComp = SimLib.limitValue(rightComp * strafeMagnitude + gyroCorrection);
		backComp = SimLib.limitValue(backComp * strafeMagnitude + gyroCorrection);
		
		this.robotOut.setDriveLeft(leftComp);
		this.robotOut.setDriveRight(rightComp);
		this.robotOut.setDriveBack(backComp);
		System.out.println("Gyro Output: "+gyroCorrection);
		System.out.println("Des Value: "+gyroControl.getDesiredVal());
		SmartDashboard.putNumber("L Out: ", leftComp);
		SmartDashboard.putNumber("R Out: ", rightComp);
		SmartDashboard.putNumber("B Out: ", backComp);
	
	
	
	
	
}
	
	
	
	public void gyroAtSpeed(double speed){
		double gyroCorrection = gyroControl.calcPID(sensorIn.getAngle());
		this.robotOut.setDriveLeft(-speed + gyroCorrection);
		this.robotOut.setDriveRight(speed + gyroCorrection);
		this.robotOut.setDriveBack(gyroCorrection);
			
		
	}
	
	public void gyroAtSpeedFps(double speed,double output){
	
		if(this.sensorIn.getXSpeedFps() < speed){ // bang bang high 
			double gyroCorrection = gyroControl.calcPID(sensorIn.getAngle());
			if(speed*0.13 > output){ // going to fast for acceleration control
				this.robotOut.setDriveLeft((-output) + gyroCorrection);
				this.robotOut.setDriveRight((output) + gyroCorrection);
				this.robotOut.setDriveBack(gyroCorrection);
				
			}else{
				this.robotOut.setDriveLeft((-speed*0.11) + gyroCorrection);
				this.robotOut.setDriveRight((speed*0.11) + gyroCorrection);
				this.robotOut.setDriveBack(gyroCorrection);
			}
			
		}else{ // bang bang low 
			double gyroCorrection = gyroControl.calcPID(sensorIn.getAngle());
			if(speed*0.07 > output){ // going too fast somehow
				this.robotOut.setDriveLeft((-output) + gyroCorrection);
				this.robotOut.setDriveRight((output) + gyroCorrection);
				this.robotOut.setDriveBack(gyroCorrection);
				
			}else{
				this.robotOut.setDriveLeft((-speed*0.06) + gyroCorrection);
				this.robotOut.setDriveRight((speed*0.06) + gyroCorrection);
				this.robotOut.setDriveBack(gyroCorrection);
			}
		
		}
		
		
		
	}
	
	
	public void resetDesiredAngle(double angle){
		  this.gyroControl.setDesiredValue(angle);
	}
	
	public void resetDesiredAngle() {
		this.resetDesiredAngle(this.sensorIn.getAngle());
	}
	
	public boolean PIDDone(){
		return this.gyroControl.isDone();
	}
	
	public void setAngle(double angle) {
		this.gyroControl.setDesiredValue(angle);
	}
	
	public void setInAuto(boolean inAuto) {
		this.inAuto = inAuto;
	}
	

}
