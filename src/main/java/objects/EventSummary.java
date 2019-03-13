package objects;

public class EventSummary {
    private Long mDuration;
    private boolean mAlert = false;

    public EventSummary(EventData data, EventData data2) {
        this.mDuration = Math.abs(data.getTimestamp() - data2.getTimestamp());

        if (this.mDuration > 4) {
            mAlert = true;
        }
    }
}
