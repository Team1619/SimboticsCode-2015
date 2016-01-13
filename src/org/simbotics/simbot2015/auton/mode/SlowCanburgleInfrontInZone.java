package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.auton.canburglar.*;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SlowCanburgleInfrontInZone implements AutonMode {

	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
		
		ab.addCommand(new SetGyroOffset(270));
		
		// first can
		ab.addCommand(new CanburglarSetLatch(true));
		ab.addCommand(new AutonWait((long) SmartDashboard.getNumber("Canburgle Slow Time: ")));
		
		ab.addCommand(new DriveToPoint(new Vect(0, -116), 270, 1,false,2000)); // pull it forward
		ab.addCommand(new CanburglarWinchIn(AutonControl.Winch_In_Time));
		
		ab.addCommand(new DriveWait());		// wait until it's pulled the first cans down
		ab.addCommand(new CanburglarWait());
		
		ab.addCommand(new CanburglarArmUp(AutonControl.ARM_Lift_Height));   // lift arm
		ab.addCommand(new CanburglarWait());
		
		
	
	
		
		return ab.getAutonList();
	}

}
