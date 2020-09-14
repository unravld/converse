package net.unraveled.playerdata;

public class InvalidPlayerDataException extends RuntimeException {
    public InvalidPlayerDataException() {
        super("Do not use invalid player data when performing data functions", new Throwable());
    }
}