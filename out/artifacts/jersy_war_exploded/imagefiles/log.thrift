namespace java com.xiaomi.misearch.log.thrift

enum EventType {
    SEARCH = 0,
    CLICK,
    DOWNLOAD
}

struct SearchEvent {
    1: string query,
    2: string ref,
    3: string version,
    4: string os,
    5: i32 page,
    6: list<i64> ids,
}

struct ClickEvent {
    1: i32 offset = -1,
    2: i64 id,
}

struct DownloadEvent {
    1: i64 id = -1,
    2: i32 offset = -1,
}

struct Event {
    1: EventType type,
    2: i64 timestamp,
    3: optional SearchEvent searchEvent,
    4: optional ClickEvent clickEvent,
    5: optional DownloadEvent downloadEvent,
    6: string ip,
    7: optional string url,
    8: optional string session,
}

struct SessionLog {
    1: required string sid,
    2: string id,
    3: i64 startTime,
    4: i64 endTime,
    5: list<Event> events,
    6: string version,
    7: string os,
}

struct SessionLogList {
    1: string id,
    2: list<SessionLog> sessions,
}
