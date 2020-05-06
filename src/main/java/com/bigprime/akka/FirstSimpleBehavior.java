package com.bigprime.akka;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstSimpleBehavior extends AbstractBehavior<String> {

    static final Logger LOG = LoggerFactory.getLogger(FirstSimpleBehavior.class);

    private FirstSimpleBehavior(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> create() {
        return Behaviors.setup(FirstSimpleBehavior::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onAnyMessage(message -> {
                    LOG.info("I received the message: " + message);
                    return this;
                })
                .build();
    }
}
