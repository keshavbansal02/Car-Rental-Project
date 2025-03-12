import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class car{
    private int car_id;
    private String car_model;
    private String car_brand;
    private double car_charges; // this is rent price of the car per day
    private boolean available;

    public car(int car_id, String car_brand, String car_model, double car_charges){

        this.available = true;
        this.car_charges=car_charges;
        this.car_model=car_model;
        this.car_id=car_id;
        this.car_brand=car_brand;

    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar_model() {
        return car_model;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public double getCar_charges() {
        return car_charges;
    }

    public boolean isAvailable() {
        return available;
    }


    public void rent(){
        this.available=false;
    }

    public void givebackcar(){
        this.available=true;
    }

    public double calculatecharges(int x){
        int days = x;
        return x * car_charges;
    }
}

class customer{

    private String name;
    private long addhar_number;
    private long phone_number;

    public customer(String name,long addhar_number,long phone_number){
        this.phone_number=phone_number;
        this.addhar_number=addhar_number;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public long getAddhar_number() {
        return addhar_number;
    }

    public long getPhone_number() {
        return phone_number;
    }

}

class rental{

    private car car;
    private customer cust;
    private int rental_days;

    public rental(car car, customer cust,int rental_days){
        this.car=car;
        this.cust=cust;
        this.rental_days=rental_days;
    }

    public car getCar() {
        return car;
    }

    public customer getCust() {
        return cust;
    }
}

class car_rental_system {
    private List<car> cars;
    private List<customer> customers;
    private List<rental> rentals;

    public car_rental_system() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void cars_add(car car) {
        cars.add(car);
    }

    public void cust_add(customer cust) {
        customers.add(cust);
    }

    public void rentCar(car car, customer cust, int rental_days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new rental(car, cust, rental_days));
        } else {
            System.out.println("this Car model isn't avaible");
        }
    }

    public void returncar(car car, customer cust, int rental_days){
        car.givebackcar();
        rental rentaltoremove =null;
        for(rental rental: rentals){
            if(rental.getCar()==car){
                rentaltoremove = rental;
                break;
            }
        }
        if (rentaltoremove!=null){
            rentals.remove(rentaltoremove);
            System.out.println("Car Returned successfully");
        }
        else {
            System.out.println("Car was not Rented");
        }

    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("===== Welcome to Car Rental System =====");
            System.out.println("1. You want to rent a Car");
            System.out.println("2. You want to return a Car");
            System.out.println("3. you want to Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            if(choice == 1){
                System.out.println(" ");
                System.out.println("== Rent a Car ==");
                System.out.println("Enter your name: ");

                String customer_name = sc.next();

                System.out.println("Enter your Addhar Number: ");

                long addhar_no =sc.nextLong();

                System.out.println("Enter your phone Number: ");
                long phone_no =sc.nextLong();
                System.out.println("");

                System.out.println("Available Car are");
                for (car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCar_model() + "-" + car.getCar_brand() + "-" + car.getCar_id() + "-" + car.getCar_charges());
                    }
                }

                System.out.println("Enter Car ID You want to rent : ");
                int Car_id = sc.nextInt();

                System.out.println("Enter total day for you want to rent : ");
                int rent_days = sc.nextInt();

                customer Cust1 = new customer(customer_name,addhar_no,phone_no);
                cust_add(Cust1);

                car selected_car = null;
                for( car car : cars){
                    if(car.getCar_id()==Car_id && car.isAvailable()) {
                        selected_car = car;
                        break;
                    }
                }

                if(selected_car!=null){
                    double total_price = selected_car.calculatecharges(rent_days);
                    System.out.println("");
                    System.out.println("=== Rental Information ===");
                    System.out.println("Customer Id :" + Cust1.getAddhar_number() );
                    System.out.println("Customer Name :" + Cust1.getName() );
                    System.out.println("Customer Phone :" + Cust1.getPhone_number() );
                    System.out.println("Car Rented ID:"  + selected_car.getCar_id());
                    System.out.println("Car Model:"  + selected_car.getCar_model());
                    System.out.println("Rented for "+ rent_days + "days");
                    System.out.println("Total Ammount to Pay ( in INR ):"+ total_price );

                    System.out.println("Do you want to Confirm your Booking and pay the amount shown");

                    String confirmation =sc.next();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        rentCar(selected_car, Cust1, rent_days);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("Rental canceled.");
                    }

                }
                else{
                    System.out.println("Invalid car selection or car not available for rent");
                }



            }
            else if (choice ==2){
                System.out.println("=== Return a Car ===");
                System.out.print("Enter the car ID you want to return: ");
                int carId = sc.nextInt();

                car carToReturn = null;
                for (car car : cars) {
                    if (car.getCar_id() == carId && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    customer cust1 = null;
                    for (rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            cust1 = rental.getCust();
                            break;
                        }
                    }

                    if(cust1!=null){
                        carToReturn.givebackcar();
                        System.out.println("Car returned Successfully by : " + cust1.getName());
                    }
                    else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }
                else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }
            else if (choice ==3){
                System.out.println("======= Thank You =======");
                System.out.println("=== Please Visit again===");
                break;
            }
            else {
                System.out.println("Kindly try again with a valid input");
            }
        }
    }
}

    public class Main {
        public static void main(String[] args) {

            car_rental_system rentalSystem = new car_rental_system();

            car car1 = new car(001, "Toyota", "Camry", 600.0);
            car car2 = new car(002, "Honda", "Accord", 800.0);
            car car3 = new car(003, "Mahindra", "Thar", 1200.0);
            car car4 = new car(004, "Tata", "Nexon", 1000.0);

            rentalSystem.cars_add(car1);
            rentalSystem.cars_add(car2);
            rentalSystem.cars_add(car3);
            rentalSystem.cars_add(car4);

            rentalSystem.menu();
        }
    }