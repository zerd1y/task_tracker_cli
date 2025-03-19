/**
 * Define attribute and method of task
 */
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Task {
    private static int lastId = 0;
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Task(String description) {
        this.id = ++lastId;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.TODO;
    }

    public int getId() {
        return id;
    }

    public void makeInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void makeDone() {
        this.status = Status.DONE;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String toJson() {
        return "{\"id\":\"" + id + "\"," +
                "\"description\":\"" + description.strip() + "\"," +
                "\"status\":\"" + status.toString() + "\"," +
                "\"createdAt\":\"" + createdAt.format(formatter) + "\"," +
                "\"updatedAt\":\"" + updatedAt.format(formatter) + "\"}";
    }

    public static Task fromJson(String json) {
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] parts = json.split(",");

        String id = parts[0].split(":")[1].strip();
        String description = parts[1].split(":")[1].strip();
        String statusString = parts[2].split(":")[1].strip();
        String createdAtStr = parts[3].split(":")[1].strip();
        String updatedAtStr = parts[4].split(":")[1].strip();

        Status status = Status.valueOf(statusString.toUpperCase().replace(" ", "_"));

        Task task = new Task(description);
        task.id = Integer.parseInt(id);
        task.status = status;
        task.createdAt = LocalDateTime.parse(createdAtStr, formatter);
        task.updatedAt = LocalDateTime.parse(updatedAtStr, formatter);

        if (Integer.parseInt(id) > lastId) {
            lastId = Integer.parseInt(id);
        }

        return task;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "id: " + id + ", description: " + description.strip() + ", status: " + status.toString() +
                ", createdAt: " + createdAt.format(formatter) + ", updatedAt: " + updatedAt.format(formatter);
    }
}
