package org.simbotics.simbot2015.io;

import org.simbotics.simbot2015.util.SimAccel;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimEncoder;
import org.simbotics.simbot2015.util.SimGyro;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SensorInput {
    
    private static SensorInput instance;
  
    private SimEncoder encDriveLeft;
    private SimEncoder encDriveRight;    
    private SimEncoder encDriveBack;
    
    private SimEncoder encIndexer; 
    
    private SimGyro gyro;
    private SimAccel accel;
    
    private Compressor compressor; 
    private PowerDistributionPanel pdp;
    
	private static double TICKSPERINCH = 255*2 / (Math.PI*4);
	
	private Vect position;
	private Vect dPosition;
	private double lastTime = 0.0;
	private double deltaTime = 20.0;
	private double gyroOffset = 0.0;
	private double indexerSpeed =0.0; 
	private boolean hasSeenHooks = false; 
	private boolean firstCycle =true;
	
	//drive at speed bang bang code
	private double xSpeedFps =0.00;
	private double prevXPosition;
	
	
	private DigitalInput seesHooksSensor; 
	private DigitalInput hasToteSensor;
	
	private DigitalInput topSensor;
	
	private SensorInput() {
    	this.encDriveLeft = new SimEncoder(3, 2);
    	this.encDriveRight = new SimEncoder(5, 4);
    	this.encDriveBack = new SimEncoder(7, 6);
    	this.encIndexer = new SimEncoder(0,1);
    	
    	this.compressor = new Compressor();
    	this.seesHooksSensor = new DigitalInput(8);
    	this.hasToteSensor = new DigitalInput(9);
    	this.topSensor = new DigitalInput(15);
    	
    	
    	this.gyro = new SimGyro(0);
    	this.accel = new SimAccel();
    	this.gyro.setSensitivity(0.00703*(350.0/360.0));//(0.006835);//(0.00703);
    			
    	
    	this.pdp = new PowerDistributionPanel();
    		
    	this.reset();
    }
    	
    public static SensorInput getInstance() {
        if(instance == null) {
            instance = new SensorInput();
        }
        return instance;
    }
    
    public void update() {
    	if (this.lastTime == 0.0) {
    		this.deltaTime = 20;
    		this.lastTime = System.currentTimeMillis();
    	} else {
    		this.deltaTime = System.currentTimeMillis() - this.lastTime;
    		this.lastTime = System.currentTimeMillis();
    	}

    	if(this.firstCycle){
    		this.encIndexer.set(-1160);
    		this.firstCycle = false;
    	}
    	
    	//System.out.println("Gyro Speed" + this.gyro.getSpeed());
    	this.encDriveLeft.updateSpeed();
    	this.encDriveRight.updateSpeed();
    	this.encDriveBack.updateSpeed();
    	this.encIndexer.updateSpeed();
    	this.gyro.updateAngle();
    	this.accel.update();
    	
    	if(this.canSeeHooks() && !hasSeenHooks){
			this.encIndexer.reset();
			this.hasSeenHooks = true;
		}
    	SmartDashboard.putNumber("Indexer Enc", this.getIndexerEnc());
    	SmartDashboard.putNumber("GYRO: ", this.getAngle());
    	
    	double left = this.getEncoderLeftSpeed() / TICKSPERINCH;
		double right = this.getEncoderRightSpeed() / TICKSPERINCH;
		double back = this.getEncoderBackSpeed() / TICKSPERINCH;
		
		SmartDashboard.putBoolean("Is air low?", this.getIsAirLow());
		SmartDashboard.putNumber("LEFT ENC Inches: ", left);
    	SmartDashboard.putNumber("RIGHT ENC Inches: ", right);
    	SmartDashboard.putNumber("BACK ENC Inches: ", back);
		
		SmartDashboard.putNumber("left speed: ", this.encDriveLeft.rawSpeed());
		SmartDashboard.putNumber("right speed: ", this.encDriveRight.rawSpeed());
		SmartDashboard.putNumber("back speed: ", this.encDriveBack.rawSpeed());
		
		//Absolute position
		this.dPosition = new Vect((-left+right)/Math.sqrt(3), left/3.0 + right/3.0 - 2/3.0*back);
		this.dPosition = dPosition.rotate(this.getAngle());
		position = position.add(this.dPosition);
		
		this.xSpeedFps = -1*(position.getX() - this.prevXPosition)/12.0 / (this.deltaTime/1000.0);
		
		
		SmartDashboard.putNumber("X Speed Fps:", this.xSpeedFps);
		
    	SmartDashboard.putNumber("X Position: ",this.getXPosition());
    	SmartDashboard.putNumber("Y Position: ",this.getYPosition());
    	
    	SmartDashboard.putNumber("Left Enc",this.getEncoderLeft());
    	SmartDashboard.putNumber("Right Enc",this.getEncoderRight());
    	SmartDashboard.putNumber("Back Enc",this.getEncoderBack());
    	
    	SmartDashboard.putNumber("Right Enc",this.getEncoderRight());
    	SmartDashboard.putNumber("Back Enc",this.getEncoderBack());
    	
    	SmartDashboard.putNumber("Gyro Speed:", this.gyro.getSpeed());
    	
    	this.indexerSpeed = this.encIndexer.speed();
    	SmartDashboard.putNumber("Indexer Speed: ", this.indexerSpeed);
    	this.prevXPosition = getXPosition();
    	SmartDashboard.putNumber("Prev X Pos:", this.prevXPosition);
    }
    
    public void reset() {
        //TODO: add sensors that need to be reset before auto here
		this.gyro.reset();
		
		this.position = new Vect(0,0);
		this.encDriveLeft.reset();
		this.encDriveRight.reset();
		this.encDriveBack.reset();
		this.firstCycle = true;
		this.hasSeenHooks =false;
		//this.encIndexer.reset();
		if (this.gyroFailed()){
			System.out.println("WARNING: Resetting GyroFailed");
			SmartDashboard.putString("WARNING: ", "Gyro Failed Flag Reset");
			this.gyro.manualResetGyroFailed();
		}
		
	} 
    
    public double getLastTickLength() {
    	return this.deltaTime;
    }
    
    // -----------------------------------------------------
    // ---- Component Specific Methods ---------------------
    // -----------------------------------------------------
    
    public Vect getSpeedVect() {
    	double left = this.encDriveLeft.getRate();
    	double right = this.encDriveRight.getRate();
    	double back = this.encDriveBack.getRate();
    	Vect robotCentric = new Vect((-left+right)/Math.sqrt(3), left/3.0 + right/3.0 - 2/3.0*back);
    	return robotCentric.rotate(this.getAngle());
    }
    
    // ----------------- DRIVE ------------------------------
    public int getEncoderLeft() {
    	return this.encDriveLeft.get();
    }
    
    public int getEncoderLeftSpeed() {
    	return this.encDriveLeft.speed();
    }
    
    public double getEncoderLeftRawSpeed() {
    	return this.encDriveLeft.rawSpeed();
    }
   
    public int getEncoderRight() {
    	return this.encDriveRight.get();
    }
    
    public int getEncoderRightSpeed() {
    	return this.encDriveRight.speed();
    }
    
    public double getEncoderRightRawSpeed() {
    	return this.encDriveRight.rawSpeed();
    }
    
    public int getEncoderBack() {
    	return this.encDriveBack.get();
    }
    
    public int getEncoderBackSpeed() {
    	return this.encDriveBack.speed();
    }
    
    public double getEncoderBackRawSpeed() {
    	return this.encDriveLeft.rawSpeed();
    }
    
    public SimEncoder getEncoderLeftObj() {
    	return this.encDriveLeft;
    }
   
    public SimEncoder getEncoderRightObj() {
    	return this.encDriveRight;
    }
    public SimEncoder getEncoderBackObj() {
    	return this.encDriveBack;
    }
    
    public double getGyroSpeed(){
    	return this.gyro.getSpeed();
    }
    
 // ----------------------- Indexer -------------------------------    
    
    public int getIndexerEnc(){
    	return this.encIndexer.get();
    }
    
    public int getIndexerSpeed(){
    	return this.encIndexer.speed();
    }
    
    public void setIndexerEnc(int val){
    	this.encIndexer.set(val);
    }
    
   
    
 // ----------------------- Light Sensors ----------------------------------    
    
    public boolean hasTote(){
    	return !this.hasToteSensor.get();
    }
    
    public boolean canSeeHooks(){
    	return !this.seesHooksSensor.get();
    }
    
    public boolean topSensor(){
    	return !this.topSensor.get();
    }
    
    
    
    
 // ----------------------- GYRO ----------------------------------
    
    public void setGyroOffset(double offset) {
    	this.gyroOffset = offset;
    }
    
    public double getAngle(){
    	return this.gyro.getAngle() - 90 + gyroOffset;
    }
    
    public boolean gyroFailed(){
    	return this.gyro.hasFailed();
    }
	
    
    // ----------------------- POSITIONAL SYSTEM ----------------------------------
    
	public Vect getPosition () {
		return this.position;
	}
	
	public double getXPosition () {
		return this.position.getX();
	}
	
	public double getYPosition () {
		return this.position.getY();
	}
	
	public double getXSpeedFps(){
		return this.xSpeedFps;
	}
	
	// ----------------------- PDP  ----------------------------------
    public double getVoltage() {
    	return this.pdp.getVoltage();
    }
    
    public double getCurrent(int channel) {
    	return this.pdp.getCurrent(channel);
    }
    
    public double getTotalCurrent() {
    	return this.pdp.getTotalCurrent();
    }
    
 // ----------------------- Compressor  ----------------------------------
    
    public boolean getIsAirLow(){
    	return this.compressor.getPressureSwitchValue();
    }
    
}