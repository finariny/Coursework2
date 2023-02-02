package tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task {
    private static int idGenerator = 1;
    private String title;
    private final Type type;
    private final int id;
    private final LocalDateTime dateTime;
    private String description;

    public Task(String title, String description, Type type, LocalDateTime dateTime){
        setTitle(title);
        setDescription(description);
        if (type == null) {
            throw new IncorrectArgumentException("Тип повторяемости");
        }
        this.type = type;
        if (dateTime == null) {
            throw new IncorrectArgumentException("Дата и время");
        }
        this.dateTime = dateTime;
        this.id = idGenerator;
        idGenerator++;
    }

    public final int getId() {
        return this.id;
    }

    public final Type getType() {
        return this.type;
    }

    public final void setTitle(String title){
        if (title == null || title.isBlank()) {
            throw new IncorrectArgumentException("Заголовок");
        }
        this.title = title;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setDescription(String description){
        if (description == null || description.isBlank()) {
            throw new IncorrectArgumentException("Описание");
        }
        this.description = description;
    }

    public final String getDescription() {
        return this.description;
    }

    public final LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public abstract boolean appearsIn(LocalDate localDate);

    @Override
    public String toString() {
        return "Заголовок: " + this.title + "; Описание: " + this.description + "; Тип: " + this.type +
                "; Дата и время: " + this.dateTime + "; ID: " + this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return this.id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}