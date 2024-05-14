package taskOne;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("BankSystem");
        ActorRef bankAccount = system.actorOf(Props.create(Account.class), "bankAccount"); 
        // Print initial balance
        System.out.println("Initial Balance: £100");

        Random random = new Random();
        int transactionCount = 0;
        for (int i = 0; i < 10; i++) {
            double amount = random.nextDouble() * 2000 - 1000; 
            if (amount > 0) {
                bankAccount.tell(new Store(amount), ActorRef.noSender());
            } else {
                bankAccount.tell(new Retrieve(-amount), ActorRef.noSender());
            }
            transactionCount++;
        }

        // Adding a shutdown hook to terminate the system gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            system.terminate();
            System.out.println("Program Ended");
        }));
    }
}
