import java.util.*;
import java.io.*;
import java.nio.channels.*;



public class KeyValueDataStoreUI {
	
	
	static String create(KeyValueStorageHandler dataHandler, Scanner in) {
		
		System.out.println("....Creation Initiated");
		
		boolean userWantNextIteration = true;
		
		do {
			userWantNextIteration = false;
			System.out.println("....Enter the Key (The key size should not exceed more than 32)");
			String key = in.next();
			in.nextLine();
			
			System.out.println("....Enter the JSONObject (The size should be always less than 16KB)");
			String value = in.nextLine(); //JSONOject
			
			String message = dataHandler.createNewKeyValuePair(key, value);
			
			System.out.println(message);
			
			
			System.out.println("....To continue creating press 1, To exit press any number except 1");
			String valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				userWantNextIteration = true;
			}
			
		} while (userWantNextIteration);
		
		
		return "Create operation is done";
	}
	
	static String delete(KeyValueStorageHandler dataHandler, Scanner in) {
		
		
		System.out.println("....Deletion Initiated");
		
		boolean userWantNextIteration = false;
		
		do {
			userWantNextIteration = false;
			System.out.println("....Enter the key that has to be deleted");
			
			String key = in.next();
			
			String message = dataHandler.deleteTheKey(key);
			
			System.out.println(message);
			
			System.out.println("....To continue deleting press 1, To exit press any number except 1");
            String valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				userWantNextIteration = true;
			}
		} while (userWantNextIteration);
		
		return "Delete operation is done";
	}
	
	static String read(KeyValueStorageHandler dataHandler, Scanner in) {
		
		System.out.println("....Reading Initiated");
		
		boolean userWantNextIteration = false;
		
		do {
			userWantNextIteration = false;
			System.out.println("Enter the key to display the JSON object");
			
			String key = in.next();
			
			String message = dataHandler.getTheValue(key); 
			
			System.out.println(message);
			
			System.out.println("....To continue reading press 1, To exit press any number except 1");
			String valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				userWantNextIteration = true;
			}
		} while (userWantNextIteration);
		
		
		return "Read operation is done";
	}
	
	
	static void menuOperation(Scanner in, File file) {
		
		boolean userWantNextIteration = false;
		String valuePressed;
		KeyValueStorageHandler dataHandler = new KeyValueStorageHandler(file);
		do {
			userWantNextIteration = false;
			
			System.out.println("..Menu\n");
			System.out.println("..Press 1 to create a new key value pair\n..Press 2 to Read the key\n..Press 3 to delete the key");
			
			char choice = in.next().charAt(0);
			String message = "";
			
			switch (choice) {
			
			case '1' : message = create(dataHandler, in);
			break;
			
			case '2' : message = read(dataHandler, in);
			break;
			
			case '3' : message = delete(dataHandler, in);
			break;
			
			default : message = "Please Enter valid choice";
			break;
			
			}
			
			System.out.println(message);
			
			System.out.println("..Press 1 to continue with the same file\n..To exit or explore other file press any other number except 1");
		
            valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				userWantNextIteration = true;
			}
			
		} while (userWantNextIteration);

	}
	
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		boolean userWantNextIteration = false;
		
		do {
			userWantNextIteration = false;
			String valuePressed;
			
			System.out.println("Enter the file Name that you want to create or access");
			
			String fileName = in.next();
			
			System.out.println("To Enter the file path manually press 1\nTo use default path press any number except 1");
			
			File file;
			valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				
				System.out.println("Enter the filePath:");
				System.out.println("Please use \\\\ instead of \\ while entering filePath\n");
				String filePath = in.next();
				filePath = filePath + "\\" + fileName;
				file = new File(filePath);
			} else {
				file = new File(fileName);
				System.out.println("The file has been created in this path" + file.getAbsolutePath());
			}
			
			
			try {
				RandomAccessFile access = new RandomAccessFile(file, "rw");
//				FileChannel channel = access.getChannel();
//				FileLock lock = channel.tryLock();
				
								

				boolean isFileAccepted = true;
				if (!file.exists()) {
					try {
							
						file.createNewFile();
						
					} catch (IOException excepted) {
						isFileAccepted = false;
						System.out.println("Unable to create a file try again");
					}
				}
					
				if (isFileAccepted) {
					menuOperation(in, file);
				}
					
				
				
//				lock.release();
//				channel.close();
				access.close();
					
			} catch (OverlappingFileLockException e) {
				System.out.println("The mentioned file is accessing by some other program, try again later or close the program which is using the file");
			} catch(Exception e) {
				System.out.println("An Exception error has been occurred while processing, Please try again");
			}
			
			
			System.out.println("To explore or create further file press 1\nTo exit press any number except 1");
			
			valuePressed = in.next();
			
			if (valuePressed.equals("1")) {
				userWantNextIteration = true;
			}
			
			
		} while (userWantNextIteration);
		
		in.close();
	}
	
}
