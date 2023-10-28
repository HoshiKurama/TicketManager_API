package com.github.hoshikurama.ticketmanager.api.java.registry.locale;

import com.github.hoshikurama.ticketmanager.api.registry.RegistrationResult;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a registration point for Locale extensions. This should be synchronously called in your plugin's
 * enable function.
 * <p>
 * Note: TicketManager will accept only the first LocaleExtension to be successfully registered. All subsequent
 * LocaleExtension registration requests will be rejected with this reason.
 */
public interface LocaleRegistryJava {
    /**
     * Allows registration via Reflection. This method is the simplest but requires that your primary constructor does
     * not take any arguments. Otherwise, the registry will reject your request.
     * @return whether the request was accepted or rejected (with a reason)
     */
    @NotNull RegistrationResult register(@NotNull Class<? extends LocaleExtensionJava> clazz);

    /**
     * Allows registration with an existing instance. Not as clean or as simple as the Reflection method, but it does offer
     * more control (like if your extension depends on other data).
     * @return whether the request was accepted or rejected (with a reason)
     */
    @NotNull RegistrationResult register(@NotNull LocaleExtensionJava extension);
}