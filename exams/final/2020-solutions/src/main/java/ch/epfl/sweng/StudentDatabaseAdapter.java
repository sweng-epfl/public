package ch.epfl.sweng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import ch.epfl.sweng.database.Database;
import ch.epfl.sweng.database.DatabaseException;

/**
 * This class adapts a {@link Database} so that it implements the {@link StudentDatabase} interface.
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class StudentDatabaseAdapter implements StudentDatabase {

    private final Database database;

    /**
     * Constructs a new student database adapter.
     *
     * @param database The adapted database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public StudentDatabaseAdapter(Database database) {
        if (database == null) {
            throw new IllegalArgumentException();
        }
        this.database = database;
    }

    @Override
    public void put(Student student) throws DatabaseException {
        if (student == null) {
            throw new IllegalArgumentException();
        }
        ByteArrayOutputStream serializeBuffer = new ByteArrayOutputStream();
        try {
            ObjectOutputStream serializer = new ObjectOutputStream(serializeBuffer);
            serializer.writeObject(student);
            serializer.close();
        } catch (IOException e) {
            throw new DatabaseException("cannot serialize student", e);
        }
        String serialized = Base64.getEncoder().encodeToString(serializeBuffer.toByteArray());
        this.database.put(student.sciper, serialized);
    }

    @Override
    public void remove(String sciper) throws DatabaseException {
        this.database.remove(sciper);
    }

    @Override
    public Student get(String sciper) throws DatabaseException {
        String data = this.database.get(sciper);
        if (data == null) {
            return null;
        }

        Student result;
        byte[] rawData;
        try {
            rawData = Base64.getDecoder().decode(data);
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("cannot decode student data", e);
        }
        ByteArrayInputStream inputBuffer = new ByteArrayInputStream(rawData);
        try {
            ObjectInputStream deserializer = new ObjectInputStream(inputBuffer);
            result = (Student) deserializer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DatabaseException("cannot deserialize string to student", e);
        }

        return result;
    }
}
