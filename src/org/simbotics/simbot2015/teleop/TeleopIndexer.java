package org.simbotics.simbot2015.teleop;

import org.simbotics.simbot2015.io.DriverInput;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopIndexer implements TeleopComponent {
	private SensorInput sensorIn;
	private DriverInput driverIn;
	private RobotOutput robotOut; 
	private static TeleopIndexer instance;
	private boolean prevState = false; 
	private boolean droppingStack = false;
	private double goal; 
	private int slowRange; 
	private int speedUpRange;
	private double largeOutput;
	private double smallOutput; 
	private int currentPos; 
	private double indexerMultiplier; 
	private boolean upsideDownStack = false;
	private boolean hasLiftedStack = false;
	private int liftUpHeight = 0;
	private int slowDropVal =0;
	private boolean hasSeenHooksFlag = false;
	private boolean slowDownRangeFlag = false;
	private double dropStackStartingTime; 
	private boolean liftTimeFlag = false;
	private boolean wasIndexingCan = false;
	private double noodleHeight = 0.0;
	private double indexerD = 0.0; 
	private int hasSeenToteCount=0;
	private int ticksPerRev;
	private boolean CoopFlag = false;
	public static TeleopIndexer getInstance() {
		if(instance == null) {
			instance = new TeleopIndexer();
		}
		return instance;
	}
	
	private TeleopIndexer(){
		//SmartDashboard.putNumber("Indexer Goal", 0);
		SmartDashboard.putNumber("Large Output",1.0);
		SmartDashboard.putNumber("Small Output",0.4);
		SmartDashboard.putNumber("Slow Down Range",2050);
		SmartDashboard.putNumber("Speed Up Range", 2700);
		SmartDashboard.putNumber("Ticks Per Rev",3825);
		SmartDashboard.putNumber("Indexer Multiplier: ", 0.3);
		SmartDashboard.putNumber("Lift Up Val", 1200);
		SmartDashboard.putNumber("Slow Drop Val",1050);
		SmartDashboard.putNumber("Noodle Val",1500);
		SmartDashboard.putNumber("Indexer Stop Pos: ", 1);
		
		
		this.sensorIn = SensorInput.getInstance();
		
		this.driverIn = DriverInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
	}
	
	
	
	@Override
	public void calculate() {
		this.liftUpHeight = (int) SmartDashboard.getNumber("Lift Up Val");
		this.slowDropVal = (int) SmartDashboard.getNumber("Slow Drop Val");
		this.ticksPerRev = (int) SmartDashboard.getNumber("Ticks Per Rev");
		this.noodleHeight = SmartDashboard.getNumber("Noodle Val");
		SmartDashboard.putNumber("Indexer Enc", this.sensorIn.getIndexerEnc());
		this.currentPos = this.sensorIn.getIndexerEnc();//%this.ticksPerRev;
		
		SmartDashboard.putBoolean("DO I have Tote?", this.driverIn.getToteButton());
		SmartDashboard.putBoolean("DO I see Hooks?", this.sensorIn.canSeeHooks());
		this.largeOutput = SmartDashboard.getNumber("Large Output");
		this.smallOutput = SmartDashboard.getNumber("Small Output");
		this.slowRange = (int) SmartDashboard.getNumber("Slow Down Range");
		this.speedUpRange = (int) SmartDashboard.getNumber("Speed Up Range");
		this.indexerMultiplier = SmartDashboard.getNumber("Indexer Multiplier: ");
		this.indexerD = SmartDashboard.getNumber("Indexer D: ");
		
		if(this.sensorIn.hasTote()){
			this.hasSeenToteCount++;
		}else{
			this.hasSeenToteCount =0;
		}
		
		
		
		
		
		
		if(this.driverIn.getIntakeCanButton()){
			this.robotOut.setIndexerOpen(true);
			this.droppingStack = false;
			this.hasLiftedStack = false;	
			this.hasSeenHooksFlag =false;
			this.slowDownRangeFlag = false;
		}else if(this.driverIn.getDropStackTrigger() > 0.3){
			this.robotOut.setIndexerOpen(true);
			this.robotOut.setIntakeOpen(true);
			this.droppingStack = true;
			this.upsideDownStack = false;
		}else if(this.driverIn.getDropUpsideDownStackButton()){
			this.robotOut.setIntakeOpen(true);
			this.droppingStack = true;
			this.upsideDownStack = true;
		}else if(this.driverIn.getIntakeToteButton()){
			this.robotOut.setIndexerOpen(false);
			this.hasLiftedStack = false;	
			this.droppingStack = false;
			this.hasSeenHooksFlag =false;
			this.slowDownRangeFlag = false;
		}else if(this.driverIn.getIntakeFromStepTrigger() > 0.3){
			
			this.hasLiftedStack = false;
			this.droppingStack = false;
			this.hasSeenHooksFlag =false;
			this.slowDownRangeFlag = false;
		}else if(this.driverIn.getJumpButton()){
			if(!this.robotOut.getBreakOut()){
				this.robotOut.setIndexerOpen(true);
			}
			this.hasLiftedStack = false;
			this.droppingStack = false;
			this.hasSeenHooksFlag =false;
			this.slowDownRangeFlag = false;
		}
		

		boolean wasDroppingStack = this.droppingStack;
		this.droppingStack = false;
		
		if (this.driverIn.getCanIndexButton() || this.wasIndexingCan) {
			this.canIndexRoutine();
			
		}else if(this.driverIn.getNoodleIndexButton()){ 
			this.noodleIndexRoutine(); // add this
			
		}else if (this.driverIn.getFirstTotesButton() || this.driverIn.getJumpButton()) {
			this.firstToteIndexRoutine();
			this.goal =0;
		} else if (Math.abs(this.driverIn.getIndexer()) > 0.2) {
			this.robotOut.setIndexerMotor(this.driverIn.getIndexer());
			this.goal =0;
		} else if (wasDroppingStack) {
			this.droppingStack = true;
			this.goal =0;
			if(!this.upsideDownStack){
				this.dropStackRoutine();
			}else{
				this.dropStackRoutineUpsideDown();
			}
		} else if (this.driverIn.getLastToteButton()) {
			this.lastToteIndexRoutine();
			this.goal =0;
		} else {
			this.robotOut.setIndexerMotor(0.0);	
			this.goal =0;
		}
	}

	@Override
	public void disable() {
		
	}
	
	private void firstToteIndexRoutine() {
		double pos = this.currentPos%this.ticksPerRev;
		double speed = this.sensorIn.getIndexerSpeed();
	
		if ((this.driverIn.getToteButton() || this.hasSeenToteCount > 5 )|| pos > this.ticksPerRev*this.indexerMultiplier
			|| pos < SmartDashboard.getNumber("Indexer Stop Pos: ")) {
			// currently see the tote at bottom or have raised far enough that we should keep going
			// (ie lifted out of light sensor range)
			// don't stop until pos > 400 to move closer to the bottom instead of stopping at the light sensor
			
			if(pos > SmartDashboard.getNumber("Range : ") && pos < 2700){ // do D calc at top of indexer 
				double output = 1.0 - (speed * this.indexerD);
				if(output < 0){
					output = 0;
				}
				if(this.driverIn.getIndexer() >= 0.5){
					output = 0.6;
				}
				this.robotOut.setIndexerMotor(output); // even if we are going very fast dont change directions 
				SmartDashboard.putNumber("Indexer Output: ",output);
				if(this.driverIn.getJumpButton()){
					
					this.robotOut.setIndexerOpen(false);
				}
			}else{
				this.robotOut.setIndexerMotor(1.0);
				if(this.driverIn.getJumpButton() && this.hasSeenToteCount > 5){
					this.robotOut.setCanburglarLatch(true);
				}
				SmartDashboard.putNumber("Indexer Output: ",1.0);
			}
		}else{ // add move back code 
			// finished
			
			if(pos > 550 && pos < 1200) { // hooks are in a bad spot
				this.robotOut.setIndexerMotor(-0.6);
			}else{
				this.robotOut.setIndexerMotor(0.0);
			}	
		}
	}
	
	private void lastToteIndexRoutine(){
		double pos = this.currentPos % this.ticksPerRev;
		
		if (pos > 2600) {
			// on the back side - go fast around
			this.robotOut.setIndexerMotor(1);
		} else if (pos < this.ticksPerRev*0.20 && (this.driverIn.getToteButton() || this.sensorIn.hasTote())) { // not there
			// still too low, keep going fast
			this.robotOut.setIndexerMotor(1);
		} else if (pos < 1000 && (this.driverIn.getToteButton() || this.sensorIn.hasTote())) {
			// close, go at slower speed
			// also will happen again if stack starts lowering
			this.robotOut.setIndexerMotor(0.4);
		} else {
			// at the carry height - all good for now
			this.robotOut.setIndexerMotor(0.0);
		}
	}
	
	private void canIndexRoutine(){
		// TODO: simplify me now that we have a set zero point for the hooks
		if (this.goal == 0) {
			this.goal = Math.ceil(((double)this.currentPos+1180)/this.ticksPerRev) * this.ticksPerRev;
		}
		this.robotOut.setCanGrabber(true);
		double pos = this.currentPos - this.goal + this.ticksPerRev; 
		if(this.currentPos < this.goal){ // not there
			this.wasIndexingCan = true;
			if(pos > 2000){ // got high enough to close latches
				this.robotOut.setIndexerMotor(1.0);
				this.robotOut.setIndexerOpen(false);  // close latches to hold can up
				this.robotOut.setIntakeOpen(false);   // close intake (tote pickup time!)
			}else{ // far away
				this.robotOut.setIndexerMotor(1.0);
			}
		} else{
			// finished
			this.robotOut.setIndexerMotor(0.0);
			this.wasIndexingCan = false;
		}
	}
	
	
	
	private void noodleIndexRoutine(){
		double pos = this.currentPos%this.ticksPerRev;
	
		if (pos < this.noodleHeight || pos > 2500) {
			this.robotOut.setIndexerMotor(1.0);
		} else {
			// finished
			this.robotOut.setIndexerMotor(0.0);
		}
	}
	
	
	
	
	
	

	private void dropStackRoutine(){
		// TODO: rewrite as series of steps instead to simplify
		
		double pos = (this.currentPos%this.ticksPerRev);
		System.out.println("Pos:"+pos);
		System.out.println("LightSensor:"+this.sensorIn.canSeeHooks());
		
		if(pos <= this.liftUpHeight && !this.hasLiftedStack) { // we want to drop but we have not lifted it up yet
			this.robotOut.setIndexerMotor(1.0);
		}else if(pos > this.liftUpHeight && !this.hasLiftedStack){ // we got to position now we can drop
			this.hasLiftedStack = true; 
		}else if(pos > this.slowDropVal && this.hasLiftedStack && !this.slowDownRangeFlag){
			this.robotOut.setIndexerMotor(-this.smallOutput);
			this.robotOut.setCanGrabber(false);
		}else if (!this.sensorIn.canSeeHooks()  && (pos > 30 && pos < 1500) && !this.hasSeenHooksFlag ) { // go down until light sensor
			System.out.println("Go Down Section!");
			this.slowDownRangeFlag = true;
			this.robotOut.setIndexerMotor(-1.0);
			this.robotOut.setCanGrabber(false);
		}else if(!this.hasSeenHooksFlag){
			this.hasSeenHooksFlag =true;
			System.out.println("Has Seen Hooks");
		}else{ // we have finished the drop routine
			this.robotOut.setIndexerMotor(0.0);
			System.out.println("Finished");
			this.droppingStack = false;
			
		}
	}
	
	
	private void dropStackRoutineUpsideDown(){
		// TODO: rewrite as series of steps instead to simplify
		
		
		double pos =(this.currentPos%this.ticksPerRev);
		if(pos <= 2475 && !this.hasLiftedStack) { // we want to drop but we have not lifted it up yet
			this.robotOut.setIndexerMotor(1.0);
		}else if(pos > 2475 && !this.hasLiftedStack){ // we got to position now we can drop
			this.hasLiftedStack = true; 
			this.robotOut.setIndexerOpen(true);
		}else if (!this.sensorIn.canSeeHooks() && !this.hasSeenHooksFlag && (pos > 50 && pos < 3400)) { // go down until light sensor
			this.robotOut.setIndexerMotor(1.0);
		}else if(!this.hasSeenHooksFlag){
			this.hasSeenHooksFlag =true;
		}else{ // we have finished the drop routine
			this.robotOut.setIndexerMotor(0.0);
			this.droppingStack = false;
			
			
		}
	}
	
}
	
	

