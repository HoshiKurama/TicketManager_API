package com.github.hoshikurama.ticketmanager.api.java.impl;

import com.github.hoshikurama.ticketmanager.api.java.impl.registry.*;

@SuppressWarnings("unused")
public class TicketManagerJava {
    public static TMConfigRegistryJava ConfigRegistry = new TMConfigRegistryJava();
    public static TMDatabaseRegistryJava DatabaseRegistry = new TMDatabaseRegistryJava();
    public static TMLocaleRegistryJava LocaleRegistry = new TMLocaleRegistryJava();
    public static TMPermissionRegistryJava PermissionRegistry = new TMPermissionRegistryJava();
    public static TMPlayerJoinRegistryJava PlayerJoinRegistry = new TMPlayerJoinRegistryJava();
    public static TMPreCommandRegistryJava PreCommandRegistry = new TMPreCommandRegistryJava();
    public static TMRepeatingTaskRegistryJava RepeatingTaskRegistry = new TMRepeatingTaskRegistryJava();
    public static TMEventBusJavaImpl EventBus = new TMEventBusJavaImpl();
}