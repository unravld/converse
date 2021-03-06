package net.unraveled.api.interfaces;

import java.util.Date;
import java.util.UUID;

public interface IBan {
    UUID getUuid();

    String getName();

    String getIssuer();

    Date getIssueDate();

    Date getBanExpiry();

    String getBanId();

    String getBanMessage();
}
