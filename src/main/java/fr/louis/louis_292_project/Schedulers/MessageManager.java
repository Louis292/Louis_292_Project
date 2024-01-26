package fr.louis.louis_292_project.Schedulers;

public class MessageManager {

    public static final String[] MESSAGES = new String[] {
            "Hey, Test Message 1",
            "Hey, Test Message 2",
            "Hey, Test Message 3"
    };

    private int index;

    public MessageManager() {
        this.index = 0;
    }

    public String getNextMessage() {
        final String message = MESSAGES[index];

        this.index++;

        if (this.index >= MESSAGES.length) {
            this.index = 0;
        }

        return message;
    }
}