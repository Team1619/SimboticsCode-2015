package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.auton.canburglar.*;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CanburgleDropAndWaitLeft implements AutonMode {

	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
		
		ab.addCommand(new SetGyroOffset(270));
		
		// first can
		ab.addCommand(new CanburglarSetLatch(true));
	
		ab.addCommand(new AutonWait((long) SmartDashboard.getNumber("Canburgle Med Time: ")));
		
		ab.addCommand(new CanburglarWinchIn(1000));
		ab.addCommand(new CanburglarWait());
		//ab.addCommand(new DriveToPoint(new Vect(0, -96), 270, 1,true,3000,false,0.3)); // pull it forward
		//ab.addCommand(new DriveWait());
		
		
	
	
		
		return ab.getAutonList();
	}

}
