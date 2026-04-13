package BCC.ES.CLP.exceptions;

public class TimeoutScan extends RuntimeException{
    public TimeoutScan() {
        super("Timeout no scan");
    }

    public TimeoutScan(String message) {
        super(message);
    }
}
