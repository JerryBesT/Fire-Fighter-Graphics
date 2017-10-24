
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

public class Fireball {

	private Graphic graphic;
	private float speed;
	private boolean isAlive;
	
	/**
	 * This method detects and handles collisions between any active (!= null) Water objects, and the current Fireball. 
	 * When a collision is found, the colliding water should be removed (array reference set to null), 
	 * and this Fireball should also be removed from the game (its shouldRemove() should begin to return true when called). 
	 * When this Fireball's shouldRemove method is already returning true, this method should not do anything.
	 * @param water is the Array of Water objects that have been launched by the Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) 
	{
		//iterate the whole water array, set isAlive variable to false if water index is not null and collision is true
		//and eliminate the water by referring it to null
		for(int i=0; i < water.length;++i)
		{
			if(water[i] != null)
			{
				if(this.graphic.isCollidingWith(water[i].getGraphic()))
				{
					isAlive = false;
					water[i] = null;
				}
			}
		}
	}
	
	/**
	 * This constructor initializes a new instance of Fireball at the specified location and facing a specific movement direction. 
	 * This Fireball should move with a speed of 0.2 pixels per millisecond.
	 * @param x the x-coordinate of this new Fireball's position
	 * @param y the y-coordinate of this new Fireball's position
	 * @param directionAngle the angle (in radians) from 0 to 2pi that this new Fireball 
	 * should be both oriented and moving according to.
	 */
	public Fireball(float x, float y, float directionAngle)
	{
		//initialize all the following variables when you call a fire ball object
		this.isAlive = true;
		graphic = new Graphic("FIREBALL");
		speed = 0.2f;
		graphic.setDirection(directionAngle);
		graphic.setPosition(x, y);
		graphic.draw();
	}
	
	/**
	 * This method is called repeatedly by the Level to draw and move the current Fireball. 
	 * When a Fireball moves more than 100 pixels beyond any edge of the screen,
	 *  it should be destroyed and its shouldRemove() method should begin to return true instead of false.
	 * @param time-is the amount of time in milliseconds that has elapsed since the last time this update was called.
	 */
	public void update(int time)
	{
		//only execute if the isAlive variable is true.
		if(isAlive)
		{
			graphic.draw();
			//check whether the fire ball goes beyond the graph 100 pixels or not, if it does go
			//beyond, set isAlive to true. if balls are within the boundary, move the fire balls
			//in correct direction
			if(Math.abs(graphic.getX() - GameEngine.getWidth()) >= 100 ||
					Math.abs(graphic.getY() - GameEngine.getHeight()) >= 100)
			{
				isAlive = true;
				graphic.draw();
				float x = graphic.getX() + graphic.getDirectionX() * speed * time;
				float y = graphic.getY() + graphic.getDirectionY() * speed * time;
				graphic.setY(y);
				graphic.setX(x);
			}
			else
			{
				isAlive = false;
			}
		}
	}
	
	/**
	 * This is a simple accessor for this object's Graphic, which may be used by other objects to check for collisions.
	 * @return a reference to this Fireball's Graphic object.
	 */
	public Graphic getGraphic()
	{
		return this.graphic;
	}
	
	/**
	 * This helper method allows other classes (like Pant) to destroy a Fireball upon collision. 
	 * This method should ensure that the shouldRemove() methods only returns true after this method (destroy) has been called.
	 */
	public void destory()
	{
		isAlive = false;
	}
	
	/**
	 * This method communicates to the Level whether this Fireball is still 
	 * in use versus ready to be removed from the Levels's ArrayList of Fireballs.
	 * @return true when this Fireball has either gone off the screen or collided with a Water or Pant object, and false otherwise.
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
