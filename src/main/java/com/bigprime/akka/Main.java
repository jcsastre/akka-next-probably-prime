package com.bigprime.akka;

import akka.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {
        final ActorSystem<String> actorSystem =
                ActorSystem.create(FirstSimpleBehavior.create(), "FirstActorSystem");
        actorSystem.tell("Hello, are you here?");
        actorSystem.tell("This is the second message");
    }
}
