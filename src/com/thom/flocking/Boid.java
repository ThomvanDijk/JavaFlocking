package com.thom.flocking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Boid {

	protected double x, y;
	
	private Vector2 location;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private double maxforce;    	//Maximum steering force.
	private double maxspeed;    	//Maximum speed.
	private float neighbordist;		//Detection range.
	private float separation;		//Separation between two boids.
	//private double rotation;
	
	private int diameter;
	
	//private Vector2 direction;
	//private Vector2 playerPos;
	
	//private double angle;
	//private double scale;

	public Boid(double x, double y) {
		diameter = 32;
		
		this.x = x;
		this.y = y;
		
		acceleration = new Vector2(0, 0);
	    double angle = Math.random() * (Math.PI * 2);
	    velocity = new Vector2(Math.cos(angle), Math.sin(angle));
	    location = new Vector2(x, y);
	    
	    maxspeed = 1.5;
	    maxforce = 0.03;
	    neighbordist = 120;
	    separation = 48;

		//location = new Vector2(x, y);
		//velocity = new Vector2(0, 0);
		//angle = Vector2.deg2rad(36.8);
		//velocity.fromAngle(angle);
		//scale = 0.7;
	}

	public void run(ArrayList<Boid> boids) {
	    flock(boids);
	    update();
	    borders();
	}
	 
	//We accumulate a new acceleration each time based on three rules.
	public void flock(ArrayList<Boid> boids) {
		//double mouseX = Main.mouseX;
		//double mouseY = Main.mouseY;
		
		//Vector2 seek = seek(new Vector2(mouseX, mouseY));
		//System.out.println(mouseX + " , " + mouseY);
		Vector2 sep = separate(boids);
		Vector2 ali = align(boids);
		Vector2 coh = cohesion(boids);
		
		//Arbitrarily weight these forces.
		//seek.multS(1.0);
		sep.multS(1.8);
		ali.multS(1.0);
		coh.multS(1.0);
		
		//Add the force vectors to acceleration.
		//applyForce(seek);
		applyForce(sep);
		applyForce(ali);
		applyForce(coh);
	}

	//Method to update location.
	public void update() {
	    velocity.add(acceleration);
	    
	    //rotation = Vector2.rad2deg(velocity.getAngle());
	    
	    velocity.limit(maxspeed);
	    location.add(velocity);

	    acceleration.multS(0);
	    
	    this.x = location.x;
		this.y = location.y;
	}
	
	public void borders() {
		if(location.x > Main.WIDTH + diameter/2) {
			location.x = -diameter/2;
		}
		if(location.x < -diameter/2) {
			location.x = Main.WIDTH + diameter/2;
		}
		if(location.y > Main.HEIGHT + diameter/2) {
			location.y = -diameter/2;
		}
		if(location.y < -diameter/2) {
			location.y = Main.HEIGHT + diameter/2;
		}
	}
	
	void applyForce(Vector2 force) {
		acceleration.add(force);
	}

	//Here you calculate the steering towards a target.
	public Vector2 seek(Vector2 target) {
		Vector2 targetcopy = new Vector2(target.x, target.y);
        Vector2 desired = targetcopy.sub(location);
        
	    desired.normalize();
	    desired.multS(maxspeed);
	    Vector2 steer = desired.sub(velocity);
	    steer.limit(maxforce);
	    
	    return steer;
	}
	
	//Separation.
	//Method checks for nearby boids and steers away.
	public Vector2 separate(ArrayList<Boid> boids) {
		Vector2 sum = new Vector2(0, 0);
		int count = 0;
	    //For every boid in the system, check if it's too close.
	    for (Boid other : boids) {
	    	double d = location.dist(other.location);
	    	//If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself).
	    	if ((d > 0) && (d < separation)) {
	    		//Calculate vector pointing away from neighbor.
	    		Vector2 locationcopy = new Vector2(location.x, location.y);
	            Vector2 diff = locationcopy.sub(other.location);
	            
	    		diff.normalize();
	    		diff.divS(d);        //Weight by distance.
	    		sum.add(diff);
	    		count++;            //Keep track of how many.
	    	}
	    }
	    
	    //Average -- divide by how many.
	    if (count > 0) {
	    	sum.divS(count);
	    	sum.normalize();
	    }

	    //As long as the vector is greater than 0.
	    if (sum.mag() > 0) {
	    	sum.multS(maxspeed);
	    	Vector2 steer = sum.sub(velocity);
	    	steer.limit(maxforce);
	    }
	    return sum;
	}

	//Alignment.
	//For every nearby boid in the system, calculate the average velocity.
	public Vector2 align(ArrayList<Boid> boids) {
		Vector2 sum = new Vector2(0, 0);
		int count = 0;
	    for (Boid other : boids) {
	    	double d = location.dist(other.location);
	    	if ((d > 0) && (d < neighbordist)) {
	    		sum.add(other.velocity);
	    		count++;
	    	}
	    }
	    if (count > 0) {
	    	sum.divS(count);
	    	sum.normalize();
	    	sum.multS(maxspeed);
	    	Vector2 steer = sum.sub(velocity);
	    	steer.limit(maxforce);
	    	return steer;
	    } 
	    else {
	    	return new Vector2(0, 0);
	    }
	}

	//Cohesion.
	//For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location.
	public Vector2 cohesion(ArrayList<Boid> boids) {
		Vector2 sum = new Vector2(0, 0);
		int count = 0;
	    for (Boid other : boids) {
	    	double d = location.dist(other.location);
	    	if ((d > 0) && (d < neighbordist)) {
	    		sum.add(other.location); // Add location
		        count++;
	    	}
	    }
	    if (count > 0) {
	    	sum.divS(count);
	    	return seek(sum);  //Steer towards the location.
	    } 
	    else {
	    	return new Vector2(0, 0);
	    }
	}

	public void render(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(Color.orange);

		//Here a circle is drawn with 0,0 in the middle of it.
		g2D.fillOval((int)location.x - diameter/2, 
		(int)location.y - diameter/2, diameter, diameter);
		
		/** //Here a triangle is drawn with simple lines.
		Point point2 = new Point((int) location.x - 11, (int) location.y - 10);
		Point point3 = new Point((int) location.x + 15, (int) location.y);
		Point point4 = new Point((int) location.x - 11, (int) location.y + 10);
		
		g2D.setColor(Color.white);
		g2D.drawLine(point2.x, point2.y, point3.x, point3.y);
		g2D.drawLine(point3.x, point3.y, point4.x, point4.y);
		g2D.drawLine(point2.x, point2.y, point4.x, point4.y);
		//g2D.rotate(rotation); **/	
	}
}
