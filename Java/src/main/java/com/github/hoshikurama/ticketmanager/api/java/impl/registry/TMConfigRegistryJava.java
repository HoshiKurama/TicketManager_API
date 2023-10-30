package com.github.hoshikurama.ticketmanager.api.java.impl.registry;

import com.github.hoshikurama.ticketmanager.api.impl.TicketManager;
import com.github.hoshikurama.ticketmanager.api.impl.registry.TMConfigRegistry;
import com.github.hoshikurama.ticketmanager.api.java.ConfigExtensionAdapter;
import com.github.hoshikurama.ticketmanager.api.java.registry.config.ConfigExtensionJava;
import com.github.hoshikurama.ticketmanager.api.java.registry.config.ConfigRegistryJava;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected;
import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult.Rejected.Reason;
import org.jetbrains.annotations.NotNull;

public class TMConfigRegistryJava implements ConfigRegistryJava {

    @NotNull
    @Override
    public RegistrationResult register(@NotNull Class<? extends ConfigExtensionJava> clazz) {
        final var realRegistry = (TMConfigRegistry) TicketManager.INSTANCE.getConfigRegistry();

        if (realRegistry.extensionInitialized())
            return new Rejected(Reason.HAS_REGISTRATION.INSTANCE);

        try {
            final var extensionJava = clazz.getDeclaredConstructor().newInstance();
            final var extensionKotlin = new ConfigExtensionAdapter(extensionJava);
            return realRegistry.register(extensionKotlin);
        } catch (NoSuchMethodException e) {
            return new Rejected(Reason.INVALID_CONSTRUCTOR.INSTANCE);
        } catch (Exception e) {
            return new Rejected(new Reason.EXCEPTION_OCCURRED(e));
        }
    }

    @NotNull
    @Override
    public RegistrationResult register(@NotNull ConfigExtensionJava extension) {
        final var extensionKotlin = new ConfigExtensionAdapter(extension);
        return TicketManager.INSTANCE.getConfigRegistry().register(extensionKotlin);
    }
}
