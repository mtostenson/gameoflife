package com.michaelt.gol;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

import com.michaelt.gol.Cell.CELL_STATE;

@SuppressWarnings("serial")
public class Grid extends JPanel {
	
	private boolean prev[][];
	private boolean  cur[][];
	
	public Cell cells[][];	
	public Grid() {
		setSize(500, 500);
		cells = new Cell[50][50];
		prev = new boolean[50][50];
		cur = new boolean[50][50];
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				cells[i][j] = new Cell();
//				cur[i][j] = false;
//				prev[i][j] = false;
				Random rand = new Random();
				if(rand.nextInt(3) == 0) {
					cells[i][j].state = CELL_STATE.ON;
				}
				
			}
		}
//		cells[24][24].state = CELL_STATE.ON;
//		cells[25][24].state = CELL_STATE.ON;
//		cells[25][25].state = CELL_STATE.ON;
//		cells[24][25].state = CELL_STATE.ON;
	}	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				switch(cells[i][j].state)
				{	
					case FLAG_OFF:
						cells[i][j].state = CELL_STATE.OFF;
					case OFF:						
						g.clearRect(i*10,j*10,10,10);
						break;
					case FLAG_ON:
						cells[i][j].state = CELL_STATE.ON;
					case ON:
						g.fillRect(i*10,j*10,10,10);
						break;						
				}
			}
		}		
	}
	public int total_neighbors(int x, int y) {		
		int count = 0;		
		try {
			for(int i = -1; i <= 1; i++) {
				if(cells[x+i][y-1].state == CELL_STATE.ON || 
					cells[x+i][y-1].state == CELL_STATE.FLAG_OFF) {
					count++;
				}
				if(cells[x+i][y+1].state == CELL_STATE.ON || 
					cells[x+i][y+1].state == CELL_STATE.FLAG_OFF) {
					count++;
				}
			}
			if(cells[x-1][y].state == CELL_STATE.ON ||
				cells[x-1][y].state == CELL_STATE.FLAG_OFF) {
				count++;
			}
			if(cells[x+1][y].state == CELL_STATE.ON || 
				cells[x+1][y].state == CELL_STATE.FLAG_OFF) {
				count++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			// Do nothing
		}		
		return count;
	}
	public void iterate() {
	   for(int i = 0; i < 50; i++) {
	   	for(int j = 0; j < 50; j++) {
	   		cur[i][j] = cells[i][j].state == CELL_STATE.ON; 
	   		int neighbors = total_neighbors(i,j);
	   		CELL_STATE cur_state = cells[i][j].state;
				if(neighbors == 3 && cur_state == CELL_STATE.OFF) {
					cells[i][j].state = CELL_STATE.FLAG_ON;
				}
				else if(neighbors > 3 && cur_state == CELL_STATE.ON) {
					cells[i][j].state = CELL_STATE.FLAG_OFF;
				}
				else if(neighbors < 2 && cur_state == CELL_STATE.ON) {
					cells[i][j].state = CELL_STATE.FLAG_OFF;
				}
	   	}
	   }
   	if(Arrays.deepEquals(prev, cur))
   		System.err.println("IDENTICAL!");
   	prev = cur;
	   repaint();
   }	
}
