import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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

enum UserType {
  ADMIN,
  SELLER,
  CUSTOMER
}

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

class CommandDispatcher {
  private RealEstate realEstate;

  public CommandDispatcher(RealEstate realEstate) {
    this.realEstate = realEstate;
  }

  public void dispatch(Command command) {
    command.execute(realEstate);
  }
}

class Command {
  private String command;

  public Command(String command) {
    this.command = command;
  }
}

class RealEstate {
  private HashMap<User, ArrayList<House>> houses;

  public RealEstate() {
    houses = new HashMap<User, ArrayList<House>>();
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

public class Demo {
  public static void main(String[] args) {
    private dispa
  }
}
