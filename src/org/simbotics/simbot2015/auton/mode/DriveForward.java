package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForward implements AutonMode {
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		
		
	
		ab.addCommand(new SetGyroOffset(90));
		
		ab.addCommand(new DriveToPoint(new Vect(0, 80), 90,1,true,-1,false, 1.0));
		ab.addCommand(new DriveWait());
		
		return ab.getAutonList();
	}

}