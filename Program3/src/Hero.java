import java.util.ArrayList;

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
//I DO NOT HAVE A PARTNER
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
public class Hero 
{
	private Graphic graphic;
	private float   speed;
	private int     controlType;
	
	/**
	 * This method detects an handles collisions between any active Fireball objects, and the current Hero. 
	 * When a collision is found, this method returns true to indicate that the player has lost the Game.
	 * @param fireballs the ArrayList of Fireballs that should be checked against the current Hero's position for collisions.
	 * @return true when a Fireball collision is detected, otherwise false.
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) 
	{
		//iterate the whole fire ball array, return true false if collision is true, else return false
		//and eliminate the water by referring it to null
		for(int i=0; i < fireballs.size();++i)
		{
			if(fireballs != null && graphic.isCollidingWith(fireballs.get(i).getGraphic()))
			{
				return true;
			}
		}
			return false;
		
	}
	
	/**
	 * This constructor initializes a new instance of Hero at the appropriate location and using the appropriate controlType. 
	 * This Hero should move with a speed of 0.12 pixels per millisecond.
	 * @param x the x-coordinate of this new Hero's position
	 * @param y the y-coordinate of this new Hero's position
	 * @param controlType specifies which control scheme should be used by the player to move this hero around: 1, 2, or 3.
	 */
	public Hero(float x, float y, int controlType)
	{
		//by initializing hero object, declare all the following variables, 
		graphic = new Graphic("HERO");
		speed = 0.12f;
		this.controlType = controlType;
		graphic.setPosition(x, y);
	}
	
	/**
	 * This method is called repeated by the Level to draw and move (based on the current controlType) the Hero, 
	 * as well as to spray new Water in the direction that this Hero is currently facing.
	 * @return a reference to this Hero's Graphic object.
	 */
	public Graphic getGraphic()
	{
		return this.graphic;
	}
	
	public String getHUDmessage()
	{
		String a = "X Position is: ";
		String b = "Y Position is: ";
		a = a + graphic.getX();
		b = b + graphic.getY();
		return a + "\n" + b; 
	}
	
	/**
	 * This method is called repeated by the Level to draw and move (based on the current controlType) the Hero, 
	 * as well as to spray new Water in the direction that this Hero is currently facing.
	 * @param time is the amount of time in milliseconds that has elapsed since the last time this update was called.
	 * @param water the array of Water that the Hero has sprayed in the past, and if there is an empty (null) element in this array,
	 *  they can can add a new Water object to this array by pressing the appropriate controls.
	 */
	public void update(int time, Water water[])
	{
		graphic.draw();
		
		//execute if control type equals one
		if(controlType == 1)
		{
			//set direction and distance to four different keys and each key has its own direction rage
			if(GameEngine.isKeyHeld("D"))
			{
				graphic.setDirection(0);
				graphic.setX(graphic.getX() + speed * time);
				//graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("A"))
			{
				graphic.setDirection((float) Math.PI);
				graphic.setX(graphic.getX() - speed * time);
				//graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("W"))
			{
				graphic.setDirection((float) ((3 * Math.PI) / 2));
				graphic.setY(graphic.getY() - speed * time);
				//graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("S"))
			{
				graphic.setDirection((float) Math.PI / 2);
				graphic.setY(graphic.getY() + speed * time);
				//graphic.draw();
			}
		}
		else if(controlType == 2)
		{
			//set the direction by the track of mouse and move by the keys
			if(GameEngine.isKeyHeld("D"))
			{
				graphic.setDirection(GameEngine.getMouseX() , GameEngine.getMouseY());
				graphic.setX(graphic.getX() + speed * time);
				graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("A"))
			{
				graphic.setDirection(GameEngine.getMouseX() , GameEngine.getMouseY());
				graphic.setX(graphic.getX() - speed * time);
				graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("W"))
			{
				graphic.setDirection(GameEngine.getMouseX() , GameEngine.getMouseY());
				graphic.setY(graphic.getY() - speed * time);
				graphic.draw();
			}
			
			else if(GameEngine.isKeyHeld("S"))
			{
				graphic.setDirection(GameEngine.getMouseX() , GameEngine.getMouseY());
				graphic.setY(graphic.getY() + speed * time);
				graphic.draw();
			}
		}
		else if(controlType == 3)
		{
			//change the direction of objects to mouse control
			graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
			float x1 = GameEngine.getMouseX();
			float y1 = GameEngine.getMouseY();
			float x2 = graphic.getX();
			float y2 = graphic.getY();
			//calculate the distance by the nearest distance of two points
			//which can be applied from the triangle theory
			double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			//if the distance is greater than 20 pixels, 
			if(distance >= 20)
			{
				//set the movement of the hero if the distance between mouse and the hero greater than 20 pixels
				graphic.setX(x2 + graphic.getDirectionX() * speed * time);
				graphic.setY(y2 + graphic.getDirectionY() * speed * time);
			}
		}
		
		double x = graphic.getDirection();
		if(GameEngine.isKeyHeld("SPACE") || GameEngine.isKeyHeld("MOUSE"))
		{
			//if the key is held, iterate the for loop for eight times and refer the water object to water array if it is null.
			for(int i=0; i < water.length;++i)
			{
				if(water[i] == null)
				{
					water[i] = new Water(graphic.getX(), graphic.getY(), (float) x);
					break;
				}
			}
		}
	}
}
