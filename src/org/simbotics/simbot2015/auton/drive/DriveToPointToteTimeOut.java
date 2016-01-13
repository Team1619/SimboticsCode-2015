package org.simbotics.simbot2015.auton.drive;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimAccelCap;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimPID;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToPointToteTimeOut extends AutonCommand {

	private SensorInput sensorIn; 
	private RobotOutput robotOut;
	
	private double desiredHeading;
	
	private SimPID xControl;
	private SimPID yControl;
	
	private SimDriveControl driveControl;
	
	private boolean firstCycle = false;
	
	private double pVal = 0.0;
	private double iVal = 0.0;
	private double dVal = 0.0;
	private int errorEps = 0;
	private int doneRange = 0;
	private boolean AccelerationControl = true;
	private boolean goFaster = false;
	private double maxSpeed; 
	private SimAccelCap xCap = new SimAccelCap();
	private SimAccelCap yCap = new SimAccelCap();
	
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps, boolean AccelerationControl , long timeout, boolean goFaster, double maxSpeed) {
		super(RobotComponent.DRIVE, timeout);
		
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		this.maxSpeed = maxSpeed;
		this.desiredHeading = heading;
		this.errorEps = errorEps; 
		this.driveControl = new SimDriveControl();
		this.xControl = new SimPID();
		this.xControl.setDesiredValue(point.getX());
		this.yControl = new SimPID();
		this.yControl.setDesiredValue(point.getY());
		this.firstCycle = true;
		this.driveControl.setInAuto(true);
		this.goFaster = goFaster; 
		this.AccelerationControl = AccelerationControl;
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps, boolean AccelerationControl, boolean goFaster) {
		this(point, heading, errorEps, AccelerationControl, -1,goFaster,1.0);
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps,boolean AccelerationControl ,long timeout){
		this(point,heading,errorEps,AccelerationControl,timeout,false,1.0);
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps, boolean AccelerationControl){
		this(point,heading,errorEps,AccelerationControl,-1,false,1.0);
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps, long timeout){
		this(point,heading,errorEps,true,timeout,false,1.0);
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading , int errorEps, double maxSpeed){
		this(point,heading,errorEps,true,-1,false,maxSpeed);
	}
	
	public DriveToPointToteTimeOut(Vect point, double heading, int errorEps){
		this(point, heading, errorEps, true,false);
	}

	@Override
	public boolean calculate() {
		
		this.pVal = SmartDashboard.getNumber("P Val: "); 
		this.iVal = SmartDashboard.getNumber("I Val: ");
		this.dVal = SmartDashboard.getNumber("D Val: ");
		
		this.doneRange = this.errorEps;    //(int) SmartDashboard.getNumber("Done Range: ");

		this.xControl.setConstants(this.pVal, this.iVal, this.dVal);
		this.xControl.setErrorEpsilon(this.errorEps);
		this.xControl.setDoneRange(this.doneRange);
		
		this.yControl.setConstants(this.pVal, this.iVal, this.dVal);
		this.yControl.setErrorEpsilon(this.errorEps);
		this.yControl.setDoneRange(this.doneRange);
		
		if(this.firstCycle) {
			this.firstCycle = false;
			this.driveControl.resetDesiredAngle();
		}
		
		Vect curPos = this.sensorIn.getPosition();
		
		
		//Vect output = new Vect(this.xControl.calcPID(curPos.getX()), this.yControl.calcPID(curPos.getY()));
		double xOut = 0.0;
		double yOut = 0.0;
		if(this.AccelerationControl){
			
			if(this.goFaster){
				xOut = this.xCap.calculate(this.xControl.calcPID(curPos.getX()),true);
				yOut = this.yCap.calculate(this.yControl.calcPID(curPos.getY()),true);
			
				SmartDashboard.putNumber("AC_xOut", xOut);
				SmartDashboard.putNumber("AC_yOut", yOut);
				
			}else{
				xOut = this.xCap.calculate(this.xControl.calcPID(curPos.getX()));
				yOut = this.yCap.calculate(this.yControl.calcPID(curPos.getY()));
			
				SmartDashboard.putNumber("AC_xOut", xOut);
				SmartDashboard.putNumber("AC_yOut", yOut);
			}
		}else{
			xOut = this.xControl.calcPID(curPos.getX());
			yOut = this.yControl.calcPID(curPos.getY());
			
			
		}
		
		Vect output = new Vect(xOut, yOut);	
		output = output.rotate(-this.sensorIn.getAngle());
		// Convert from coordinate system direction to robot-centric direction
		
		
		// output magnitude capping
		if(output.mag() > this.maxSpeed ){
            output = output.unit().scalarMult(this.maxSpeed);

		}
		
		
		
		this.driveControl.setAngle(this.desiredHeading);
		   
        if((this.xControl.isDone() && this.yControl.isDone()) || this.sensorIn.hasTote()) {
            // shut off motors
			this.robotOut.setDriveBack(0.0);
			this.robotOut.setDriveLeft(0.0);
			this.robotOut.setDriveRight(0.0);
			System.out.println("In Position");
			this.firstCycle = true; //FIXME: this is kinda horrible do an actual implementation
            return true;
        } else {
            // not done yet
    		this.driveControl.drive(output, 0.0);
    		//System.out.println("output: " + output.mag());
            return false;
        }
  	}

	@Override
	public void override() {
		this.robotOut.setDriveBack(0.0);
		this.robotOut.setDriveLeft(0.0);
		this.robotOut.setDriveRight(0.0);	
	}
}