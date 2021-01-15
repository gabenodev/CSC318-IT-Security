/**
 * Puzzle.java models a puzzle object
 * @Author Gabriel Petcu
 * @Version 1.0.0
 */

import javax.crypto.SecretKey;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Puzzle {

    public int puzzleNumber;
    public SecretKey secretKey;

    /**
     * Puzzle constructor which takes an int and a secret key as parameters.
     * @param puzzleNumber representing the number of the puzzle.
     * @param secretKey representing the secret key of the puzzle.
     */
    public Puzzle(int puzzleNumber, SecretKey secretKey)
    {
        this.puzzleNumber = puzzleNumber;
        this.secretKey = secretKey;
    }

    /**
     * getPuzzleNumber which returns the number of the puzzle.
     * @return puzzle number
     */

    public int getPuzzleNumber()
    {
        return this.puzzleNumber;
    }

    /**
     * getKey method which return the secret key of the puzzle.
     * @return secret key of the puzzle
     */

    public SecretKey getKey()
    {
        return this.secretKey;
    }

    /**
     * getPuzzleAsBytes which converts a puzzle of type int to an array of bytes.
     * @return the array of bytes of the puzzle.
     */

    public byte[] getPuzzleAsBytes()
    {
        int puzzleNumber = getPuzzleNumber();
        byte[] zeros = new byte[16];
        byte[] PuzzleToByte =  CryptoLib.smallIntToByteArray(puzzleNumber);
        byte[] key = secretKey.getEncoded();
        int firstLength = zeros.length;
        int secondLength= PuzzleToByte.length;
        int thirdLength = key.length;

        byte [] result1 = new byte[firstLength+secondLength];
        byte [] result2 = new byte [firstLength+secondLength+thirdLength];

        System.arraycopy(zeros,0,result1,0,firstLength);
        System.arraycopy(PuzzleToByte, 0, result1, firstLength, secondLength);
        System.arraycopy(result1,0,result2,0, result1.length);
        System.arraycopy(key, 0, result2, result1.length,thirdLength);
        return result2;

    }


}
