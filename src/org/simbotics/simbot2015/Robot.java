
package org.simbotics.simbot2015;



import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.io.*;
import org.simbotics.simbot2015.teleop.TeleopControl;
import org.simbotics.simbot2015.teleop.TeleopDrive;
import org.simbotics.simbot2015.util.SimAccelCap;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	private RobotOutput robotOut;
	private DriverInput driverInput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
    private Logger logger;
    private static double ACCEL_CAP = 0.01;
    private static double ACCEL_VAL = 0.2;

    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	SmartDashboard.putNumber("P Val: ", 0.4);
    	SmartDashboard.putNumber("I Val: ", 0.001);
    	SmartDashboard.putNumber("D Val: ", 1.0);
    	SmartDashboard.putNumber("Error EPS Large: ",20.00);
    	SmartDashboard.putNumber("Done Range: ", 1.00);
    	SmartDashboard.putNumber("Error EPS Small", 1.00);
    	SmartDashboard.putNumber("BackOutputVal: ", 0.0);
		SmartDashboard.putNumber("Output Magnitude: ", 0.8);
		SmartDashboard.putNumber("Turning Magnitude: ", 1.0);
		SmartDashboard.putNumber("acceleration Cap: ",Robot.ACCEL_CAP);
		SmartDashboard.putNumber("Accel Jump Value: ",Robot.ACCEL_VAL);
		SmartDashboard.putNumber("Gyro P: ", 0.02);
    	SmartDashboard.putNumber("Gyro I: ", 0.0);
    	SmartDashboard.putNumber("Gyro D: ", 0.2);
		
		SmartDashboard.putNumber("Canburgle Fast Time: ", 600);
		SmartDashboard.putNumber("Canburgle Med Time: ", 265);
		SmartDashboard.putNumber("Canburgle Slow Time: ", 2000);
		SmartDashboard.putNumber("Range : ", 2000);
		SmartDashboard.putNumber("Indexer D: ", 0.000);
		SmartDashboard.putBoolean("Accel Control On", false);
		SmartDashboard.putNumber("Turning Speed: ", 0.65);
    	this.robotOut = RobotOutput.getInstance();
    	this.driverInput = DriverInput.getInstance();
    	this.sensorInput = SensorInput.getInstance();
    	this.teleopControl = TeleopControl.getInstance();
    	this.logger = Logger.getInstance();
   
		
		
    }
    

    public void autonomousInit() {
    	AutonControl.getInstance().initialize();
    	SensorInput.getInstance().reset();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	this.sensorInput.update();
    	AutonControl.getInstance().runCycle();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit(){
    	this.logger.openFile();
    	TeleopDrive.getInstance().reset();
    }
    
    
    public void teleopPeriodic() {
    	this.sensorInput.update();
    	this.teleopControl.runCycle();
        this.logger.logAll(); // write the log data
    }
    
    
    
    @Override
	public void disabledPeriodic() {
    	this.sensorInput.update();
    	AutonControl.getInstance().updateModes();	
	}

	/**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void disabledInit() {
    	this.robotOut.stopAll();
    	this.logger.close();
     	
    	
    }
    
}
