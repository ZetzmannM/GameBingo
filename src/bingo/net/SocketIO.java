package bingo.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Class for general Reading/Writing from/to Sockets
 * @author Max
 * @version 1.0.0
 * @since 15.10.2015
 */
public class SocketIO {

	public static int readInteger(InputStream s) throws IOException{
		byte[] fileLengthA = new byte[4];
		s.read(fileLengthA, 0, 4);

		return fileLengthA[0]<<24&0xff000000|
				fileLengthA[1]<<16&0x00ff0000|
				fileLengthA[2]<< 8&0x0000ff00|
				fileLengthA[3]<< 0&0x000000ff;
	}

	public static  int readShort(InputStream s) throws IOException{
		byte[] fileLengthA = new byte[2];
		s.read(fileLengthA, 0, 2);

		return fileLengthA[0]<<8&0xff000|
				fileLengthA[1]<<0&0x00ff;
	}

	public static  double readDouble(InputStream s) throws IOException{
		byte[] arr = new byte[8]; s.read(arr);
		return ByteBuffer.wrap(arr).order(ByteOrder.BIG_ENDIAN).getDouble();
	}

	public static float readFloat(InputStream s) throws IOException{
		byte[] arr = new byte[4]; s.read(arr);
		return ByteBuffer.wrap(arr).order(ByteOrder.BIG_ENDIAN).getFloat();
	}

	public static void writeInteger(int i, OutputStream s) throws IOException{
		s.write(ByteBuffer.allocate(4).putInt(i).array());
	}

	public static void writeShort(short i, OutputStream s) throws IOException{
		s.write(ByteBuffer.allocate(2).putShort(i).array());
	}

	public static void writeDouble(double i, OutputStream s) throws IOException{
		s.write(ByteBuffer.allocate(8).putDouble(i).array());
	}

	public static void writeFloat(float i, OutputStream s) throws IOException{
		s.write(ByteBuffer.allocate(4).putFloat(i).array());
	}

	/**
	 * Reads data out of an inputstream
	 * @param s	InputStream
	 * @param content	read content
	 * @param fileLength	how many bytes should be read
	 * @param printText	whether output should be displayed
	 * @return	whether the transfer succeeded
	 * @throws IOException when something goes wrong
	 */
	public static boolean readAndCheckData(InputStream s, byte[] content, int fileLength, boolean printText) throws IOException {
		if(fileLength > 0){
			int count = 0;
			int countl = 0;
			int len = 0;
			int currLength = 0;
			byte[] content_piece = new byte[8192];

			do{
				if(count == 0){
					len = s.read(content_piece, 0, fileLength>=8192?8192:fileLength);
				}else{
					len = s.read(content_piece, 0, len);
				}
				if(len != -1){
					add(content, content_piece, currLength,len);
					currLength += len;
					if(countl == 10000){
						if(printText){ System.out.println("INFO Fetched Content ... "+(int)(currLength*100f/fileLength*10)/10f+"% {"+currLength+"/"+fileLength+"}");}
						countl = 0;
					}
				}
				countl++;
				if(count ==0){
					count ++;
				}
			}while(len != -1 && !(currLength == fileLength));

			if(printText){ System.out.println("INFO Checking transfered data...");}
			if(currLength != fileLength){
				if(printText){ System.out.println("INFO TRANSFER FAILED!!! {file_length="+fileLength+",received_length"+currLength+"}");}
				return false;
			}
			if(printText){ System.out.println("INFO Transfer succeeded! {file_length==received_length}");}
			return true;
		}
		return true;
	}

	private static void add(byte[] content, byte[] content_piece, int start, int len) {
		for(int i = start; i < start + len; i++){
			content[i] = content_piece[i - start];
		}
	}

	/**
	 *	Writes a string to the OutputStream
	 * @param s	OutputStream
	 * @param string	string
	 */
	public static void writeString(OutputStream s, String string) {
		try {
			writeInteger(string.length(), s);
			s.write(string.getBytes());
			s.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	reads a string from the given Stream
	 * @param stream	stream to read from
	 * @return Read string
	 * @throws IOException if something goes wrong
	 */
	public static String readString(InputStream stream) throws IOException{
		int len = readInteger(stream);
		byte[] cont = new byte[len];
		readAndCheckData(stream, cont, len, false);
		return new String(cont);
	}

	/**
	 * Loads a file into an OutputStream (File has to exist!)
	 * @param path File Path
	 * @param s destination
	 */
	public static void writeFileIntoStream(String path, OutputStream s){
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] pieces = new byte[8192];
			int len;
			while( (len = bis.read(pieces, 0, 8192)) != -1){
				s.write(pieces, 0, len);
				s.flush();
			}
			bis.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the arr into a file at the given path (File Directory has to exist!)
	 * @param path	File path
	 * @param arr File data
	 */
	public static void writeFile(String path, byte[] arr) {
		try {
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(arr);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
