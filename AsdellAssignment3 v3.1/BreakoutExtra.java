/*
 * File: BreakoutExtra.java
 * -------------------
 * Program written by Benjamin Asdell
 * Stanford University CS106A, Copyright 2019
 * Section: Wednesday 5pm
 * TA: Trey Connolly
 * Problem 4 - Extension
 * 
 * This program implements the famous arcade game Breakout, invented by the young Steve Wozniak. This game involves a ball and
 * a player controlled paddle. The objective of the game is to break all the bricks at the top of the screen by using the paddle
 * to bounce the ball against the side walls and collide with the bricks. The name comes from the phenomenon that occurs after an
 * entire column of bricks has been destroyed, allowing the ball to bounce above the top row of bricks and typically break vast
 * quantities of bricks in a single trip. This version allows the player to have a set amount of chances to break the brick wall
 * without having to restart entirely. 
 * 
 * Additional features in this version include a visible score counter at the bottom of the screen, increased ball velocity as more bricks
 * are broken in each life, a bounce sound effect, and behavior for paddle corner to ball bounces. The final score is also displayed
 * at the game over screen, regardless of the outcome of the game.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BreakoutExtra extends GraphicsProgram {

	// Dimensions of the canvas, in pixels
	// These should be used when setting up the initial size of the game,
	// but in later calculations you should use getWidth() and getHeight()
	// rather than these constants for accurate size information.
	public static final double CANVAS_WIDTH = 420;
	public static final double CANVAS_HEIGHT = 600;

	// Number of bricks in each row
	public static final int NBRICK_COLUMNS = 10;

	// Number of rows of bricks
	public static final int NBRICK_ROWS = 10;

	// Separation between neighboring bricks, in pixels
	public static final double BRICK_SEP = 4;

	// Width of each brick, in pixels
	public static final double BRICK_WIDTH = Math.floor(
			(CANVAS_WIDTH - (NBRICK_COLUMNS + 1.0) * BRICK_SEP) / NBRICK_COLUMNS);

	// Height of each brick, in pixels
	public static final double BRICK_HEIGHT = 8;

	// Offset of the top brick row from the top, in pixels
	public static final double BRICK_Y_OFFSET = 20;

	// Dimensions of the paddle
	public static final double PADDLE_WIDTH = 60;
	public static final double PADDLE_HEIGHT = 10;

	// Offset of the paddle up from the bottom 
	public static final double PADDLE_Y_OFFSET = 30;

	// Radius of the ball in pixels
	public static final double BALL_RADIUS = 10;

	// The ball's vertical velocity.
	public static final double VELOCITY_Y = 4.0;

	// The ball's minimum and maximum horizontal velocity; the bounds of the
	// initial random velocity that you should choose (randomly +/-).
	public static final double VELOCITY_X_MIN = 2.0;
	public static final double VELOCITY_X_MAX = 4.0;

	// Animation delay or pause time between ball moves (ms)
	public static final double DELAY = 1000.0 / 60.0;

	// Number of turns 
	public static final int NTURNS = 3;

	//instance object representing an individual brick
	private GRect brick;

	//instance object representing player paddle
	private GRect paddle;

	//instance object representing player ball
	private GOval ball;

	//instance variable values for horizontal and vertical ball velocities, respectively
	private double vx, vy;

	//instanced random number generator for unique velocity calculations
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	//instance variable that tracks player's victory condition, decrements from initial amount of bricks
	private int brickCount = NBRICK_ROWS*NBRICK_COLUMNS;
	
	//instance object representing visual score display
	private GLabel gameScore;
	
	//instance variable representing numerical score related to quantity and position of bricks broken
	private int scoreCount = 0;

	//audio file initialization for ball bounce sound (deprecated in Java SE 9, but used according to assignment)
	@SuppressWarnings("deprecation")
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	//scalar factor for how horizontal velocity increases as more bricks are broken
	private double xDifficulty = 1.5;
	
	//scalar factor for how vertical velocity increases as more bricks are broken
	private double yDifficulty = 1.1;

	public void run() {
		setTitle("CS 106A Breakout");
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		setUpBreakout();
		playBreakout();
		removeAll();
		gameOver();
	}

	/*
	 * (non-Javadoc)
	 * @see acm.program.Program#mouseMoved(java.awt.event.MouseEvent)
	 * This is the mouse listener for mouse movement. This method updates the x-coordinate of the paddle every time the mouse is 
	 * moved horizontally. This method also contains a built-in constraint for the paddle movement, preventing it from following 
	 * the mouse outside the visible canvas. 
	 */
	public void mouseMoved(MouseEvent e) {
		double mouseX = e.getX();
		double rightBound = getWidth() - PADDLE_WIDTH / 2;
		double leftBound = PADDLE_WIDTH / 2;
		if (mouseX < rightBound && mouseX > leftBound) {
			paddle.setCenterLocation(mouseX, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT / 2);
		}
	}
	
	/*
	 * This method displays the various visual elements used in Breakout, including the bricks, paddle, and ball. 
	 */
	private void setUpBreakout() {
		drawGrid(getWidth() / 2, BRICK_Y_OFFSET);
		drawPaddle();
		drawBall();
		gameScore();
	}

	/*
	 * This method is responsible for drawing every brick according to the specified dimensions that can be changed in the 
	 * constants above. While each brick is drawn as an instance of the brick object, only the last brick to be placed (bottom
	 * right) is referred to as "brick" by the getElementAt method. This method also paints each pair of rows (if space allows)
	 * the same color according to the row number and its modulus by 10. The order of colors is red, orange, yellow, green, and cyan
	 * according to the handout, restarting the cycle every 10 rows.
	 */
	private void drawGrid(double centerX, double centerY) {
		for (int row = 0; row < NBRICK_ROWS; row++) {
			for (int column = 0; column < NBRICK_COLUMNS; column++) {
				double xOffset = (NBRICK_COLUMNS*BRICK_WIDTH) / 2 + ((NBRICK_COLUMNS-1)*BRICK_SEP) / 2; //moves first brick to left position since no X_OFFSET is given
				double xGap = column*BRICK_WIDTH + column*BRICK_SEP;
				double x = centerX - xOffset + xGap;
				double yGap = row*BRICK_HEIGHT + row*BRICK_SEP;
				double y = centerY + yGap;
				brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				add(brick);
				if (row % 10 == 0 || row % 10 == 1) { //these conditionals attribute row colors to pairs of numbers in the range 0-9
					brick.setColor(Color.RED);
				}
				else if (row % 10 == 2 || row % 10 == 3) {
					brick.setColor(Color.ORANGE);
				}
				else if (row % 10 == 4 || row % 10 == 5) {
					brick.setColor(Color.YELLOW);
				}
				else if (row % 10 == 6 || row % 10 == 7) {
					brick.setColor(Color.GREEN);
				}
				else if (row % 10 == 8 || row % 10 == 9) {
					brick.setColor(Color.CYAN);
				}
			}
		}
	}

	/*
	 * This method constructs the previously initialized paddle object and adds it to the screen. The original position is centered
	 * horizontally, at a vertical offset from the bottom of the visible canvas.
	 */
	private void drawPaddle() {
		paddle = new GRect((getWidth() / 2) - PADDLE_WIDTH / 2, getHeight() - BRICK_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}

	/*
	 * This method constructs the global instance ball object and adds to the middle of the screen.
	 */
	private void drawBall() {
		double initialX = getWidth() / 2 - BALL_RADIUS;
		double initialY = getHeight() / 2 - BALL_RADIUS;
		ball = new GOval(initialX, initialY, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	/*
	 * This method uses a random number generator to calculate a new starting horizontal velocity each time it is called.
	 * The vertical velocity is always the same within each execution of the run statement, but it can be modified by
	 * changing the value of the corresponding constant above.
	 */
	private void ballVelocity() {
		vy = VELOCITY_Y;
		vx = rgen.nextDouble(VELOCITY_X_MIN,VELOCITY_X_MAX);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}

	/*
	 * This method checks for any collision at any of the four corners of the rectangle containing the ball object. The
	 * method then returns that object to be used in the moveBall method.
	 */
	private GObject getCollidingObject() {
		double ballX = ball.getX();
		double ballY = ball.getY();
		if (getElementAt(ballX, ballY) != null) { //top left corner
			return getElementAt(ballX,ballY);
		}
		else if (getElementAt(ballX + 2*BALL_RADIUS, ballY) != null) { //top right corner
			return getElementAt(ballX + 2*BALL_RADIUS, ballY);
		}
		else if (getElementAt(ballX, ballY + 2*BALL_RADIUS) != null) { //bottom left corner
			return getElementAt(ballX,ballY + 2*BALL_RADIUS);
		}
		else if (getElementAt(ballX + 2*BALL_RADIUS, ballY + 2*BALL_RADIUS) != null) { //bottom right corner
			return getElementAt(ballX + 2*BALL_RADIUS,ballY + 2*BALL_RADIUS);
		}
		else{
			return null;
		}
	}

	/*
	 * This method is the main movement method for the game state. It includes checks for ball to wall collisions, ball to paddle
	 * collisions, and ball to brick collisions.
	 */
	private void moveBall() {
		ball.move(vx, vy);
		if (ball.getX() - vx <= 0 && vx < 0) { //left wall collision
			vx = -vx;
		}
		else if (ball.getX() + vx >= getWidth() - 2*BALL_RADIUS && vx > 0) { //right wall collision 
			vx = -vx;
		}
		else if (ball.getY() - vy <= 0 && vy < 0) { //roof collision
			vy = -vy;
		}
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			double lowerBound = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - 2*BALL_RADIUS;
			double upperBound = lowerBound + BALL_RADIUS / 2;
			double paddleLeftInner = paddle.getX();
			double paddleLeftOuter = paddle.getX() - BALL_RADIUS;
			double paddleRightInner = paddle.getX() + PADDLE_WIDTH;
			double paddleRightOuter = paddle.getX() + PADDLE_WIDTH + BALL_RADIUS;
			if (ball.getY() >= lowerBound && ball.getY() <= upperBound) { //paddle collision with tolerance
				vy = -vy;
				bounceClip.play();
			}
			if (ball.getX() >= paddleLeftOuter && ball.getX() <= paddleLeftInner) { //conditional for corner collision
				vx = -vx;
			}
			else if (ball.getX() >= paddleRightInner && ball.getX() <= paddleRightOuter) {
				vx = -vx;
			}
		}
		//only the bottom right brick is assigned "brick", so this accounts for collision with all bricks
		else if (collider != gameScore && collider != null) {
			remove(collider);
			vy = -vy;
			bounceClip.play();
			brickCount --;
			if (brickCount % 7 == 0) { //"kicker" implementation for increasing game difficulty over time
				vx = xDifficulty*vx;
				vy = yDifficulty*vy;
			}
			int rowCount = 1;
			int scoreIncrement = 10;
			if (brickCount % 10 == 0) { //awards more points for bricks removed when there are less bricks left
				rowCount ++;
			}
			scoreCount += scoreIncrement*rowCount;
			gameScore.setLabel("Score: " + scoreCount); //updates scores after each brick is broken
		}
		pause(DELAY);
	}

	/*
	 * This method is the main game state method for Breakout. It contains the key while loop that controls the continuation
	 * of the game until the player lives are all depleted. The method accounts for a newly generated starting velocity with
	 * each player life, and resets the ball position after a life has been lost. It also contains a break statement to the 
	 * victory condition.
	 */
	private void playBreakout() {
		waitForClick();
		ballVelocity();
		int lifeCount = NTURNS;
		while (lifeCount > 0) {
			moveBall();
			if (ball.getY() >= getHeight()) {
				lifeCount --;
				ball.setCenterLocation(getWidth()/2, getHeight()/2);
				ballVelocity();
				if (lifeCount >= 1) {
					waitForClick();
				}
			}
			else if (brickCount == 0) { //victory condition
				break;
			}
		}
	}

	/*
	 * This method initializes the final statement to the player and adds it to the center of the visible canvas. The 
	 * label displayed changes depending on if the player wins or how close the player was to winning.
	 */
	private void gameOver() {
		GLabel gameOver = new GLabel("", getWidth()/2, getHeight()/2);
		gameOver.setFont("Helvetica");
		if (brickCount == 0) {
			gameOver.setLabel("Congratulations, you broke out!");
			gameOver.setColor(Color.GREEN);
		}
		else {
			gameOver.setLabel("You failed with " + brickCount + " bricks left to break.");
			gameOver.setColor(Color.RED);
		}
		gameOver.setCenterLocation(getWidth()/2, getHeight()/2);
		add(gameOver);
		gameScore.setCenterLocation(getWidth()/2, getHeight()/2 + PADDLE_Y_OFFSET/2);
		add(gameScore);
	}
	
	/*
	 * This method declares the previously initialized gameScore object and adds it to the horizontally centered bottom of the visible
	 * canvas. This method is NOT responsible for the changes in the displayed score as the game progresses.
	 */
	private void gameScore() {
		gameScore = new GLabel("");
		gameScore.setLabel("Score: " + scoreCount);
		gameScore.setFont("Helvetica");
		gameScore.setCenterLocation(getWidth()/2, getHeight() - PADDLE_Y_OFFSET / 2);
		gameScore.setColor(Color.BLACK);
		add(gameScore);
	}
}

