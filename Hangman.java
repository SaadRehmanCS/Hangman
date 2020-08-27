import java.io.*;
import java.util.*;

public class Hangman {

	public static void main(String[] args) throws IOException {

		File dictionary = new File("dictionary.txt");
		File highScores = new File("highScores.txt");
		Scanner input = new Scanner(dictionary);
		Scanner in = new Scanner(System.in);
		PrintWriter output = new PrintWriter(highScores);
		Scanner highScoresInput = new Scanner(highScores);

		ArrayList<String> words = new ArrayList<>();
		ArrayList<highScores> highScoresTxt = new ArrayList<>();

		// reading the words from the dictionary into the arraylist

		while (input.hasNextLine())
			words.add(input.nextLine());

		String continueGame = "";
		int playerNumber = 0;

		// After using a try catch block to start the game off, the while loop continues
		// to run as long as the player wants to play a new game

		System.out.println("Do you want to start a new game? Yes/No");
		boolean move2 = false;
		while (move2 == false) {
			try {
				continueGame = in.nextLine().toUpperCase();
				if (!continueGame.equals("YES") && !continueGame.equals("NO"))
					throw new InputMismatchException("");
				move2 = true;
			} catch (InputMismatchException ax) {
				System.out.println("Please enter \"Yes\" or \"No\"");
			}
		}
		while (continueGame.equals("YES")) {

			// all of these variables are reset each time a new game is started

			int random = 1 + (int) (Math.random() * 178666);
			String randomWord = words.get(random).toUpperCase();

			int length = randomWord.length();

			int guesses = 0;
			char ch = 0;
			String wordCheck = "";
			ArrayList<String> incorrectGuesses = new ArrayList<>();
			char inputLetter = 0;
			String inputWord = "";
			String correctWord = "";
			int points = 0;

			// this while loop is run for the length of a single game

			while (guesses < 8) {

				System.out.print("Hidden word: ");

				// this for loop prints out the hidden word

				String[] hiddenWord = new String[length];
				for (int i = 0; i < length; i++) {
					hiddenWord[i] = " _ ";
				}
				for (int i = 0; i < length; i++) {
					ch = randomWord.charAt(i);
					if (wordCheck.contains(ch + ""))
						System.out.print(ch);
					else
						System.out.print(hiddenWord[i]);
				}
				System.out.println();

				boolean move = false;

				while (move == false) {

					System.out.println("Enter next guess: ");
					try {
						inputWord = in.nextLine().toUpperCase();
						if (inputWord.length() > 1)
							throw new IndexOutOfBoundsException("");
						inputLetter = inputWord.charAt(0);
						if (inputLetter < 'A' || inputLetter > 'Z')
							throw new InputMismatchException("");
						move = true;
					} catch (InputMismatchException ax) {
						System.out.println("Enter a letter please! ");
					} catch (IndexOutOfBoundsException az) {
						System.out.println("Enter one value only! ");
					}
				}

				// this for loop is used to see if two or more instances of a letter are guessed
				// at the same time

				int multipleLetter = 0;
				for (int i = 0; i < length; i++) {
					ch = randomWord.charAt(i);
					if (ch == inputLetter)
						multipleLetter++;
				}

				if (!randomWord.contains(inputWord) && !wordCheck.contains(inputWord)) {
					incorrectGuesses.add(inputLetter + "");
					guesses++;
				}
				if (randomWord.contains(inputWord) && !wordCheck.contains(inputWord)) {
					correctWord += inputLetter;
					if (multipleLetter == 2)
						points += 10;
					if (multipleLetter == 3)
						points += 20;
					if (multipleLetter == 4)
						points += 30;
					if (multipleLetter == 5)
						points += 40;
					if (multipleLetter == 6)
						points += 50;
					points += 10;
				}

				if (!randomWord.contains(inputWord)) {
					System.out.println("\nSorry, there were no " + inputLetter + "'s");
				} else
					System.out.println("\nCorrect guess!");

				wordCheck += inputLetter;
				System.out.println("Guesses left: " + (7 - guesses));
				System.out.println("Score: " + points);

				Collections.sort(incorrectGuesses);

				if (guesses > 6) {
					System.out.println("You have used all of your attempts! Better luck next time.");
					break;
				}

				for (int i = 0; i < length; i++) {
					ch = randomWord.charAt(i);

					if (!correctWord.contains(ch + ""))
						break;
					if (i == length - 1) {
						System.out.println("Congratulations");

						points += 100 + ((7 - guesses) * 30);
						guesses = 8;
						break;
					}

				}

				incorrectGuesses(incorrectGuesses);

			}

			System.out.println("Final score: " + points);
			System.out.println("The correct word was: " + randomWord);

			System.out.println("\n\n\nGame over");

			String playerName = "";
			int counter = 0;

			// if a player is in the all time high scores list, their name and score are
			// added onto the highScoresTxt arraylist, which then writes into the
			// highScores.txt file.

			if (playerNumber < 5) {
				System.out.println("You are in the top 5 all time high scores! Please enter your name");
				playerName = in.next();
				highScoresTxt.add(new highScores(playerName, points));
				output.println(highScoresTxt.get(playerNumber).player + " " + highScoresTxt.get(playerNumber).score);
				playerNumber++;

			} else {
				for (int i = 0; i < highScoresTxt.size(); i++) {
					if (points < highScoresTxt.get(i).score)
						counter++;
				}
				if (counter < 5) {
					System.out.println("You are in the top 5 all time high scores! Please enter your name");
					playerName = in.next();
					highScoresTxt.add(new highScores(playerName, points));
					output.println(highScoresTxt.get(playerNumber).player + " " + highScoresTxt.get(playerNumber).score);
					playerNumber++;


				}
			}



			System.out.println("Do you want to start a new game? Yes/No");
			boolean move3 = false;
			while (move3 == false) {
				try {
					continueGame = in.nextLine().toUpperCase();
					if (!continueGame.equals("YES") && !continueGame.equals("NO"))
						throw new InputMismatchException("");
					move3 = true;
				} catch (InputMismatchException ax) {
					System.out.println("Please enter \"Yes\" or \"No\"");
				}
			}

		}
		output.close();

		System.out.println("High score leaderboard");

		String name = "";
		int score = 0;
		ArrayList<highScores> finalPrintScores = new ArrayList<>();

		// here, the highScores.txt file is read and added onto the finalPrintScores
		// arraylist

		while (highScoresInput.hasNextLine()) {
			try {
				name = highScoresInput.next();
				score = highScoresInput.nextInt();
				finalPrintScores.add(new highScores(name, score));

			} catch (NoSuchElementException ax) {
			}
		}

		Collections.sort(finalPrintScores);

		// in this for loop, the contents of the highScores.txt file are read and
		// printed to the console

		for (int i = 0; i < playerNumber; i++) {
			if (i < 5)
			System.out.printf("%-10s%d\n", finalPrintScores.get(i).player, finalPrintScores.get(i).score);
		}

		System.out.println("\nProgram ends!");

	}

	public static void incorrectGuesses(ArrayList o) {
		System.out.println();
		System.out.print("Incorrect guesses: ");
		for (int i = 0; i < o.size(); i++) {

			System.out.print(o.get(i) + "");
			if (i < o.size() - 1)
				System.out.print(", ");

		}
		System.out.println("\n");
	}

}

class highScores implements Comparable<highScores> {
	protected String player;
	protected int score;

	public highScores(String player, int score) {
		this.player = player;
		this.score = score;
	}

	@Override
	public int compareTo(highScores o) {
		if (score < o.score)
			return 1;
		else if (score > o.score)
			return -1;
		return 0;
	}

}
