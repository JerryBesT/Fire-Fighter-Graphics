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

public class Fire {

	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private int heat;
	
	/**
	*This method detects and handles collisions between any active (!= null) Water objects, 
	*and the current Fire. When a collision is found, the colliding water should be removed, 
	*and this Fire's heat should be decremented by 
	*If this Fire's heat dips below one, then it should no longer be drawn to the screen, 
	*eject new Fireballs, or collide with Water and its shouldRemove() method should start returning true.
	*/
	public void handleWaterCollisions(Water[] water)
	{
		//use the loop to iterate the whole water and check if the water collides with water or not
		for(int i=0;i < water.length;++i)
		{
			if(water[i] != null)
			{
				//every time the collision occurs, decrement the heat by one
				//and set the index i of water array to null to eliminate
				if(graphic.isCollidingWith(water[i].getGraphic()))
				{
					heat = heat - 1;
					water[i] = null;
				}
			}
		}
	}
	
	/**
	 * This constructor initializes a new instance of Fire at the appropriate location and with the appropriate amount of heat. 
	 * The Random number generator should be used both to determine how much time remains before the next Fireball is propelled, 
	 * and the random direction it is shot in.
	 * @param the x-coordinate of this new Fire's position
	 * @param the y-coordinate of this new Fire's position
	 * @param randGen a Random number generator to determine when and in which direction new Fireballs are created and launched.
	 */
	public Fire(float x, float y, Random randGen)
	{
		// use the constructor to initialize heat values and graphic and position and the fire ball to count down.
		heat = 40;
		this.randGen = new Random();
		graphic = new Graphic("FIRE");
		graphic.setPosition(x, y);
		fireballCountdown = this.randGen.nextInt(3001) + 3000;
	}
	
	/**
	 * This is a simple accessor for this object's Graphic, 
	 * which may be used by other objects to check for collisions.
	 * @param time
	 * @return a reference to this Fire's Graphic object.
	 */
	public Fireball update(int time)
	{
		//only execute if the heat is less than one
		if(heat > 1)
		{
			graphic.draw();
			//keep counting down the fireballcountdown
			fireballCountdown = fireballCountdown - time;
			if(fireballCountdown <= 0)
			{
				// if count down is less than one, regenerate the count down number
				// and return a new fire ball object
				double direction = randGen.nextFloat() * 2 * Math.PI;
				Fireball fireball = new Fireball(graphic.getX(), graphic.getY(), (float)direction);
				this.fireballCountdown = randGen.nextInt(3000) + 3000 + 1;
				return fireball;
			}
		}
			return null;
			
	}
	
	/**
	*This is a simple accessor for this object's Graphic, 
	*which may be used by other objects to check for collisions.
	*@param a reference to this Fire's Graphic object.
	*/
	public Graphic getGraphic()
	{
		return this.graphic;
	}
	
	/**
	*This method should return false until this Fire's heat drops down to 0 or less.
	*After that it should begin to return true instead.
	*return false when this Fire's heat is greater than zero, otherwise true.
	*@param water - is the Array of water objects that have been launched by the Hero 
	*/
	public boolean shouldRemove()
	{
		//only return true if the heat is less than one
		if(heat < 1)
			return true;
		else
			return false;
	}
}
