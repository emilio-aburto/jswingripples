package org.incha.core.telemetry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by emilio-aburto on 12-01-17.
 * Interface to be implemented by any Telemetry Logger
 */
public abstract class AbstractTelemetryLogger {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    public AbstractTelemetryLogger() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    }

    public void setCustomCalendar(Calendar cal){
        calendar = cal;
    }

    public abstract void log(String... args);

}
