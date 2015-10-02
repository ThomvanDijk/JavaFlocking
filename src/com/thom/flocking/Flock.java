package com.thom.flocking;

import java.awt.Graphics;
import java.util.ArrayList;

public class Flock {
	
	ArrayList<Boid> boids; 
	
	public Flock() {
		boids = new ArrayList<Boid>();
	}
	
	public void update() {
		for(Boid b : boids) {
			//Passing the entire list of boids to each boid individually.
			b.run(boids);
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
}
