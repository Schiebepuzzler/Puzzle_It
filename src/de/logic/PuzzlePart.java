package de.logic;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PuzzlePart {
	private Bitmap puzzleImage;
	private int[] originPosition;
	private int[] currentPosition;
	
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
		
	}
}
