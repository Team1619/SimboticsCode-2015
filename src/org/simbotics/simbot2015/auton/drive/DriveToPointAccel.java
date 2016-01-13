package org.simbotics.simbot2015.auton.drive;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimLib;
import org.simbotics.simbot2015.util.SimPID;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToPointAccel extends AutonCommand {

	private SensorInput sensorIn; 
	private RobotOutput robotOut;
	
	private SimPID xControl;
	private SimPID yControl;
	private SimPID angleControl;
	
	private Vect leftVect;
	private Vect rightVect;
	private Vect backVect;
	
	private double pVal = 0.0;
	private double iVal = 0.0;
	private double dVal = 0.0;
	private int errorEps = 0;
	private int doneRange = 0;
	private double outputMultiplier = 0.0;
	private final double freeSpeed = 1500;
	
	
	public DriveToPointAccel(Vect point, double heading, int errorEps, double outputMultiplier, long timeout) {
		super(RobotComponent.DRIVE, timeout);
		
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
	
		this.errorEps = errorEps; 
		this.xControl = new SimPID();
		this.xControl.setDesiredValue(point.getX());
		this.yControl = new SimPID();
		this.yControl.setDesiredValue(point.getY());
		this.angleControl = new SimPID();
		this.yControl.setDoneRange(20);
		this.angleControl.setConstants(0.05,0.0,0.2);
		this.angleControl.setDesiredValue(heading);
		
		this.outputMultiplier = outputMultiplier;

		this.leftVect = Vect.fromAngle(150);
		this.rightVect = Vect.fromAngle(30);
		this.backVect = Vect.fromAngle(270);
	}
	
	public DriveToPointAccel(Vect point, double heading, int errorEps, double outputMultiplier) {
		this(point, heading, errorEps, outputMultiplier, -1);
	}
	
	public DriveToPointAccel(Vect point, double heading, int errorEps){
		this(point, heading, errorEps, 0.0);
	}

	@Override
	public boolean calculate() {
		this.pVal = SmartDashboard.getNumber("P Val: "); 
		this.iVal = SmartDashboard.getNumber("I Val: ");
		this.dVal = SmartDashboard.getNumber("D Val: ");
		
		this.doneRange = this.errorEps;

		this.xControl.setConstants(this.pVal, this.iVal, this.dVal);
		this.xControl.setDoneRange(this.doneRange);
		
		this.yControl.setConstants(this.pVal, this.iVal, this.dVal);
		this.yControl.setDoneRange(this.doneRange);
		
		Vect curPos = this.sensorIn.getPosition();
		double xOut = this.xControl.calcPID(curPos.getX());
		double yOut = this.yControl.calcPID(curPos.getY());
		
		double voltage = this.sensorIn.getVoltage();
		double window = this.outputMultiplier;
		
		Vect speed = this.sensorIn.getSpeedVect();
		
		double xMag = Math.abs(xOut);
		double xSign = xOut/xMag;
		double xSpeedFrac = speed.getX()/this.freeSpeed/xSign;
		if (xMag > xSpeedFrac + window) {
			xOut = (xSpeedFrac + window)*xSign;
		}
		
		double yMag = Math.abs(yOut);
		double ySign = yOut/yMag;
		double ySpeedFrac = speed.getY()/this.freeSpeed/ySign;
		if (yMag > ySpeedFrac + window) {
			yOut = (ySpeedFrac + window)*ySign;
		}
		
		Vect output = new Vect(xOut*12.5/voltage, yOut*12.5/voltage);
		
		// Convert from coordinate system direction to robot-centric direction
		output = output.rotate(-this.sensorIn.getAngle());

		double rotate = this.angleControl.calcPID(this.sensorIn.getAngle());
		rotate = SimLib.limitValue(rotate);
		
		if(this.outputMultiplier <= 0.001){
			this.outputMultiplier = SmartDashboard.getNumber("Output Magnitude: ");
		}
		
		if(this.xControl.isDone() && this.yControl.isDone()) {
    		this.robotOut.setDriveBack(0.0);
    		this.robotOut.setDriveLeft(0.0);
    		this.robotOut.setDriveRight(0.0);	
    		return true;
        } else {
    		double leftOut = output.scalarProjectOnto(this.leftVect) + rotate;
    		double rightOut = output.scalarProjectOnto(this.rightVect) + rotate;
    		double backOut = output.scalarProjectOnto(this.backVect) + rotate;
    		
    		this.robotOut.setDriveLeft(leftOut);
    		this.robotOut.setDriveRight(rightOut);
    		this.robotOut.setDriveBack(backOut);
    		
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
