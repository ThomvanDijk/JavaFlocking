package com.thom.flocking;

import java.awt.Graphics;
import java.util.ArrayList;

public class Flock {
	
	private ArrayList<Boid> boids;
	
	public Flock() {
		boids = new ArrayList<Boid>();
	}
	
	public void update() {
		// Save the list of boids to prevent list manipulation in the for loop
		ArrayList<Boid> oldList = new ArrayList<Boid>(boids);
		
		for(Boid b: oldList) {
			// Passing the entire list of boids to each boid individually
			b.run(oldList);
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < boids.size(); i++) {
			Boid tempBoid = boids.get(i);
			
			tempBoid.render(g);
		}
	}
	
	public void addBoid(Boid b) {
		boids.add(b);
	}
	
	public int getNumberOfBoids() {
		return boids.size();
	}
}
