package com.thom.flocking;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Screen extends Canvas {

	private static final long serialVersionUID = 6780782201615456618L;

	public Screen(int width, int height, String title, Main main) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(main);
		frame.setVisible(true);
		main.start();
	}
	
}
