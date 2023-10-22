package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.RepeatingTaskExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.repeatingtasks.RepeatingTaskExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.repeatingtasks.RepeatingTaskRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import org.jetbrains.annotations.NotNull;

public class TMRepeatingTaskRegistryJava implements RepeatingTaskRegistryJava {

    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends RepeatingTaskExtensionJava> clazz) {
        final var realRegistry = TicketManager.INSTANCE.getRepeatingTaskRegistry();

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new RepeatingTaskExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull RepeatingTaskExtensionJava extension) {
        final var extensionKotlin = new RepeatingTaskExtensionAdapter(extension);
        return TicketManager.INSTANCE.getRepeatingTaskRegistry().register(extensionKotlin);
    }
}