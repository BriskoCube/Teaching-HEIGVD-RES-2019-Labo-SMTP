package Smtp;

public class ServerResponse {

    int status;
    String message;

    public ServerResponse(String line){
        status = Integer.parseInt(line.substring(0,3));
        message = line.substring(4);
    }

    public ServerResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
