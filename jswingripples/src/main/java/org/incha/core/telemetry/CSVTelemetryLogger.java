package org.incha.core.telemetry;

import org.incha.core.telemetry.utils.CSVLogDeserializer;
import org.incha.core.telemetry.utils.CSVLogSerializer;
import org.incha.core.telemetry.utils.Phase;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.incha.core.telemetry.utils.CSVLogSerializer.*;

/**
 * Created by eaburto on 26-01-17.
 */
public class CSVTelemetryLogger extends AbstractTelemetryLogger{
    private CSVLogSerializer serializer;
    private CSVLogDeserializer deserializer;
    private List<CSVQuad> logs;
    private static CSVTelemetryLogger instance;

    private CSVTelemetryLogger(){
        logs = new LinkedList<>();
    }

    @Override
    public void log(Phase phase, String action, String entity) {
        logs.add(new CSVQuad(getTimestamp(), phase, action, entity));
    }

    public void importFromFile(File file) {
        // TODO
    }
    public void exportToFile(File file){
        // TODO
    }

    public List<CSVQuad> getLogs(){
        return logs;
    }

    public static CSVTelemetryLogger getInstance(){
        if (instance == null){
            instance = new CSVTelemetryLogger();
        }

        return instance;
    }

}
