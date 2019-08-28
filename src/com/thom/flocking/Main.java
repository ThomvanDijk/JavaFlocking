package com.thom.flocking;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -2285453672747000155L;

	public static final int WIDTH = 900, HEIGHT = 800;
	
	private Thread thread;
	private boolean running = false;
	
	private Flock flock;
	private int boids = 0;
	private int FPSCounter;
	private int frames = 0;
	
	public static int mouseX;
	public static int mouseY;
	
	public Main() {
		new Screen(WIDTH, HEIGHT, "Java Flocking", this);
		
		flock = new Flock();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//These x and y are relative to the screen.
			    int x = e.getX();
			    int y = e.getY();
			    addBoid(x, y);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//The game loop.
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				delta--;
			}
			
			if (running) {
				render();
			}
			
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				FPSCounter = frames;
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	//Update function.
	private void update() {
		flock.update();

		if (boids < 100) {
			double randomX = Math.random() * WIDTH;
			double randomY = Math.random() * HEIGHT;
			
			addBoid(randomX, randomY);
		}
	}
	
	public void addBoid(double x, double y) {
		flock.addBoid(new Boid(x, y));
		boids++;
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		flock.render(g);
		this.hud(g);

		g.dispose();
		bs.show();
	}
	
	//Here the heads up display is drawn.
	public void hud(Graphics g) {
		g.setColor(Color.black);
		g.drawString("FPS: " + FPSCounter, 10, 20);
		g.drawString("Boids: " + flock.getNumberOfBoids(), 70, 20);
	}

	public static void main(String[] args) {
		new Main();
	}

}
