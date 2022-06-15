/* -------------------------------------------------------------------------- */
/*                              // You name here                              */
/* -------------------------------------------------------------------------- */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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
  ADD_USER,
  ADD_PROPERTY,
  REMOVE_PROPERTY,
  SEARCH_PROPERTY,
  VIEW_PROPERTY,
  NOT_FOUND
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

  public User(String name, String lastName, String nationalID, String phoneNumber, String address,
      Enum<UserType> userType) {
    super(name, lastName, nationalID, phoneNumber, address);
    this.userType = userType;
  }

  public Enum<UserType> getUserType() {
    return userType;
  }
}

class House {
  private int price;
  private float area;
  private String address;
  private int numberOfRooms;
  private boolean hasParking;
  private int yearOfConstruction;

  public House(int price, float area, String address, int numberOfRooms, boolean hasParking,
      int yearOfConstruction) {
    this.price = price;
    this.area = area;
    this.address = address;
    this.numberOfRooms = numberOfRooms;
    this.hasParking = hasParking;
    this.yearOfConstruction = yearOfConstruction;
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

  public int age() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    return currentYear - yearOfConstruction;
  }
}

class RealEstate {
  private HashMap<User, ArrayList<House>> houses;

  public RealEstate() {
    houses = new HashMap<User, ArrayList<House>>();
  }
}

/* --------------------------- Application Classes -------------------------- */

class Storage {
  private ArrayList<User> users;
  private ArrayList<House> houses;

  public Storage() {
    users = new ArrayList<User>();
    houses = new ArrayList<House>();
  }

  public void addUser(User user) {
    users.add(user);
  }

  public void addHouse(House house) {
    houses.add(house);
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public ArrayList<House> getHouses() {
    return houses;
  }

  public Iterator<User> getUsersIterator() {
    return new Iterator<User>() {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < users.size();
      }

      @Override
      public User next() {
        return users.get(currentIndex++);
      }
    };
  }

  public Iterator<House> getHousesIterator() {
    return new Iterator<House>() {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < houses.size();
      }

      @Override
      public House next() {
        return houses.get(currentIndex++);
      }
    };
  }
}

class Command {
  private String command;
  private CommandType commandType;
  private String[] arguments;

  public Command(String command) {
    this.command = command;
    try {
      this.commandType = CommandType.valueOf(command.split(" ")[1]);
    } catch (Exception e) {
      this.commandType = CommandType.NOT_FOUND;
    }
    this.arguments = command.split(" ");
  }

  public String getCommand() {
    return command;
  }

  public CommandType getCommandType() {
    return commandType;
  }
}

class CommandDispatcher {
  private RealEstate realEstate;
  private Storage storage;

  public CommandDispatcher(RealEstate realEstate, Storage storage) {
    this.realEstate = realEstate;
    this.storage = storage;
  }

  public void dispatch(Command command) {
    switch (command.getCommandType()) {
      case ADD_PROPERTY:
        break;
      case REMOVE_PROPERTY:
        break;
      case ADD_USER:
        break;
      default:
        System.out.println("Command not found");
        break;
    }
  }
}

/* -------------------------- Start of the program -------------------------- */

public class Application {
  public static void main(String[] args) {
    Storage storage = new Storage();
    RealEstate realEstate = new RealEstate();
    CommandDispatcher dispatcher = new CommandDispatcher(realEstate, storage);

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
