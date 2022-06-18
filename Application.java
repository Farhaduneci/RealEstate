/* -------------------------------------------------------------------------- */
/*                              // You name here                              */
/* -------------------------------------------------------------------------- */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/* ----------------------------- Enum Data Types ---------------------------- */

// Types of users in the system. Privilege level is used to determine the
// access rights of the user.
enum UserType {
  ADMIN,
  SELLER,
  CUSTOMER
}

// Types of commands available to the user, NOT_FOUND is used when the user enters an
// invalid command. This is a special command that is used to handle errors.
enum CommandType {
  HELP,
  REPORT,
  ADD_USER,
  LIST_USERS,
  SEARCH_USER,
  ADD_HOUSE,
  REMOVE_HOUSE,
  SEARCH_HOUSE,
  VIEW_HOUSE,
  OPTIONS, // Show available options to a customer.
  NOT_FOUND
}

/* ---------------------------- Global Variables ---------------------------- */
class Variables {
  public static int HOUSE_ID = 0;
}

/* ------------------------------ Basic Classes ----------------------------- */

// Person class represents a person in the system. A Human being.
class Person {
  private String name;
  private String lastName;
  private String nationalID;
  private String phoneNumber;
  private String address;

  public Person(String name, String lastName, String nationalID, String phoneNumber, String address) {
    this.name = name;
    this.lastName = lastName;
    this.nationalID = nationalID;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return name + " " + lastName;
  }

  public String getNationalID() {
    return nationalID;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getAddress() {
    return address;
  }
}

// User class represents a user in the system. A "Person" with different privileges.
class User extends Person {
  private Enum<UserType> userType;
  private ArrayList<House> houses;
  private boolean hasPurchasedHouse = false;
  private int salary = 0;

  public User(String name, String lastName, String nationalID, String phoneNumber, String address,
      Enum<UserType> userType) {
    super(name, lastName, nationalID, phoneNumber, address);
    this.userType = userType;
    if (userType == UserType.SELLER) {
      this.houses = new ArrayList<House>();
    }
  }

  public User(String name, String lastName, String nationalID, String phoneNumber, String address,
      Enum<UserType> userType, int salary) {
    super(name, lastName, nationalID, phoneNumber, address);
    this.userType = userType;
    if (userType == UserType.SELLER) {
      this.houses = new ArrayList<House>();
    } else if (userType == UserType.CUSTOMER) {
      this.salary = salary;
    }
  }

  public void addHouse(House house) {
    houses.add(house);
  }

  public void removeHouse(int id) {
    for (int i = 0; i < houses.size(); i++) {
      if (houses.get(i).getId() == id) {
        houses.remove(i);
      }
    }
  }

  public House getHouse(int id) {
    for (House house : houses) {
      if (house.getId() == id) {
        return house;
      }
    }
    return null;
  }

  public Enum<UserType> getUserType() {
    return userType;
  }

  public String toString() {
    return getFullName() + " (" + userType + ")";
  }

  public int getSalary() {
    return salary;
  }

  public ArrayList<House> getHouses() {
    return houses;
  }

  public boolean hasPurchasedHouse() {
    return hasPurchasedHouse;
  }
}

class House {
  private int ID;
  private int price;
  private float area;
  private String address;
  private int numberOfRooms;
  private boolean hasParking;
  private boolean purchased = false;
  private int yearOfConstruction;
  private User owner;

  public House(int price, float area, String address, int numberOfRooms, boolean hasParking,
      int yearOfConstruction, User owner) {
    this.ID = Variables.HOUSE_ID++;
    this.price = price;
    this.area = area;
    this.address = address;
    this.numberOfRooms = numberOfRooms;
    this.hasParking = hasParking;
    this.yearOfConstruction = yearOfConstruction;
    this.owner = owner;
  }

  public int getId() {
    return ID;
  }

  public int getPrice() {
    return price;
  }

  public float getArea() {
    return area;
  }

  public String getAddress() {
    return address;
  }

  public int getNumberOfRooms() {
    return numberOfRooms;
  }

  public boolean hasParking() {
    return hasParking;
  }

  public int getYearOfConstruction() {
    return yearOfConstruction;
  }

  public User getOwner() {
    return owner;
  }

  public User changeOwner(User newOwner) {
    User oldOwner = owner;
    owner = newOwner;
    return oldOwner;
  }

  public boolean isPurchased() {
    return purchased;
  }

  public int age() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    return currentYear - yearOfConstruction;
  }

  public void purchase() {
    purchased = true;
  }

  public String toString() {
    return "ID: " + ID + " | Price: " + price + " | Area: " + area + " | Address: " + address
        + " | Number of rooms: " + numberOfRooms + " | Has parking: " + hasParking
        + " | Year of construction: " + yearOfConstruction + " | Owner: " + owner + " | Age: " + age()
        + " | Purchased: " + purchased;
  }
}

/* --------------------------- Application Classes -------------------------- */

// Storage class is used to store all the data in the system.
class Storage {
  private ArrayList<User> users;

  public Storage() {
    users = new ArrayList<User>();
  }

  public void addUser(User user) {
    users.add(user);
  }

  public User getUser(String name, String lastName) {
    for (User user : users) {
      if (user.getName().equals(name) && user.getLastName().equals(lastName)) {
        return user;
      }
    }
    return null;
  }

  public ArrayList<User> getUsers() {
    return users;
  }
}

// Command class is used to represent a command that the user enters.
// It contains the command type and the arguments that the user entered.
class Command {
  private String command;
  private CommandType commandType;
  private String[] arguments;

  public Command(String command) {
    this.command = command;
    try {
      this.commandType = CommandType.valueOf(command.split(" ")[0].toUpperCase());
    } catch (Exception e) {
      this.commandType = CommandType.NOT_FOUND;
    }
    // If the command is not found, the arguments are set to null.
    if (this.commandType == CommandType.NOT_FOUND) {
      this.arguments = null;
    } else {
      this.arguments = command.split(" ");
      this.arguments[0] = this.commandType.toString();
    }
  }

  public String getCommand() {
    return command;
  }

  public String[] getArguments() {
    return arguments;
  }

  public CommandType getCommandType() {
    return commandType;
  }
}

class CommandDispatcher {
  private Storage storage;

  public CommandDispatcher(Storage storage) {
    this.storage = storage;
  }

  public void dispatch(Command command) {
    switch (command.getCommandType()) {
      case HELP:
        this.help();
        break;
      case ADD_USER:
        this.addUser(command.getArguments());
        break;
      case ADD_HOUSE:
        this.addHouse(command.getArguments());
        break;
      case LIST_USERS:
        this.listUsers();
        break;
      case REMOVE_HOUSE:
        this.removeHouse(command.getArguments());
        break;
      case OPTIONS:
        this.options(command.getArguments());
        break;
      case REPORT:
        this.report();
        break;
      default:
        System.out.println("\033[31m" + "Command not found" + "\033[0m");
        break;
    }
  }

  private void help() {
    System.out.println("\033[32m" + "Available commands:" + "\033[0m");
    System.out.println("\033[32m" + "  help" + "\033[0m");

    // ADD_USER <user> <user type> <name> <last name> <national ID> <phone number> <address> <!salary>
    // ADD_HOUSE <user> <owner name> <owner last> <price> <area> <address> <number of rooms> <has parking> <year of construction>
    // REMOVE_HOUSE <user> <owner name> <owner last> <house ID>
    // LIST_USERS
    // OPTIONS <name> <last name>

    System.out.println(
        "\033[32m" + "  add user <user> <user type> <name> <last name> <national ID> <phone number> <address> <!salary>"
            + "\033[0m");
    System.out.println("\033[32m"
        + "  add house <user> <owner name> <owner last> <price> <area> <address> <number of rooms> <has parking> <year of construction>"
        + "\033[0m");
    System.out.println("\033[32m" + "  remove house <user> <owner name> <owner last> <house ID>" + "\033[0m");
    System.out.println("\033[32m" + "  list users" + "\033[0m");
    System.out.println("\033[32m" + "  options <name> <last name>" + "\033[0m");
  }

  // ADD_USER <user> <user type> <name> <last name> <national ID> <phone number> <address> <!salary>
  private void addUser(String[] arguments) {
    String user = arguments[1];

    // Only the "ROOT" user can add users.
    if (!user.equals("ROOT")) {
      System.out.println("\033[31m" + "This user is not allowed to add users" + "\033[0m");
      return;
    }

    String userType = arguments[2];
    String name = arguments[3];
    String lastName = arguments[4];
    String nationalID = arguments[5];
    String phoneNumber = arguments[6];
    String address = arguments[7];

    switch (userType) {
      case "SELLER":
        this.storage.addUser(new User(name, lastName, nationalID, phoneNumber, address, UserType.SELLER));
        break;
      case "CUSTOMER":
        int salary = Integer.parseInt(arguments[8]);
        this.storage.addUser(new User(name, lastName, nationalID, phoneNumber, address, UserType.CUSTOMER, salary));
        break;
    }
    System.out.println("\033[32m" + "User added" + "\033[0m");
  }

  // ADD_HOUSE <user> <owner name> <owner last> <price> <area> <address> <number of rooms> <has parking> <year of construction>
  public void addHouse(String[] arguments) {
    String user = arguments[1];

    // Only the "ROOT" user can add users.
    if (!user.equals("ROOT")) {
      System.out.println("\033[31m" + "This user is not allowed to add houses" + "\033[0m");
      return;
    }

    int price = Integer.parseInt(arguments[4]);
    int area = Integer.parseInt(arguments[5]);
    String address = arguments[6];
    int numberOfRooms = Integer.parseInt(arguments[7]);
    boolean hasParking = Boolean.parseBoolean(arguments[8]);
    int yearOfConstruction = Integer.parseInt(arguments[9]);

    User owner = this.storage.getUser(arguments[2], arguments[3]);
    if (owner == null) {
      System.out.println("\033[31m" + "Owner not found!" + "\033[0m");
      return;
    }

    owner.addHouse(new House(price, area, address, numberOfRooms, hasParking, yearOfConstruction, owner));
    System.out.println("\033[32m" + "House added" + "\033[0m");
  }

  private void listUsers() {
    System.out.println("\033[32m" + "Users:" + "\033[0m");
    for (User user : storage.getUsers()) {
      System.out.println("\033[32m" + "  " + user.toString() + "\033[0m");
    }
  }

  // REMOVE_HOUSE <user> <owner name> <owner last> <house ID>
  private void removeHouse(String[] arguments) {
    String user = arguments[1];

    // Only the "ROOT" user can remove houses.
    if (!user.equals("ROOT")) {
      System.out.println("\033[31m" + "This user is not allowed to remove houses" + "\033[0m");
      return;
    }

    User owner = this.storage.getUser(arguments[2], arguments[3]);
    if (owner == null) {
      System.out.println("\033[31m" + "Owner not found!" + "\033[0m");
      return;
    }

    House house = owner.getHouse(Integer.parseInt(arguments[4]));
    if (house == null) {
      System.out.println("\033[31m" + "House not found!" + "\033[0m");
      return;
    }

    owner.removeHouse(house.getId());
    System.out.println("\033[32m" + "House removed" + "\033[0m");
  }

  // OPTIONS <name> <last name>
  private void options(String[] arguments) {
    User user = this.storage.getUser(arguments[1], arguments[2]);

    if (user == null) {
      System.out.println("\033[31m" + "User not found!" + "\033[0m");
      return;
    }

    if (user.getUserType() != UserType.CUSTOMER) {
      System.out.println("\033[31m" + "This user is not a customer" + "\033[0m");
      return;
    }

    System.out.println("\033[32m" + "Available Houses to Buy:" + "\033[0m");
    for (User seller : storage.getUsers()) {
      if (seller.getUserType() == UserType.SELLER) {
        for (House house : seller.getHouses()) {
          if (house.isPurchased() == false && house.getPrice() <= user.getSalary()) {
            System.out.println("\033[32m" + "  " + house.toString() + "\033[0m");
          }
        }
      }
    }
  }

  private void report() {
    System.out.println("\033[32m" + "Report:" + "\033[0m");
    for (User user : storage.getUsers()) {
      System.out.println("\033[32m" + "  " + user.toString() + "\033[0m");
      for (House house : user.getHouses()) {
        System.out.println("\033[32m" + "    " + house.toString() + "\033[0m");
      }
    }
  }
}
/* -------------------------- Start of the program -------------------------- */

public class Application {
  public static void main(String[] args) {
    Storage storage = new Storage();
    CommandDispatcher dispatcher = new CommandDispatcher(storage);

    User ROOT = new User("ROOT", "ROOT", "1", "1", "Address", UserType.ADMIN);
    storage.addUser(ROOT);

    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Welcome to the real estate system!");
      System.out.println("Type 'help' for a list of commands.");

      while (true) {
        System.out.print("Enter command: ");
        String command = scanner.nextLine();

        if (command.equals("exit")) {
          break;
        } else {
          Command commandObject = new Command(command);
          dispatcher.dispatch(commandObject);
        }
      }
    }
    System.out.println("Goodbye!");
  }
}

/* -------------------------------------------------------------------------- */
/*                              Example Commands                              */
/* -------------------------------------------------------------------------- */

// ADD_USER ROOT SELLER Farhad Uneci 3861167190 09398466645 Hamedan, Mahdie
// ADD_HOUSE ROOT Farhad Uneci 1000000 250 Hamedan 5 false 2000
