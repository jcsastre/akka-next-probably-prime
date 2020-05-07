package com.bigprime.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class WorkerBehavior extends AbstractBehavior<WorkerBehavior.Command> {

    @Value
    public static class Command implements Serializable {
        String message;
        ActorRef<ManagerBehavior.Command> sender;
    }

    static final Logger logger = LoggerFactory.getLogger(WorkerBehavior.class);

    private WorkerBehavior(ActorContext<WorkerBehavior.Command> context) {
        super(context);
    }

    public static Behavior<WorkerBehavior.Command> create() {
        return Behaviors.setup(WorkerBehavior::new);
    }

    private BigInteger prime;

    @Override
    public Receive<WorkerBehavior.Command> createReceive() {
        return newReceiveBuilder()
                .onAnyMessage(command -> {
                    if (command.getMessage().equals("start")) {
                        if (prime == null) {
                            final BigInteger bigInteger = new BigInteger(2000, new Random());
                            prime = bigInteger.nextProbablePrime();
                        }
                        final ManagerBehavior.ResultCommand resultCommand = new ManagerBehavior.ResultCommand(prime);
                        command.getSender().tell(resultCommand);
                    }
                    return this;
                })
                .build();
    }
}
