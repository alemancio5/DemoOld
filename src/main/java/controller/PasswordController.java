package main.java.controller;

import main.java.network.server.Server;

import java.io.*;
import java.util.HashMap;

public class PasswordController {
    private final HashMap<String, String> passwords;

    public PasswordController() {
        this.passwords = this.restorePasswords();
    }

    private void savePasswords() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/passwords.ser")) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this.passwords);
        } catch (IOException e) {
            Server.LOGGER.severe("Error in saving passwords on file");
        }
    }

    public HashMap<String, String> restorePasswords() {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/passwords.ser")) {
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            return (HashMap<String, String>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Server.LOGGER.severe("Error in restoring passwords from file");
        }
        return null;
    }

    public Boolean registerPlayer(String username, String password) {
        if (!this.passwords.containsKey(username)) {
            this.passwords.put(username, password);
            this.savePasswords();
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean loginPlayer(String username, String password) {
        if (this.passwords.containsKey(username)) {
            return this.passwords.get(username).equals(password);
        }
        else {
            return false;
        }
    }
}
