package net.unraveled.api.abstracts;

public abstract class SerializableObject<T> {
    public abstract String serialize();

    public abstract T deserialize();
}
