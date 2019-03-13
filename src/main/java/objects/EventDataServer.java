package objects;

public class EventDataServer extends EventData {
    private final String mType;
    private final String mHost;

    public EventDataServer(String mId, State mState, Long mTimestamp, String mType, String mHost) {
        super(mId, mState, mTimestamp);
        this.mType = mType;
        this.mHost = mHost;
    }

    public String getType() {
        return mType;
    }

    public String getHost() {
        return mHost;
    }
}
