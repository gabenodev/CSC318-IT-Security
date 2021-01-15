/**
 * PuzzleCreator.java creates the puzzles and encrypts them.
 * @Author Gabriel Petcu
 * @version 1.0.0
 */

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;


public class PuzzleCreator {

    public ArrayList<Puzzle> list = new ArrayList<Puzzle>(4096);
    public ArrayList<byte[]> encryptedPuzzle = new ArrayList<byte[]>(4096);
    public ArrayList<byte[]> PuzzletoByte = new ArrayList<byte[]>(4096);

    public PuzzleCreator() {

    }

    /**
     * createPuzzles method which creates an arraylist of 4096 puzzles.
     *
     * @return the list of all 4096 puzzles.
     */

    public ArrayList<Puzzle> createPuzzles() {

        for (int i = 0; i < 4096; i++) {
            Puzzle puzzle = null;
            try {
                puzzle = new Puzzle(i, CryptoLib.createKey(createRandomKey()));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            list.add(puzzle);
        }
        return list;
    }

    /**
     * createRandomKey method which creates a random key.
     *
     * @return the key as a byte[] array.
     */

    public byte[] createRandomKey() {
        byte[] zeros = new byte[6];
        byte[] keyDES = new byte[1];
        SecretKey secretKey = new SecretKeySpec(keyDES, "DES");
        byte[] key = secretKey.getEncoded();

        int firstLength = zeros.length;
        int secondLength = keyDES.length;
        int thirdLength = key.length;

        byte[] result1 = new byte[firstLength + secondLength];
        byte[] result2 = new byte[firstLength + secondLength + thirdLength];

        System.arraycopy(zeros, 0, result1, 0, firstLength);
        System.arraycopy(keyDES, 0, result1, firstLength, secondLength);
        System.arraycopy(result1, 0, result2, 0, result1.length);
        System.arraycopy(key, 0, result2, result1.length, thirdLength);
        return result2;
    }

    /**
     * encryptPuzzle method which encripts a puzzle.
     *
     * @param puzzle       represents the puzzle object that needs to be encripted.
     * @param encryptedKey represents a byte array of encrypted key.
     * @return the encrypted puzzle as a byte[] array.
     */

    public byte[] encryptPuzzle(byte[] encryptedKey, Puzzle puzzle) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, CryptoLib.createKey(encryptedKey));
            byte[] cipherByte = new byte[0];
            cipherByte = cipher.doFinal(puzzle.getPuzzleAsBytes());
            return cipherByte;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        return null;

    }

    /**
     * encryptPuzzleToFile which encrypts all the puzzles to a file.
     *
     * @param fileName represents the name of the file.
     * @throws FileNotFoundException in case of not finding the file.
     */

    public void encryptPuzzlesToFile(String fileName) {
      //  File f = new File(fileName);
        FileOutputStream fileOutput = null;
        try {

            fileOutput = new FileOutputStream(fileName);
            for (int i = 0; i < 4096; i++) {

              //  byte[] puzzle = list.get(i).getPuzzleAsBytes();
               // fileOutput.write(puzzle);
                //for(Puzzle puzzles : list)
                encryptedPuzzle.add(encryptPuzzle(list.get(i).getKey().getEncoded(), list.get(i)));
                PuzzletoByte.add(list.get(i).getPuzzleAsBytes()); // Null pointer exception... I'm just giving up
                fileOutput.write(PuzzletoByte.get(i));
                fileOutput.write(encryptedPuzzle.get(i));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      finally {
           try {
               if (fileOutput != null) {
                   fileOutput.close();
               }
           } catch (IOException ioe) {
               System.out.println("Error while closing stream: " + ioe);
           }
    }

}

       /**
        * findKey method that finds the key of a puzzle.
        * @param puzzleNumber reperesents the number of the puzzle.
        * @return the key of the puzzle.
        */
       public SecretKey findKey ( int puzzleNumber){

           for (int i = 0; i < 4096; i++) {
               if (list.get(i).getPuzzleNumber() == puzzleNumber) {
                   return list.get(i).getKey();
               }
           }
           return null;

       }



}
