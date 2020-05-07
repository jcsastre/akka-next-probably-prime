package com.bigprime.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
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
import java.util.SortedSet;
import java.util.TreeSet;

public class ManagerBehavior extends AbstractBehavior<ManagerBehavior.Command> {

    static final Logger logger = LoggerFactory.getLogger(ManagerBehavior.class);

    public interface Command extends Serializable {};

    @Value
    public static class InstructionCommand implements Command {
        public static final long serialVersionUID = 1L;
        private String message;
    }

    @Value
    public static class ResultCommand implements Command {
        public static final long serialVersionUID = 1L;
        private BigInteger prime;
    }

    private SortedSet<BigInteger> primes = new TreeSet<>();

    private ManagerBehavior(ActorContext<ManagerBehavior.Command> context) {
        super(context);
    }

    public static Behavior<ManagerBehavior.Command> create() {
        return Behaviors.setup(ManagerBehavior::new);
    }

    @Override
    public Receive<ManagerBehavior.Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(InstructionCommand.class, command -> {

                    if (command.getMessage().equals("start")) {
                        for (int i = 0; i <20; i++) {

                            final ActorRef<WorkerBehavior.Command> workerActor =
                                    getContext().spawn(WorkerBehavior.create(), "worker" + i);

                            workerActor.tell(new WorkerBehavior.Command("start", getContext().getSelf()));
                        }
                    }

                    return this;
                })
                .onMessage(ResultCommand.class, command -> {
                    primes.add(command.getPrime());
                    logger.info("(" + getContext().getSelf().path() + ") I have received " + primes.size() + " prime numbers");
                    if (primes.size() == 20) {
                        primes.forEach(prime -> logger.info("(" + getContext().getSelf().path() + ") prime: "+ prime));
                    }
                    return this;
                })
                .build();
    }
}
