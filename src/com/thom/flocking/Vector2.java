package com.thom.flocking;

public class Vector2 {
	
	// properties
	public double x;
	public double y;
	
	// constructor
	public Vector2(double xx, double yy) {
		this.x = xx;
		this.y = yy;
	}
	
	// methods
	// creators (setters)
	public Vector2 setTo(double xx, double yy) {
		// TODO implement
		return null;
	}
	
	public Vector2 create(Vector2 begin, Vector2 end) {
		// TODO implement
		return null;
	}
	
	public Vector2 fromAngle(double angle) {
		angle *= (Math.PI/180);
		this.x = Math.cos(angle);
		this.y = Math.sin(angle);
		return this;
	}
	
	public Vector2 copy() {
		return new Vector2(this.x, this.y);
	}
	
	// info (getters)
	public double mag() {
		double getal = (x*x) + (y*y);
		double pos = Math.sqrt(getal);
		return pos;
	}
	
	public double magSq() {
		// TODO implement
		return 0;
	}
	
	public double dist(Vector2 other) {
		double p1 = this.x - other.x;
		double p2 = this.y - other.y;
		return Math.sqrt(Math.pow(p1, 2) + Math.pow(p2, 2));
	}
	
	public Vector2 getNormalized() {
		Vector2 t = new Vector2(this.x, this.y);
		t.normalize();
		return t;
	}
	
	public Vector2 normalize() {
		double m = this.mag();
		this.x = this.x / m;
		this.y = this.y / m;
		return this;
	}
	
	public Vector2 limit(double max) {
		if (this.mag() > max) {
			this.normalize();
			this.multS(max);
		}
		return this;
	}
	
	public Vector2 setMag(double magnitude) {
		// TODO implement
		return null;
	}
	
	public double getAngle() {
		double angle = Math.atan2(y, x);
		return angle * (180/Math.PI);
	}
	
	public double getAngleToVector(Vector2 other) {
		// TODO implement
		return 0;
	}
	
	public double dot(Vector2 other) {
		// TODO implement
		return 0;
	}
	
	// manipulators
	public Vector2 rotate(double angle) {
		// TODO implement
		return null;
	}
	
	public Vector2 add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2 sub(Vector2 other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2 mult(Vector2 other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
	
	public Vector2 multS(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public Vector2 div(Vector2 other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public Vector2 divS(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}
	
	public static double rad2deg(double a) {
		return a * (180/Math.PI);
	}
	
	public static double deg2rad(double a) {
		return a * (Math.PI/180);
	}
	
	public String toString() {
		return new String("["+this.x+", "+this.y+"]");
	}
}
