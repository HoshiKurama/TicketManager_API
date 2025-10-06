package com.github.hoshikurama.ticketmanager.api.registry.locale

@Suppress("Unused")
/**
 * Describes the Strings that any Locale instance MUST provide to TicketManager
 */
interface Locale {
// Core locale file fields
    // Miscellaneous
    val consoleName: String
    val miscNobody: String
    val wikiLink: String

    // Command Types
    val commandBase: String
    val commandWordAssign: String
    val commandWordSilentAssign: String
    val commandWordClaim: String
    val commandWordSilentClaim: String
    val commandWordClose: String
    val commandWordSilentClose: String
    val commandWordCloseAll: String
    val commandWordSilentCloseAll: String
    val commandWordComment: String
    val commandWordSilentComment: String
    val commandWordCreate: String
    val commandWordHelp: String
    val commandWordHistory: String
    val commandWordList: String
    val commandWordListAssigned: String
    val commandWordListUnassigned: String
    val commandWordReopen: String
    val commandWordSilentReopen: String
    val commandWordSearch: String
    val commandWordSetPriority: String
    val commandWordSilentSetPriority: String
    val commandWordTeleport: String
    val commandWordVersion: String
    val commandWordUnassign: String
    val commandWordSilentUnassign: String
    val commandWordView: String
    val commandWordDeepView: String
    val commandWordReload: String

    // Search words:
    val searchAssigned: String
    val searchCreator: String
    val searchKeywords: String
    val searchPriority: String
    val searchStatus: String
    val searchTime: String
    val searchWorld: String
    val searchPage: String
    val searchClosedBy: String
    val searchLastClosedBy: String
    val searchTimeSecond: String
    val searchTimeMinute: String
    val searchTimeHour: String
    val searchTimeDay: String
    val searchTimeWeek: String
    val searchTimeYear: String

    // Required or Optional
    val parameterID: String
    val parameterAssignment: String
    val parameterLowerID: String
    val parameterUpperID: String
    val parameterComment: String
    val parameterPage: String
    val parameterLevel: String
    val parameterUser: String
    val parameterConstraints: String
    val parameterNewSearchIndicator: String

    // Literals for Assignment suggestions
    val parameterLiteralPlayer: String
    val parameterLiteralGroup: String
    val parameterLiteralPhrase: String

    // Console Logging Messages
    val consoleInitializationComplete: String
    val consoleErrorBadDatabase: String
    val consoleWarningInvalidConfigNode: String
    val consoleErrorScheduledNotifications: String
    val consoleErrorCommandExecution: String
    val consoleDatabaseLoaded: String
    val consoleDatabaseWaitStart: String

// Visual Player-Modifiable Values
    // Priority
    val priorityLowest: String
    val priorityLow: String
    val priorityNormal: String
    val priorityHigh: String
    val priorityHighest: String
    val priorityColourLowestHex: String
    val priorityColourLowHex: String
    val priorityColourNormalHex: String
    val priorityColourHighHex: String
    val priorityColourHighestHex: String

    // Status
    val statusOpen: String
    val statusClosed: String
    val statusColourOpenHex: String
    val statusColourClosedHex: String

    // Time
    val timeSeconds: String
    val timeMinutes: String
    val timeHours: String
    val timeDays: String
    val timeWeeks: String
    val timeYears: String

    // Click Events
    val clickTeleport: String
    val clickViewTicket: String
    val clickNextPage: String
    val clickBackPage: String
    val clickWiki: String

    // Pages
    val pageActiveNext: String
    val pageInactiveNext: String
    val pageActiveBack: String
    val pageInactiveBack: String
    val pageFormat: String

    // Warnings
    val warningsLocked: String
    val warningsNoConfig: String
    val warningsUnexpectedError: String
    val warningsLongTaskDuringReload: String
    val warningsInvalidConfig: String
    val warningsInternalError: String

    //Brigadier Warnings
    val brigadierNotYourTicket: String
    val brigadierInvalidID: String
    val brigadierTicketAlreadyClosed: String
    val brigadierTicketAlreadyOpen: String
    val brigadierConsoleLocTP: String
    val brigadierNoTPSameServer: String
    val brigadierNoTPDiffServer: String
    val brigadierNoTPProxyDisabled: String
    val brigadierOtherHistory: String
    val brigadierSearchBadSymbol1: String
    val brigadierSearchBadStatus: String
    val brigadierSearchBadSymbol2: String
    val brigadierSearchBadSymbol3: String
    val brigadierBadPageNumber: String
    val brigadierBadSearchConstraint: String
    val brigadierInvalidAssignment: String
    val brigadierInvalidTimeUnit: String
    val brigadierInvalidPriority: String
    val brigadierInvalidPlayerName: String

    // View and Deep View
    val viewHeader: String
    val viewSep1: String
    val viewCreator: String
    val viewAssignedTo: String
    val viewPriority: String
    val viewStatus: String
    val viewLocation: String
    val viewSep2: String
    val viewComment: String
    val viewDeepComment: String
    val viewDeepSetPriority: String
    val viewDeepAssigned: String
    val viewDeepReopen: String
    val viewDeepClose: String
    val viewDeepMassClose: String

    // List
    val listHeader: String
    val listAssignedHeader: String
    val listUnassignedHeader: String
    val listEntry: String
    val listFormattingSize: Int
    val listMaxLineSize: Int

    // Search Format:
    val searchHeader: String
    val searchEntry: String
    val searchQuerying: String
    val searchFormattingSize: Int
    val searchMaxLineSize: Int

    // History Format:
    val historyHeader: String
    val historyEntry: String
    val historyFormattingSize: Int
    val historyMaxLineSize: Int

    // Help Format:
    val helpHeader: String
    val helpLine1: String
    val helpLine2: String
    val helpLine3: String
    val helpSep: String
    val helpHasSilence: String
    val helpLackSilence: String
    val helpRequiredParam: String
    val helpOptionalParam: String
    val helpEntry: String

    // Help Explanations
    val helpExplanationAssign: String
    val helpExplanationClaim: String
    val helpExplanationClose: String
    val helpExplanationCloseAll: String
    val helpExplanationComment: String
    val helpExplanationCreate: String
    val helpExplanationHelp: String
    val helpExplanationHistory: String
    val helpExplanationList: String
    val helpExplanationListAssigned: String
    val helpExplanationListUnassigned: String
    val helpExplanationReload: String
    val helpExplanationReopen: String
    val helpExplanationSearch: String
    val helpExplanationSetPriority: String
    val helpExplanationTeleport: String
    val helpExplanationUnassign: String
    val helpExplanationView: String
    val helpExplanationDeepView: String

    // Modified Stacktrace
    val stacktraceLine1: String
    val stacktraceLine2: String
    val stacktraceLine3: String
    val stacktraceLine4: String
    val stacktraceEntry: String

    // Information:
    val informationReloadInitiated: String
    val informationReloadSuccess: String
    val informationReloadTasksDone: String
    val informationReloadFailure: String
    val informationUnderCooldown: String

    // Ticket Notifications
    val notifyUnreadUpdateSingle: String
    val notifyUnreadUpdateMulti: String
    val notifyOpenAssigned: String
    val notifyTicketAssignEvent: String
    val notifyTicketAssignSuccess: String
    val notifyTicketCreationSuccess: String
    val notifyTicketCreationEvent: String
    val notifyTicketCommentEvent: String
    val notifyTicketCommentSuccess: String
    val notifyTicketModificationEvent: String
    val notifyTicketMassCloseEvent: String
    val notifyTicketMassCloseSuccess: String
    val notifyTicketCloseSuccess: String
    val notifyTicketCloseWCommentSuccess: String
    val notifyTicketCloseEvent: String
    val notifyTicketCloseWCommentEvent: String
    val notifyTicketReopenSuccess: String
    val notifyTicketReopenEvent: String
    val notifyTicketSetPrioritySuccess: String
    val notifyTicketSetPriorityEvent: String
    val notifyPluginUpdate: String
    val notifyProxyUpdate: String
}