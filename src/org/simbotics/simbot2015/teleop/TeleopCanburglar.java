package org.simbotics.simbot2015.teleop;

import org.simbotics.simbot2015.io.DriverInput;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopCanburglar implements TeleopComponent{
	private SensorInput sensorIn;
	private DriverInput driverIn;
	private RobotOutput robotOut;
	private static TeleopCanburglar instance;
	
	
	public static TeleopCanburglar getInstance() {
		if(instance == null) {
			instance = new TeleopCanburglar();
		}
		return instance;
	}
	
	private TeleopCanburglar(){
		this.sensorIn = SensorInput.getInstance();
		this.driverIn = DriverInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
	}
	
	@Override
	public void calculate() {
		// check for mode change
		
		
		
		
		
		if(this.driverIn.getCanburglarModeActivate()) {
			this.driverIn.setCanburglarMode(true);
		}
		if(this.driverIn.getCanburglarModeDeactivate()) {
			this.driverIn.setCanburglarMode(false);
		}
		
		SmartDashboard.putBoolean("CANBURGLAR MODE ACTIVE", this.driverIn.getCanburglarMode());
		
		
		// latch control

		if(this.driverIn.getCanburglarLatchButton()){
			this.robotOut.setCanburglarLatch(true);
		}else if(this.driverIn.getCanburglarUnLatchButton()){
			this.robotOut.setCanburglarLatch(false);
		}
		
		
		
		
		// arm control
		
		if(this.driverIn.getCanburglarArmUpButton()){
			this.robotOut.setCanburglarArmReverse();
		}else if(this.driverIn.getCanburglarArmDownButton()){
			this.robotOut.setCanburglarArmForward();
		}else{
			this.robotOut.setCanburglarArmOff();
		}
	
		
		
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

}
