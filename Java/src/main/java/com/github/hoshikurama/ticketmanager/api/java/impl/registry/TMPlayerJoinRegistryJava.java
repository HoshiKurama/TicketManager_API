package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.java.PlayerJoinExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.playerjoin.PlayerJoinExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.playerjoin.PlayerJoinRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import com.github.hoshikurama.ticketmanager.api.registry.playerjoin.PlayerJoinRegistry;
import org.jetbrains.annotations.NotNull;

public class TMPlayerJoinRegistryJava implements PlayerJoinRegistryJava {

    @Override
    public @NotNull RegistrationResult register(@NotNull Class<? extends PlayerJoinExtensionJava> clazz, PlayerJoinRegistry.@NotNull RunType runType) {
        final var realRegistry = TicketManager.INSTANCE.getPlayerJoinRegistry();

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new PlayerJoinExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin, runType);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @Override
    public @NotNull RegistrationResult register(@NotNull PlayerJoinExtensionJava extension, PlayerJoinRegistry.@NotNull RunType runType) {
        final var extensionKotlin = new PlayerJoinExtensionAdapter(extension);
        return TicketManager.INSTANCE.getPlayerJoinRegistry().register(extensionKotlin, runType);
    }
}
