package objects;

import java.util.List;

public class EventData {
    private final String mId;
    private final State mState;
    private final Long mTimestamp;

    public EventData(String mId, State mState, Long mTimestamp) {
        this.mId = mId;
        this.mState = mState;
        this.mTimestamp = mTimestamp;
    }

    public String getId() {
        return mId;
    }

    public State getState() {
        return mState;
    }

    public Long getTimestamp() {
        return mTimestamp;
    }

    public EventData checkIfIdExists(List<EventData> eventDatas) {
        return eventDatas.stream().filter(data -> data.getId().equals(mId)).findFirst().orElse(null);
    }
}
