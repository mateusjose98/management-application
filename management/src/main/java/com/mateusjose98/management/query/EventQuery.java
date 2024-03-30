package com.mateusjose98.management.query;

public class EventQuery {
    public static final String SELECT_EVENTS_BY_USER_ID_QUERY = "SELECT uev.id, uev.ip_address, ev.type, ev.description, uev.created_at FROM Events ev JOIN UserEvents uev ON ev.id = uev.event_id JOIN Users u ON u.id = uev.user_id WHERE u.id = :id ORDER BY uev.created_at DESC LIMIT 10";
    public static final String INSERT_EVENT_BY_USER_EMAIL_QUERY = """
              INSERT INTO UserEvents (user_id, event, ip_address)
              SELECT
                  (SELECT id FROM Users WHERE email = :email),
                  :event,
                  :ipAddress;
            """;

}
