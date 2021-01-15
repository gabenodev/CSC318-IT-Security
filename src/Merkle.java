/**
 * Merkle.java for testing purposes only.
 * @author Gabriel
 * @version 1.0.0
 */
public class Merkle {


    public static void main(String[] args)
    {
        PuzzleCreator puzzles = new PuzzleCreator();
        puzzles.encryptPuzzlesToFile("Puzzle.txt");
        PuzzleCracker puzzleCracker = new PuzzleCracker("Puzzle.txt");
        puzzles.findKey(23);
    }
}
