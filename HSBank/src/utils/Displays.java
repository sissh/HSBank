package utils;
import java.util.ArrayList;
import components.*;


public class Displays {
	public static void displayClients(ArrayList<Client> clients) {
		clients.stream().forEach(System.out::println);
	}

}
