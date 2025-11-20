package bank;

public class HelloBank {

    private String bankName;
    private String city;
    private int yearFounded; // Exercice 6.2 : Nouvel attribut

    // Exercice 6.2 : Modification du constructeur
    public HelloBank(String name, String city, int year) {
        this.bankName = name;
        this.city = city;
        this.yearFounded = year;
    }

    public void greetCustomer(String customerName) {
        // Exercice 6.2 : Modification de l'affichage
        System.out.println("Bienvenue à " + bankName + " (" + city + "), fondée en " + yearFounded + ".");
        System.out.println("Cher " + customerName + " !");
    }

    public static void info() {
        System.out.println("UPPA Bank Système de démonstration Java.");
    }
}