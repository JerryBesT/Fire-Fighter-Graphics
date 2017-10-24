import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:            Pants On Fire
//Files:            Fire.java, Hero.java, Pant.java, Fireball.java, Water.java
//Semester:         CS302 Fall 2016
//
//Author:           Zhenyu Zou
//Email:            zzou24@wisc.edu
//CS Login:         zzou
//Lecturer's Name:  Gary Dahl
//Lab Section:      1350
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                   I DO NOT HAVE A PARTNER
//Partner Name:     (name of your pair programming partner)
//Partner Email:    (email address of your programming partner)
//Partner CS Login: (your partner's login name)
//Lecturer's Name:  (name of your partner's lecturer)
//Lab Section:      (your partner's lab section number)
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//___ Write-up states that Pair Programming is allowed for this assignment.
//___ We have both read the CS302 Pair Programming policy.
//___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully 
//acknowledge and credit those sources of help here.  Instructors and TAs do 
//not need to be credited here, but tutors, friends, relatives, room mates 
//strangers, etc do.
//
//Persons:          (identify each person and describe their help in detail)
//Online Sources:   Piazza, Google
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * 
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */



public class Level
{
	private Random randGen = new Random();
	private Graphic graphic;
	private Hero hero;
	Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fireball> fireballs;
	private ArrayList<Fire> fires;

	
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Level(Random randGen, String level) 
	{ 
		//generate a new random
		this.randGen = new Random();
		//declare a new hero object
		hero = new Hero(GameEngine.getWidth() / 2, GameEngine.getHeight() / 2, randGen.nextInt(3) + 1);
		//Initialize a new water array of 8 
		water = new Water[8];
		//initialize a new arraylist of pants
		pants = new ArrayList<Pant>();
		//initialize a new arraylist of fireballs
		fireballs = new ArrayList<Fireball>();
		//initialize a new arraylist of fires
		fires = new ArrayList<Fire>(6);
		if(level.equals("RANDOM"))
			//if the source level is not "random", execute designed level "loadlevel(level)"
			createRandomLevel();
		else
			loadLevel(level);
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short
	 * 3 second pause and a message indicating that the player has lost.  When
	 * this method returns "ADVANCE", a short pause and win message will be 
	 * followed by the creation of a new level which replaces this one.  When
	 * this method returns anything else (including "CONTINUE"), the GameEngine
	 * will simply continue to call this update() method as usual. 
	 */
	public String update(int time) 
	{
		//calling multiple times of hero to update the status of hero
		hero.update(time, water);
		//use the for loop to update all the non-null water array indexes
		for(int i = 0;i < water.length;++i)
			if(water[i] != null) water[i] = water[i].update(time);
		
		for(int j=0;j < fires.size();++j)
		{
			//store the return from the update of fires just in case it will be changed
			Fireball a = fires.get(j).update(time);
			//if the instance a is not null, then store the fire balls into fireballs arraylist.
				if(a != null)
					fireballs.add(a);
			//eliminate the fire when the collision between fires and water occurs
			fires.get(j).handleWaterCollisions(water);
		}
		
		for(int i=0;i < fireballs.size();++i)
		{
			//print out the fire balls
			fireballs.get(i).update(time);
			//eliminate the fire balls when the collision between fires and water occurs
			fireballs.get(i).handleWaterCollisions(water);
		}
		
		for(int i=0;i < pants.size();++i)
		{
			//print out the pants
			pants.get(i).update(time);
			if(pants.get(i) != null)
			{
				//detect if the collision between pants and fire balls
				pants.get(i).handleFireballCollisions(fireballs);
				//store the return into a instance b in case it gets changed.
				Fire b = pants.get(i).handleFireballCollisions(fireballs);
				//if it does not return null, store it into fire arraylist
				if(b != null)
					fires.add(b);
			}
		}
		
		for(int i=0;i < fireballs.size();++i)
		{
			//if the fire balls should be removed, removed
			if(fireballs.get(i).shouldRemove() == true)
				fireballs.remove(i);
		}
		
		for(int i=0;i < pants.size();++i)
		{
			//if the pants should be removed, removed
			if(pants.get(i).shouldRemove() == true)
				pants.remove(i);
		}
		
		for(int i=0;i < fires.size();++i)
		{
			//if the fires should be removed, removed
			if(fires.get(i).shouldRemove() == true)
				fires.remove(i);
		}
		
		//if the hero collides with a fire ball, exist the game
		if(hero.handleFireballCollisions(fireballs))
		{
			return "QUIT";
		}
		else if(pants.size() <= 0)
		{
			return "QUIT";
		}
		else
		{
			if(fires.isEmpty())
				// if the fires are all eliminated, move on to the next level
				return "ADVANCE";
			else
				return "CONTINUE"; 
		}
		
	}	

	/**
	 * This method returns a string of text that will be displayed in the
	 * upper left hand corner of the game window.  Ultimately this text should 
	 * convey the number of unburned pants and fires remaining in the level.  
	 * However, this may also be useful for temporarily displaying messages that 
	 * help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{
		String a = "Pants Left: " + pants.size();
		String b = a + "\n" + "Fires Left: " + fires.size();
		// simply print out the status of the pants and fires, and store them into a string b
		return b;
			
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires,
	 * and 20 randomly positioned Pants.
	 */
	public void createRandomLevel() 
	{ 
		// Initialize all the pants and fireballs and fires array lists
		for(int i=0;i < 20;++i)
		{
			pants.add(new Pant(randGen.nextInt(GameEngine.getWidth()), 
					randGen.nextInt(GameEngine.getHeight()), randGen));
		}
		
		fireballs = new ArrayList<Fireball>();
		
		fires = new ArrayList<Fire>();
		for(int j=0;j < 6;++j)
		{
			fires.add(new Fire(randGen.nextInt(GameEngine.getWidth()), 
					randGen.nextInt(GameEngine.getHeight()), randGen));
		}
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * @param level is a string containing the contents of a custom level file 
	 * that is read in by the GameEngine.  The contents of this file are then 
	 * passed to Level through its Constructor, and then passed from there to 
	 * here when a custom level is loaded.  You can see the text within these 
	 * level files by dragging them onto the code editing view in Eclipse, or 
	 * by printing out the contents of this level parameter.  Try looking 
	 * through a few of the provided level files to see how they are formatted.
	 * The first line is always the "ControlType: #" where # is either 1, 2, or
	 * 3.  Subsequent lines describe an object TYPE, along with an X and Y 
	 * position, formatted as: "TYPE @ X, Y".  This method should instantiate 
	 * and initialize a new object of the correct type and at the correct 
	 * position for each such line in the level String.
	 */
	public void loadLevel(String level) 
	{ 
		//first we split the whole level statements into a bunch of lines
		String[] TempLine = level.split("\n");
		//the information for the first line is the control number, so we use char to extract the portion
		char ControlNum = TempLine[0].charAt(TempLine[0].length() - 1);
		//and we transfer the character value into numerical value.
		int Num = Character.getNumericValue(ControlNum);
		
		//iterate the whole statement lines
		for(int i = 1; i < TempLine.length; i++)
		{
			//the form of the line works similar after the first line, important information is separated into two parts
			String[] Content = TempLine[i].split("@");
			//so we use the two parts to determine the position and type
			String[] position = Content[1].split(",");
			String Type = Content[0].trim();
			//we extract the number and transfer them into float numbers
			float x = Float.parseFloat(position[0].trim());
			float y = Float.parseFloat(position[1].trim());
			
			if(Type.equals("FIRE"))
			{
				//if the type equals fire then add new fire object
				fires.add(new Fire(x, y, randGen));
			}
			else if(Type.equals("PANT"))
			{
				//if the type equals fire then add new pants object
				pants.add(new Pant(x, y, randGen));
			}
			else if(Type.equals("HERO"))
			{
				//if the type equals fire then add new hero object
				hero = new Hero(x, y, Num);
			}
		}
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level.  Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * @param args is the sequence of custom level files to play through.
	 */
	public static void main(String[] args)
	{
		GameEngine.start(null,args);
	}
}
