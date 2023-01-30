package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailyTask extends Task {
    public DailyTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, description, type, dateTime);
    }

    @Override
    public LocalDate appearsIn(LocalDate localDate) {
        return localDate.plusDays(1);
    }
}