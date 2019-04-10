package Smtp;

public class ServerResponse {

    int status;
    String message;
    boolean more;

    public ServerResponse(String line){
        status = Integer.parseInt(line.substring(0,3));
        more = line.substring(3,4).equals("-");
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

    public boolean hasMore() {
        return more;
    }
}
