import java.util.ArrayList;
import java.util.Random;

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

public class Pant 
{

	//declare class variables
	private Graphic graphic;
	private Random randGen = new Random();
	private boolean isAlive;
	
	/**
	 * method that used to handle collisions between fire balls and pants
	 * @param fireballs
	 * @return 
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs)
	{
		//boolean used to return
		boolean isHit = false;
		//check all the objects in the fire ball array list 
		for(int i=0; i < fireballs.size();++i)
		{
			if(fireballs != null && graphic.isCollidingWith(fireballs.get(i).getGraphic()))
			{
				//if collision happens, destroy the fire ball and hit is true
				isAlive = false;
				fireballs.get(i).destory();
				Fire b = new Fire(graphic.getX(), graphic.getY(), randGen);
				return b;
			}
		}
		//if hit is true, return a new fire ball object, else return null
			return null;
	}

	/**
	*This constructor initializes a new instance of Pant at the appropriate location. 
	*The Random number is only used to create a new Fire, 
	*after this pant is hit by a Fireball.
	*x - the x-coordinate of this new Pant's position
	*y - the y-coordinate of this new Pant's position
	*randGen - a Random number generator to pass onto any Fire that is created as a result of this Pant being hit by a Fireball.
	*/
	public Pant(float x, float y, Random randGen)
	{
		graphic = new Graphic ("PANT");
		graphic.setPosition(x, y);
		isAlive = true;
	}
	
	/**
	 * This method is simply responsible for draing the current Pant to the screen.
	 * @param time is the amount of time in milliseconds that has elapsed since the last time this update was called.
	 */
	public void update(int time)
	{
		// only isAlive equals to true executes
		if(isAlive)
			graphic.draw();
	}
	
	/**
	 *  This is a simple accessor for this object's Graphic, 
	 *  which may be used by other objects to check for collisions.
	 * @return a reference to this Pant's Graphic object.
	 */
	public Graphic getGraphic()
	{
		return this.graphic;
	}
	
	/**
	 * This method communicates to the Game whether this Pant is still in 
	 * use versus ready to be removed from the Game's ArrayList of Pants.
	 * @return true when this Pant has been hit by a Fireball, otherwise false.
	 */
	public boolean shouldRemove()
	{
		//return true if the isAlive is false
		if(isAlive == false)
			return true;
		else
			return false;
	}
}
