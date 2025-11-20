package bank;

public class Main {

    public static void main(String[] args) {
        HelloBank.info();

        // Mise à jour de l'objet existant avec l'année
        HelloBank b = new HelloBank("EcoBank", "Pau", 2010);
        b.greetCustomer("Alice");

        // Exercice 6.3 : Création du deuxième objet
        HelloBank b2 = new HelloBank("MyBank", "Biarritz", 1998);
        b2.greetCustomer("Bob");
    }
}
