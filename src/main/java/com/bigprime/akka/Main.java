package com.bigprime.akka;

import akka.actor.typed.ActorSystem;

public class Main {

    public static void main(String[] args) {

        final ActorSystem<ManagerBehavior.Command> bigPrimesSystem =
                ActorSystem.create(ManagerBehavior.create(), "bigPrimesSystem");

        bigPrimesSystem.tell(new ManagerBehavior.InstructionCommand("start"));
    }
}
