package utils;
import java.util.ArrayList;

import components.*;

public class Generators {
	public static ArrayList<Client> generateClients(int amount) {
		ArrayList<Client> clients = new ArrayList();;
		for (int i = 0; i < amount; i++) {
			Client client = new Client("testName"+i, "testFirstName"+i);
			clients.add(client);
		}
		return clients;
	}
}
