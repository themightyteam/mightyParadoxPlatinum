package ludum.mighty.paradox.enemy;

import java.util.ArrayList;

import ludum.mighty.paradox.assets.WholeGameAssets;
import ludum.mighty.paradox.pattern.DistanceStep;
import ludum.mighty.paradox.pattern.ObjectPattern;
import ludum.mighty.paradox.pattern.PatternStep;
import ludum.mighty.paradox.pattern.StepProcess;

public abstract class NoPlayer {

	public static final int TYPE_MAGE = 1;
	public static final int TYPE_BULLET = 2;
	public static final int TYPE_SPIKE = 3;
	public static final int TYPE_DOOR = 4;
	public static final int TYPE_BREAKABLEBLOCK = 5;
	public static final int TYPE_WICKEDREDMAGE = 6;
	public static final int TYPE_WICKEDBLUEMAGE = 7;
	public static final int TYPE_WICKEDPURPLEMAGE = 8;
	public static final int TYPE_GREENBLOB = 9;
	public static final int TYPE_PURPLEBLOB = 10;
	public static final int TYPE_BAT = 11;
	public static final int TYPE_GUILLOTINE = 12;
	public static final int TYPE_GARGOYLE = 13;
	public static final int TYPE_WICKEDBULLET = 14;
	public static final int TYPE_LEVER = 15;
	public static final int TYPE_KEY = 16;
	public static final int TYPE_JEWEL = 17;
	public static final int TYPE_BEER = 18;
	
	int noPlayerType;
	
	int idEnemy;
	int dependentId; //-1 if no dependency
	ObjectPattern pattern;
	boolean isAlive = true;
	
	float currentX = 0;
	float currentY = 0;
	
	int animation = 0;
	
	public NoPlayer(int id, int dependentId, long duration, ArrayList<String> otherPatternList)
	{
		this.idEnemy = id;
		this.dependentId = dependentId;
		
		this.updatePatternSteps(duration, otherPatternList);
				
	}
	
	public void updatePatternSteps(long duration, ArrayList<String> otherPatternList)
	{
		//Calculate the steps
		ArrayList<PatternStep> patternStepList = new ArrayList<PatternStep>();
		for (String pattern : otherPatternList)
		{
			//init_time end_time init_x init_y end_x end_y <function> <animation>
			String [] patternParts = pattern.split(" ");
			
			long initTime = Long.parseLong(patternParts[0]);
			long endTime = Long.parseLong(patternParts[1]);
			float initX = Float.parseFloat(patternParts[2]);
			float initY = Float.parseFloat(patternParts[3]);
			float endX = Float.parseFloat(patternParts[4]);
			float endY = Float.parseFloat(patternParts[5]);
			int function = Integer.parseInt(patternParts[6]);
			int animation = Integer.parseInt(patternParts[7]); //FIXME: better use a more descriptive name (you have chosen the easy way)
			
			//Build the object
			PatternStep newStep = new PatternStep(initTime, endTime, initX, initY, endX, endY, function, animation);
			
			//Add the pattern step to the list
			patternStepList.add(newStep);
			
			
		}
		
		this.pattern = new ObjectPattern( duration, patternStepList);
		
	}
	
	public void update( long newTime)
	{
		//Pick pattern step
		
		long nextTime = newTime % this.pattern.getDuration();
		
		for (PatternStep step : this.pattern.getPatternList())
		{
			
			//System.out.println("\t\tStep "+step.getEndTime());
			
			if ((step.getStartTime() < nextTime) && 
					(step.getEndTime() >= nextTime) )
			{
			
				
				this.animation = step.getAnimation();
				
				DistanceStep newStep = StepProcess.processStep(step, nextTime);
				
				this.currentX = newStep.getX();
				this.currentY = newStep.getY();
				
				//Just updated. Finish here
				return;
			}
		}
		
		//No Animation
		this.animation = WholeGameAssets.DOING_NOTHING;
		
		
		//TODO: probably this will have some implications in the world
		
	}
	
	public abstract void nowIsDead( long newTime);
	public abstract void nowIsCharred(long newTime);
	
	

	//Getters and setters



	public ObjectPattern getPattern() {
		return pattern;
	}

	public int getIdEnemy() {
		return idEnemy;
	}

	public void setIdEnemy(int idEnemy) {
		this.idEnemy = idEnemy;
	}

	public void setPattern(ObjectPattern pattern) {
		this.pattern = pattern;
	}

	public int getDependentId() {
		return dependentId;
	}

	public void setDependentId(int dependentId) {
		this.dependentId = dependentId;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public float getCurrentX() {
		return currentX;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	public void setCurrentY(float currentY) {
		this.currentY = currentY;
	}

	public int getAnimation() {
		return animation;
	}

	public void setAnimation(int animation) {
		this.animation = animation;
	}

	public int getNoPlayerType() {
		return noPlayerType;
	}

	public void setNoPlayerType(int noPlayerType) {
		this.noPlayerType = noPlayerType;
	}
	
	
	
}
