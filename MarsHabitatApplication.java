
/**
 * This is the main class for the Mars habitat simulation.
 * It handles the overall flow and control of the application.
 * @author TODO: Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.EarthAnimals;
import entities.Entity;
import entities.Entitys;
import entities.MartianAnimals;
import entities.VegetableType;

/**
 * This class is the entry point of your code. This class controls the flow of
 * control for Mission Mars
 */
public class MarsHabitatApplication {

	private final static String DEFAULT_FILE_PATH = "resources/default.in";

	private static MartianLand martianland;
	private static Scanner scanner = new Scanner(System.in);
	private static String logfile = "resources/habitability1.log";// null;

	/**
	 * The main method, entry point of the application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		MarsHabitatApplication habitat = new MarsHabitatApplication();
		habitat.displayMessage();
		int userInputInt;

		/* 0 check args */
		String filename = null;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--f") && i < args.length - 1) {
				filename = args[i + 1];
			} else if (args[i].equals("--l") && i < args.length - 1) {
				logfile = args[i + 1];
			}
		}

		/* 1 Martian Land Map */
		try {
			if (filename == null) {
				System.out.println("Please enter");
				System.out.println("[1] to load Martian map from a file");
				System.out.println("[2] to load default Martian map");
				System.out.print("> ");
				userInputInt = scanNum();
				if (userInputInt == 1) {
					System.out.println("Enter a file name to setup Martian Land Map");
					filename = scanStr();
				} else if (userInputInt == 2) {
					filename = DEFAULT_FILE_PATH;
				} else {
					// error
					return;
				}
			}

			List<String> marianList = readfile(filename);
			martianland = new MartianLand(marianList);

			martianland.show();
			martianland.showHabitabilityStatus();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		} catch (InvalidFileException e) {
			System.out.println(e.getMessage());
			return;
		} catch (UnknownEntityException e) {
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}

		/* 2 Main Mnue */
		Boolean is_loop = true;
		while (is_loop) {
			is_loop = mainMenu();
		}

	}

	/**
	 * Scans user input as integer.
	 *
	 * @return the integer value entered by user
	 */
	private static int scanNum() {
		int userInputInt = -1;
		if (scanner.hasNextLine()) {
			String userInput = scanner.nextLine();
			try {
				userInputInt = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number format.");
			}
		} else {
			System.out.println("No input available.");

		}
		return userInputInt;
	}

	/**
	 * Scans user input as string.
	 *
	 * @return the string entered by user
	 */
	private static String scanStr() {
		System.out.print("> ");
		String userInput = "";
		if (scanner.hasNextLine()) {
			userInput = scanner.nextLine();
		} else {
			return "";
		}
		if (userInput.isEmpty()) {
			return "";
		}
		return userInput;
	}

	/**
	 * Writes Martian land map to file.
	 *
	 * @param fileName file name to write
	 * @throws FileNotFoundException if file not found
	 * @throws IOException           if IO error occurs
	 */
	private static void writefile(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		final List<String> land = martianland.getLand();
		for (String element : land) {
			writer.write(element);
			writer.write("\r\n");
		}
		writer.close();
	}

	/**
	 * Reads Martian land map from file.
	 *
	 * @param fileName file name to read
	 * @return list of strings representing Martian land map
	 * @throws FileNotFoundException  if file not found
	 * @throws InvalidFileException   if file content is invalid
	 * @throws UnknownEntityException if unknown entity found
	 * @throws IOException            if IO error occurs
	 */
	private static List<String> readfile(String fileName)
			throws FileNotFoundException, InvalidFileException, UnknownEntityException, IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			throw new FileNotFoundException("File Not Found, aborting mission.");
		}

		// read the file
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		List<String> marianList = new ArrayList<>();
		while ((line = reader.readLine()) != null) {
			marianList.add(line);
		}
		reader.close();
		return marianList;
	}

	/**
	 * Shows the habitability log from file.
	 *
	 * @param fileName file name of habitability log
	 * @throws FileNotFoundException if log file not found
	 * @throws IOException           if IO error occurs
	 */
	private static void showHabitabilityLog(String fileName) throws FileNotFoundException, IOException {
		System.out.println(fileName);
		File file = new File(fileName);

		if (!file.exists()) {
			throw new FileNotFoundException("File Not Found");
		}

		// read a file
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		int num = 1;
		while ((line = reader.readLine()) != null) {
			if (line.contains("==START==")) {
				System.out.println("Program run :" + num);
				System.out.println("Habitability Status");
				System.out.println("======================");
			} else if (line.contains("SCORE=")) {
				String score = line.substring("SCORE=".length() + 1);
				System.out.println("");
				System.out.println("Total Habitability Score: " + score);
			} else if (line.contains("==END==")) {
				num++;
			} else {
				System.out.println(line);
			}
		}
	}

	/**
	 * Handles plant selection menu for robot action.
	 *
	 * @param robot     the robot entity
	 * @param direction direction robot is facing
	 * @param to_symbol symbol robot wants to move to
	 * @param action    target action position
	 */
	private static void robotPlantMenu(MartianLandPossision robot, Direction direction, char to_symbol,
			Direction action) {

		int plantInput = -1;
		while (plantInput < 0 || 8 < plantInput) {
			System.out.println("Let's Plant something");
			System.out.println("[1] to plant a potato");
			System.out.println("[2] to plant a tomato");
			System.out.println("[3] to plant an onion");
			System.out.println("[4] to plant an apple tree");
			System.out.println("[5] to plant a banana tree");
			System.out.println("[6] to plant a lily");
			System.out.println("[7] to plant a rose");
			System.out.println("[8] to plant a eucalyptus tree");
			System.out.println("[0] to go back to previous menu");
			plantInput = -1;
			while (plantInput < 0 || 8 < plantInput) {
				plantInput = scanNum();
			}
		}
		if (plantInput == 0) {
			// martianland.unMove(robot, direction, to_symbol);
			robot.add(direction);
			martianland.show();
			robotDirectionMenu(robot);
			return;
		} else {
			martianland.plant(plantInput, action);
			robotActionMenu(robot, direction, to_symbol, action);
			return;
		}
	}

	/**
	 * Handles cattle selection menu for robot action.
	 *
	 * @param robot     the robot entity
	 * @param direction direction robot is facing
	 * @param to_symbol symbol robot wants to move to
	 * @param action    target action position
	 */
	private static void robotCattleMenu(MartianLandPossision robot, Direction direction, char to_symbol,
			Direction action) {
		int cattleInput = -1;
		while (cattleInput < 0 || 4 < cattleInput) {
			System.out.println("Let's add some cattle");
			System.out.println("[1] to add a goat");
			System.out.println("[2] to add a sheep");
			System.out.println("[3] to add cow");
			System.out.println("[4] to add a dog");
			System.out.println("[0] to go back to previous menu");
			System.out.print("> ");
			cattleInput = scanNum();
		}
		if (cattleInput == 0) {
			// martianland.unMove(robot, direction, to_symbol);
			martianland.show();
			robot.add(direction);
			robotDirectionMenu(robot);
			return;
		} else if (cattleInput == 4) {
			martianland.putHealth(action.get_x(), action.get_y(), Entitys.Dog.getHealth());
		}
		martianland.cattle(cattleInput, action);
		robotActionMenu(robot, direction, to_symbol, action);
	}

	/**
	 * Handles menu selection for robot action.
	 *
	 * @param robot     the robot entity
	 * @param direction direction robot is facing
	 * @param to_symbol symbol robot wants to move to
	 * @param action    target action position
	 */
	private static void robotActionMenu(MartianLandPossision robot, Direction direction, char to_symbol,
			Direction action) {
		System.out.println("Please select");
		System.out.println("[1] to plant a tree");
		System.out.println("[2] to rear cattle");
		System.out.println("[0] to go back to previous menu");
		int actionInput = -1;
		while (actionInput < 0 || 2 < actionInput) {
			System.out.print("> ");
			actionInput = scanNum();
		}
		if (actionInput == 0) {
			martianland.show();
			robot.add(direction);
			robotDirectionMenu(robot);
			return;
		} else if (actionInput == 1) {
			robotPlantMenu(robot, direction, to_symbol, action);

		} else if (actionInput == 2) {
			robotCattleMenu(robot, direction, to_symbol, action);
		}

	}

	/**
	 * Prints possible movement directions.
	 */
	private static void showMoveDirections() {
		Direction.showDirections();
	}

	private static void robotDirectionMenu(MartianLandPossision robot) {
		int directionInput = -1;
		while (directionInput < 0 || 8 < directionInput) {
			System.out.println("SpaceRobot can move in following directions");
			showMoveDirections();
			System.out.println("Please enter a direction.");
			System.out.print("> ");
			directionInput = scanNum();
		}
		if (directionInput == 0) {
			// mainMenu();
			return;
		}
		Direction direction = new Direction(directionInput);
		char to_symbol = martianland.getSymbol(robot, direction);

		if (to_symbol == '#') {
			System.out.println("Invalid Location, Boundary reached.");
		} else if (to_symbol == '.') {
			martianland.move(robot, direction);

			Direction action = new Direction(0);
			action.set(robot.get_x() + direction.get_x() - 1, robot.get_y() + direction.get_y());
			char left_symbol = martianland.getSymbol(action);
			if (left_symbol == '.') {
				robotActionMenu(robot, direction, to_symbol, action);
			}

		} else {
			Entity entity = martianland.getEntityBySymbol(to_symbol);
			if (entity instanceof VegetableType) {
				System.out.println("Do you want to water the plant?Enter Y for yes, N for No");
				String confirmInput = "";
				while (true) {
					confirmInput = scanStr();
					if (confirmInput.equals("Y") || confirmInput.equals("N")) {
						break;
					}
					System.out.println("Invalid Command");
				}
				if (confirmInput.equals("Y")) {
					System.out.println("You watered a plant. It will grow");
					martianland.addBehaviorScore(1);
				}
				martianland.show();
				robot.add(direction);
				robotDirectionMenu(robot);
				return;
			} else if (entity instanceof MartianAnimals) {
				System.out.println("Do you want to feedr the animals??Enter Y for yes, N for No");
				String confirmInput = "";
				while (true) {
					confirmInput = scanStr();
					if (confirmInput.equals("Y") || confirmInput.equals("N")) {
						break;
					}
					System.out.println("Invalid Command");
				}
				if (confirmInput.equals("Y")) {
					System.out.println("You have fed the cattle. It will grow");
					martianland.addBehaviorScore(2);
				}
				martianland.show();
				robot.add(direction);
				robotDirectionMenu(robot);
				return;
			} else {
				System.out.println("You cannot move to this location.");
				martianland.show();
				robot.add(direction);
				robotDirectionMenu(robot);
				return;
			}

		}
	}

	/**
	 * Handles robot selection and movement menu.
	 */
	private static void robotMenu() {
		martianland.showRobotsMap();
		int robotCount = martianland.getRobotCount();
		int robotInput = 0;
		while (robotInput < 1 || robotInput > robotCount) {
			System.out.print("> ");
			robotInput = scanNum();
		}
		MartianLandPossision robot = martianland.getRobotPosition(robotInput);

		robotDirectionMenu(robot);
	}

	/**
	 * Handles direction input and movement for a rover.
	 *
	 * @param rover the rover entity
	 */
	private static void roverDirectionMenu(MartianLandPossision rover) {
		int directionInput = -1;
		while (directionInput < 0 || 8 < directionInput) {
			System.out.println("SpaceRover can move in following directions");
			showMoveDirections();
			System.out.println("Please enter a direction.");
			System.out.print("> ");
			directionInput = scanNum();
		}
		if (directionInput == 0) {
			// mainMenu();
			return;
		}
		Direction direction = new Direction(directionInput);
		char to_symbol = martianland.getSymbol(rover, direction);
		if (to_symbol == '#') {
			System.out.println("Invalid Location, Boundary reached.");
		} else if (to_symbol == '.') {
			martianland.move(rover, direction); // @TODO
		} else if (to_symbol == Entitys.Rock.getSymbol()) {
			System.out.println("We found a plain rock, Rover will destroy it now.");
			martianland.move(rover, direction); // @TODO
			martianland.addBehaviorScore(1);
		} else if (to_symbol == Entitys.Mineral.getSymbol()) {
			System.out.println("We found a mineral, Rover will collect it now.");
			martianland.move(rover, direction); // @TODO
			martianland.addBehaviorScore(2);
		} else {
			System.out.println("You cannot move to this location.");
			System.out.println(rover);
		}
		martianland.show();
		rover.add(direction);
		roverDirectionMenu(rover);
	}

	/**
	 * Handles rover selection and movement menu.
	 */
	private static void roverMenu() {
		martianland.showRoverMap();
		int roverCount = martianland.getRoverCount();
		int roverInput = 0;
		while (roverInput < 1 || roverInput > roverCount) {
			System.out.print("> ");
			roverInput = scanNum();
		}
		MartianLandPossision rover = martianland.getRooerPosition(roverInput);
		roverDirectionMenu(rover);
	}

	/**
	 * Handles direction input and movement for a Martian animal.
	 *
	 * @param animal the Martian animal entity
	 */
	private static void martianAnimalDirectionMenu(MartianLandPossision animal) {
		int directionInput = -1;
		while (directionInput < 0 || 8 < directionInput) {
			System.out.println("MartianAnimal can move in following directions");
			showMoveDirections();
			System.out.println("Please enter a direction.");
			System.out.print("> ");
			directionInput = scanNum();
		}
		if (directionInput == 0) {
			// mainMenu();
			return;
		}
		Direction direction = new Direction(directionInput);
		char to_symbol = martianland.getSymbol(animal, direction);
		if (to_symbol == '#') {
			System.out.println("Invalid Location, Boundary reached.");
		} else if (to_symbol == '.') {
			martianland.move(animal, direction); // @TODO
			martianland.moveHealth(animal, direction);
		} else {
			Entity entity = martianland.getEntityBySymbol(to_symbol);
			int x = animal.get_x() + direction.get_x();
			int y = animal.get_y() + direction.get_y();
			if (entity instanceof VegetableType) {
				System.out.println("The plantation are eaten by the martian animals.");
				martianland.move(animal, direction);
				martianland.moveHealth(animal, direction);
				martianland.addHealth(x, y, 2);

			} else if (entity.getSymbol() == Entitys.Dog.getSymbol()) {
				System.out.println("Martian animal and Dog has entered a fight");

				int animalHealth = martianland.getHealth(animal.get_x(), animal.get_y());
				int dogHealth = martianland.getHealth(x, y);
				// @TODO
				martianland.removeHealth(animal.get_x(), animal.get_y());
				martianland.removeHealth(x, y);
				while (animalHealth > 0 && dogHealth > 0) {
					dogHealth -= 2;
					System.out
							.println("Martian Animal attacked dog. Health of dog reduced by 2, Present Health: "
									+ dogHealth);
					if (dogHealth <= 0) {
						System.out.println("Dog died");
						System.out.println();
						martianland.putHealth(x, y, animalHealth);
						martianland.rewriteMapChar(x, y, '.');
						break;
					} else {
						animalHealth -= 2;
						System.out.println(
								"Dog attacked Martian Animal. Martian Animal's health reduced by 2, Present Health: "
										+ animalHealth);
						if (animalHealth <= 0) {
							System.out.println("Martian Animal died");
							martianland.putHealth(x, y, dogHealth);
							martianland.rewriteMapChar(x, y, to_symbol);
							martianland.addBehaviorScore(7);
						}
					}
				}

			} else if (entity instanceof EarthAnimals) {
				System.out.println("The cattle are killed by the martian animals.");
				martianland.move(animal, direction); // @TODO
				martianland.moveHealth(animal, direction);
				martianland.addHealth(x, y, 2);
			} else {
				System.out.println("You cannot move to this location.");
			}

		}
		martianland.show();
		animal.add(direction);
		martianAnimalDirectionMenu(animal);
	}

	/**
	 * Handles Martian animal selection and movement menu.
	 */
	private static void martianAnimalMenu() {
		martianland.showMartianAnimals();
		int animalCount = martianland.getMartianAnimalCount();
		int animalInput = 0;
		while (animalInput < 1 || animalInput > animalCount) {
			System.out.print("> ");
			animalInput = scanNum();
		}
		MartianLandPossision animal = martianland.getAnimal(animalInput);
		martianAnimalDirectionMenu(animal);
	}

	/**
	 * Handles main menu input from user.
	 *
	 * @return false if user selects to exit, true otherwise
	 */
	private static Boolean mainMenu() {
		System.out.println("Please enter");
		System.out.println("[1] to move Space Robot");
		System.out.println("[2] to move Space Rover");
		System.out.println("[3] to move Martian animals");
		System.out.println("[4] to print the current habitability stats");
		System.out.println("[5] to print the old habitability stats");
		System.out.println("[6] to exit");
		int mainInput = 0;
		while (mainInput < 1 || 6 < mainInput) {
			System.out.print("> ");
			mainInput = scanNum();
		}
		if (mainInput == 1) {
			robotMenu();

		} else if (mainInput == 2) {
			roverMenu();
		} else if (mainInput == 3) {
			martianAnimalMenu();

		} else if (mainInput == 4) {
			martianland.showHabitabilityStatus();
		} else if (mainInput == 5) {
			try {
				showHabitabilityLog(logfile);
			} catch (Exception e) {
				// error?
			}
		} else if (mainInput == 6) {
			// save OK
			System.out.println("Enter a filename for saving Martian Land Map");
			String userInput = scanStr();
			if (userInput.length() > 0) {
				try {
					writefile(userInput);
				} catch (Exception e) {
					System.out.println("Cannot create file for Martian Land Map.");
				}
			} else {
				System.out.println("Cannot create file for Martian Land Map.");
			}
			System.out.println("Terminating the mission for now. See you next time.");
			return false;
		}
		return true;

	}

	/**
	 * This method prints the starting welcome message. Do not change this code
	 */
	private void displayMessage() {
		System.out.println("         __\n" +
				" _(\\    |@@|\n" +
				"(__/\\__ \\--/ __\n" +
				"   \\___|----|  |   __\n" +
				"       \\ }{ /\\ )_ / _\\\n" +
				"       /\\__/\\ \\__O (_COMP90041\n" +
				"      (--/\\--)    \\__/\n" +
				"      _)(  )(_\n" +
				"     `---''---`");
		System.out.println(
				" /$$      /$$ /$$                    /$$                           /$$      /$$                              \n"
						+
						"| $$$    /$$$|__/                   |__/                          | $$$    /$$$                              \n"
						+
						"| $$$$  /$$$$ /$$  /$$$$$$$ /$$$$$$$ /$$  /$$$$$$  /$$$$$$$       | $$$$  /$$$$  /$$$$$$   /$$$$$$   /$$$$$$$\n"
						+
						"| $$ $$/$$ $$| $$ /$$_____//$$_____/| $$ /$$__  $$| $$__  $$      | $$ $$/$$ $$ |____  $$ /$$__  $$ /$$_____/\n"
						+
						"| $$  $$$| $$| $$|  $$$$$$|  $$$$$$ | $$| $$  \\ $$| $$  \\ $$      | $$  $$$| $$  /$$$$$$$| $$  \\__/|  $$$$$$ \n"
						+
						"| $$\\  $ | $$| $$ \\____  $$\\____  $$| $$| $$  | $$| $$  | $$      | $$\\  $ | $$ /$$__  $$| $$       \\____  $$\n"
						+
						"| $$ \\/  | $$| $$ /$$$$$$$//$$$$$$$/| $$|  $$$$$$/| $$  | $$      | $$ \\/  | $$|  $$$$$$$| $$       /$$$$$$$/\n"
						+
						"|__/     |__/|__/|_______/|_______/ |__/ \\______/ |__/  |__/      |__/     |__/ \\_______/|__/      |_______/ ");

		System.out.println();
	}

}
