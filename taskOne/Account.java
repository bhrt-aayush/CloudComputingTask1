package taskOne;

import akka.actor.AbstractActor;

public class Account extends AbstractActor {
    private double balance = 100; // Initial balance

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Retrieve.class, this::handleRetrieve)
                .match(Store.class, this::handleStore)
                .build();
    }

    private void handleRetrieve(Retrieve retrieve) {
        System.out.println(String.format("Retrieved: £%.2f", retrieve.getAmount()));
        balance += retrieve.getAmount();
        System.out.println(String.format("New Balance: £%.2f", balance));
    }

    private void handleStore(Store store) {
        if (balance >= store.getAmount()) {
            balance -= store.getAmount();
            System.out.println(String.format("Stored: £%.2f", store.getAmount()));
            System.out.println(String.format("New Balance: £%.2f", balance));
        } else {
            System.out.println("Insufficient balance to store.");
        }
    }
}
