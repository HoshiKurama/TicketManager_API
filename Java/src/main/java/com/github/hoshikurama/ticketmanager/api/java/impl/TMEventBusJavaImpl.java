package com.github.hoshikurama.ticketmanager.api.java.impl;

import com.github.hoshikurama.ticketmanager.api.events.*;
import com.github.hoshikurama.ticketmanager.api.impl.TMEventBus;
import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.events.TMEventBusJava;
import com.github.hoshikurama.ticketmanager.api.java.EventListenerAdapter;

import java.util.UUID;
import java.util.function.Consumer;

public class TMEventBusJavaImpl implements TMEventBusJava {

    @Override
    @SuppressWarnings("unchecked")
    public <Event extends TMEvent> Runnable subscribe(Class<Event> eventClass, Consumer<Event> listener) {
        final var eventListener = EventListenerAdapter.INSTANCE.create(listener);
        final var internal = TicketManager.INSTANCE.getEventBus().getInternal();
        final var uuid = UUID.randomUUID();

        if (eventClass.isAssignableFrom(TicketCreateEvent.class))
            internal.getTicketCreate().put(uuid, (TMEventBus.Internal.EventListener<TicketCreateEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketAssignEvent.class))
            internal.getTicketAssign().put(uuid, (TMEventBus.Internal.EventListener<TicketAssignEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketReopenEvent.class))
            internal.getTicketReopen().put(uuid, (TMEventBus.Internal.EventListener<TicketReopenEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketCommentEvent.class))
            internal.getTicketComment().put(uuid, (TMEventBus.Internal.EventListener<TicketCommentEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketMassCloseEvent.class))
            internal.getTicketMassClose().put(uuid, (TMEventBus.Internal.EventListener<TicketMassCloseEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketSetPriorityEvent.class))
            internal.getTicketSetPriority().put(uuid, (TMEventBus.Internal.EventListener<TicketSetPriorityEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketCloseWithCommentEvent.class))
            internal.getTicketCloseWithComment().put(uuid, (TMEventBus.Internal.EventListener<TicketCloseWithCommentEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketCloseWithoutCommentEvent.class))
            internal.getTicketCloseWithoutComment().put(uuid, (TMEventBus.Internal.EventListener<TicketCloseWithoutCommentEvent>) eventListener);
        if (eventClass.isAssignableFrom(TicketReadReceiptEvent.class))
            internal.getTicketReadReceipt().put(uuid, (TMEventBus.Internal.EventListener<TicketReadReceiptEvent>) eventListener);

        return () -> internal.getAllEvents().forEach((map) -> map.remove(uuid));
    }

    @Override
    public Runnable subscribe(Consumer<TMEvent> listener) {
        return subscribe(TMEvent.class, listener);
    }
}