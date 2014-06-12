package de.logic;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PuzzlePart {
	private Bitmap puzzleImage;
	private Bitmap whitePartImage;
	private int[] originPosition;
	private int[] currentPosition;
	private boolean isWhitePart = false;
	
	public PuzzlePart(Bitmap image){
		setPuzzleImage(image);
	}

	/**
	 * @return the puzzleImage
	 */
	public Bitmap getPuzzleImage() {
		return puzzleImage;
	}

	/**
	 * @param puzzleImage the puzzleImage to set
	 */
	public void setPuzzleImage(Bitmap puzzleImage) {
		this.puzzleImage = puzzleImage;
		this.whitePartImage = Bitmap.createBitmap(puzzleImage);
	}

	/**
	 * @return the originPosition
	 */
	public int[] getOriginPosition() {
		return originPosition;
	}

	/**
	 * @param originPosition the originPosition to set
	 */
	public void setOriginPosition(int[] originPosition) {
		this.originPosition = originPosition;
	}

	/**
	 * @return the currentPosition
	 */
	public int[] getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(int[] currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void whiteout() {
		puzzleImage.eraseColor(Color.WHITE);
		isWhitePart = true;
		
	}
	
	public void rewhite() {
		puzzleImage = whitePartImage;
		isWhitePart = false;
		
	}
	
	/**
	 * @return isWhitePart; true if the PuzzlePart is the White one
	 */
	public Boolean isWhitePartSet(){
		return isWhitePart;
	}
	
	/**
	 * @return die UrsprungsPosition als String
	 */
	public String originPositionToString(){
		return ("("+originPosition[0]+", "+originPosition[1]+")");
	}
	
	/**
	 * @return die aktuelle Position als String
	 */
	public String currentPositionToString(){
		return ("("+currentPosition[0]+", "+currentPosition[1]+")");
	}
	
	public Boolean isOnOriginPosition(){
		if (originPosition[0] == currentPosition[0]){
			if (originPosition[1] == currentPosition[1]){
				return true;
			}
		}
		return false;
	}
}
