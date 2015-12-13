package ludum.mighty.paradox.enemy;

import java.util.ArrayList;

public class EnemyFactory {
	
	
	public static NoPlayer buildEnemy(String enemyString)
	{
		//Split lines by comma
		
		String [] enemyPatternList = enemyString.split(";");
		
		if (enemyPatternList.length > 0)
		{
			//Process first line
			//First line has this format
			//<class> <id> <dep_id> <duration_pattern>
			
			String [] firstLine = enemyPatternList[0].split(" ");
			
			String enemyType = firstLine[0];
			int idEnemy = Integer.parseInt(firstLine[1]);
			int depId = Integer.parseInt(firstLine[2]);
			long duration = Long.parseLong(firstLine[3]);
			
			ArrayList<String> otherPatternList = new ArrayList<String>();
			
			//Read the other lines
			//Other lines are init_time end_time init_x init_y end_x end_y <function>
			for (int i = 1; i < enemyPatternList.length; i++)
			{
				otherPatternList.add(enemyPatternList[i]);
			}
			
			//Do the constructor considering the class
		
			if (enemyType.toUpperCase().equals("BAT"))
			{
				return new Bat(idEnemy,depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("GARGOYLE"))
			{
				return new Gargoyle(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("GREENBLOB"))
			{
				return new GreenBlob(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("GUILLOTINE"))
			{
				return new Guillotine(idEnemy, depId, duration, otherPatternList);
				
			}
			else if (enemyType.toUpperCase().equals("PURPLEBLOB"))
			{
				return new PurpleBlob(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("WICKEDBLUEMAGE"))
			{
				return new WickedBlueMage(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("WICKEDPURPLEMAGE"))
			{
				return new WickedPurpleMage(idEnemy, depId, duration, otherPatternList);
				
			}
			else if (enemyType.toUpperCase().equals("WICKEDREDMAGE"))
			{
				return new WickedRedMage(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("WICKEDBULLET"))
			{
				return new WickedBullet(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("BREAKABLEBLOCK"))
			{
				return new BreakableBlock(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("SPIKE"))
			{
				return new Spike(idEnemy, depId, duration, otherPatternList);
			}	
			else if (enemyType.toUpperCase().equals("LEVER"))
			{
				return new Lever(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("JEWEL"))
			{
				return new Jewel(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("BEER"))
			{
				return new Beer(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("KEY"))
			{
				return new Key(idEnemy, depId, duration, otherPatternList);
			}
			else if (enemyType.toUpperCase().equals("DOOR"))
			{
				return new Door(idEnemy, depId, duration, otherPatternList);
			}
			
		}
		
		
		//No pattern found
		return null;
	}

}
