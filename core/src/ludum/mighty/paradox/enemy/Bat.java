package ludum.mighty.paradox.enemy;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Bat extends NoPlayer {

	public Bat(int id, int dependentId, long duration,
			ArrayList<String> otherPatternList) {
		super(id, dependentId, duration, otherPatternList);
		// TODO Auto-generated constructor stub
		
		this.noPlayerType = NoPlayer.TYPE_BAT;
	}

	@Override
	public void nowIsDead(long newTime) {
		// TODO Auto-generated method stub
	
		/*
		String deathPattern = MessageFormat.format("{0} {1} {2} {3} {4} {5} {6} {7}", 
				new Object [] {
				Long.toString(newTime),
				Long.toString(newTime + 1750),
				Float.toString(this.currentX),
				Float.toString(this.currentY),
				Float.toString(this.currentX),
				Float.toString(this.currentY),
				Integer.toString(1),
				Integer.toString(43)
				
				
		} ); */
		
		String deathPattern = Long.toString(newTime)+" "+
				Long.toString(newTime + 1750)+" "+
				Float.toString(this.currentX)+" "+
				Float.toString(this.currentY)+" "+
				Float.toString(this.currentX)+" "+
				Float.toString(this.currentY)+" "+
				Integer.toString(1)+" "+
				Integer.toString(43);
		
		ArrayList<String> stepList = new ArrayList<String>();
		stepList.add(deathPattern);
		stepList.add(Long.toString(newTime + 1750)+" "+Long.toString(Long.MAX_VALUE)+" 0 0 0 0 1 43");
		
		this.updatePatternSteps(Long.MAX_VALUE, stepList);
		
	}

	@Override
	public void nowIsCharred(long newTime) {
		// TODO Auto-generated method stub
		
		/*
		String deathPattern = MessageFormat.format("{0} {1} {2} {3} {4} {5} {6} {7}", 
				new Object [] {
				Long.toString(newTime),
				Long.toString(newTime + 1750),
				Float.toString(this.currentX),
				Float.toString(this.currentY),
				Float.toString(this.currentX),
				Float.toString(this.currentY),
				Integer.toString(1),
				Integer.toString(43)
				
		} ); */
		String deathPattern = Long.toString(newTime)+" "+
				Long.toString(newTime + 1750)+" "+
				Float.toString(this.currentX)+" "+
				Float.toString(this.currentY)+" "+
				Float.toString(this.currentX)+" "+
				Float.toString(this.currentY)+" "+
				Integer.toString(1)+" "+
				Integer.toString(43);
		
		
		ArrayList<String> stepList = new ArrayList<String>();
		stepList.add(deathPattern);
		stepList.add(Long.toString(newTime + 1750)+" "+Long.toString(Long.MAX_VALUE)+" 0 0 0 0 1 43");
		
		this.updatePatternSteps(Long.MAX_VALUE, stepList);
	}



}
