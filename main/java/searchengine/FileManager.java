
package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

  public FileManager() {
  }

  /**
   * This method returns a byte array "byte[]" that contains the bytes read from the @param.
   * This is done by Files' readAllBytes() which: "Reads all the bytes from a file... 
   * ...The method ensures that the file is closed when all bytes have been read or an I/O error,...
   * ... or other runtime exception, is thrown"
   *  
   * @param filename
   * @return byte[]
   * @throws IOException
   */
  public byte[] getFile(String filename) throws IOException {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }
}