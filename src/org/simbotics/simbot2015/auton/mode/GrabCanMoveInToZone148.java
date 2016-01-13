package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonOverride;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerCanSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerFullDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerLastToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerSetLatches;
import org.simbotics.simbot2015.auton.indexer.IndexerToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerWait;
import org.simbotics.simbot2015.auton.indexer.IndexerWaitForHasTote;
import org.simbotics.simbot2015.auton.intake.BootSetOut;
import org.simbotics.simbot2015.auton.intake.CanGrabberSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeed;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeedIndependant;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GrabCanMoveInToZone148 implements AutonMode {
	private int largeEps =0;
	private int smallEps =0;
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		this.largeEps =(int)SmartDashboard.getNumber("Error EPS Large: ");
		this.smallEps =(int)SmartDashboard.getNumber("Error EPS Small");
		
		System.out.println("Error Eps Large: "+this.largeEps);
		System.out.println("Error Eps Small: "+this.smallEps);
		
		ab.addCommand(new SetGyroOffset(315));
		
		ab.addCommand(new IndexerSetLatches(true));
		ab.addCommand(new IntakeSetOpen(false));
		ab.addCommand(new BootSetOut(false));
		ab.addCommand(new CanGrabberSetOpen(true));
		
		ab.addCommand(new IntakeSetSpeedIndependant(1.0, -1.0));
		
		
		ab.addCommand(new DriveToPoint(new Vect(0,0), 0,1,1000));
		ab.addCommand(new AutonWait(150));
		
		ab.addCommand(new IntakeSetSpeedIndependant(-1.0, -1.0));
		ab.addCommand(new DriveWait());
		ab.addCommand(new CanGrabberSetOpen(false));
		
		ab.addCommand(new IndexerCanSequence());
		ab.addCommand(new IndexerWait());
		
		ab.addCommand(new DriveAtSpeedUntilLightSensor(1.0,0,true,2500,true));
		ab.addCommand(new DriveWait());
		ab.addCommand(new AutonWait(100));
		
		ab.addCommand(new IntakeSetSpeedIndependant(0, 0));
		ab.addCommand(new IndexerToteSequence(0.7));
		
		ab.addCommand(new AutonWait(250));
		
		ab.addCommand(new DriveToPoint(new Vect(-15,0),180,10,2500));
		ab.addCommand(new DriveWait());
		
		/*ab.addCommand(new DriveToPoint(new Vect(20,0),180,10,2500));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-15,0),180,20,true,1500,true,1.0));
		ab.addCommand(new DriveWait());
		*/
		ab.addCommand(new IntakeSetSpeedIndependant(-1.0, -1.0));
		//ab.addCommand(new IndexerWait());
		
		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.7,180,false,2000,true));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeedIndependant(0, 0));
		ab.addCommand(new IndexerToteSequence(0.7));
		ab.addCommand(new AutonWait(200));
		

		ab.addCommand(new IntakeSetSpeedIndependant(-0.5, 0.5));
		
		ab.addCommand(new DriveToPoint(new Vect(-110,-20),180,10,true,1500,true,1.0));
		ab.addCommand(new DriveWait());
		
		
		
		
		ab.addCommand(new DriveToPoint(new Vect(-135,12),180,1,true,1500,true,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeedIndependant(-1.0, -1.0));
		
		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.7,180,true,2500,true));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerLastToteSequence()); // pickup of last tote
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-200, 0), 180,this.largeEps,false,2000,false,1.0));
		ab.addCommand(new DriveWait());
		
		
		
		ab.addCommand(new DriveToPoint(new Vect(-210, 60), 180,this.largeEps,false,2000,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(0.0));
		
		ab.addCommand(new AutonOverride(RobotComponent.INDEXER));
		ab.addCommand(new IndexerFullDropSequence());
		
		ab.addCommand(new DriveToPoint(new Vect(-240, 165), 180,this.smallEps,false,3000,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(1.0));
		
		
		ab.addCommand(new IndexerWait());
	
		
		
		//ab.addCommand(new IndexerWait()); // can remove to save time
		ab.addCommand(new DriveToPoint(new Vect(-210, 165), 180,this.smallEps,false,-1,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-230, 165), 180,this.smallEps,false,-1,false,1.0));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-170, 165), 180,this.smallEps,false,-1,false,0.6));
		ab.addCommand(new DriveWait());
		
		
		
		
		
		
		return ab.getAutonList();
	}

}