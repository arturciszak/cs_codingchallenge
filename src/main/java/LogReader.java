import objects.EventData;
import objects.EventDataServer;
import objects.EventSummary;
import objects.State;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogReader {
    private final static String CHARSET_NAME = "UTF-8";
    private Logger LOGGER = Logger.getLogger(LogReader.class.getName());
    private ArrayList<EventData> mEventDataList;
    private Scanner mScanner = null;

    public LogReader(final String pathToFile) {
        try {
            FileInputStream mInputStream = new FileInputStream(pathToFile);
            mScanner = new Scanner(mInputStream, CHARSET_NAME);
            readFile();
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    private void readFile() throws IOException {
        mEventDataList = new ArrayList<>();

        while (mScanner.hasNextLine()) {
            String line = readJsonLine();

            EventData eventData = addEventData(line);
            EventData prevEventData = eventData.checkIfIdExists(mEventDataList);
            if (prevEventData != null) {
                if (prevEventData.getState() != eventData.getState()) {
                    mEventDataList.remove(prevEventData);
                    mEventDataList.remove(eventData);

                    new EventSummary(prevEventData, eventData);
                }
            }

            mEventDataList.add(eventData);
        }
        if (mScanner.ioException() != null) {
            throw mScanner.ioException();
        }
    }

    private String readJsonLine() {
        String line = mScanner.nextLine();
        if (!line.endsWith("}")) {
            line += mScanner.nextLine();
        }
        return line;
    }

    private EventData addEventData(String line) {
        try {
            JSONObject dataObject = new JSONObject(line);
            String id = dataObject.getString("id");
            State state = State.valueOf(dataObject.getString("state"));
            Long timestamp = dataObject.getLong("timestamp");
            String host = null;
            String type = null;

            if (dataObject.has("host")) {
                host = dataObject.getString("host");
            }
            if (dataObject.has("type")) {
                type = dataObject.getString("type");
            }

            if (host != null && type != null) {
                return new EventDataServer(id, state, timestamp, type, host);
            }

            return new EventData(id, state, timestamp);

        } catch (JSONException e) {
            LOGGER.log(Level.INFO, e.getMessage());
            LOGGER.log(Level.CONFIG, e.getMessage(), e.getCause());
            return null;
        }
    }

    private boolean checkIfExists(EventData data) {
        return mEventDataList.contains(data);
    }
}