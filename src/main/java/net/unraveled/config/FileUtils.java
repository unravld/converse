package net.unraveled.config;

import net.unraveled.util.ConverseBase;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils extends ConverseBase {
    private final File configFile;

    public FileUtils(String fileName) {
        File dataFolder = new File(plugin.getDataFolder().getName());

        if (!dataFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configFile = file;
    }

    public FileUtils(String fileName, String parentFolder) {
        File dataFolder = new File(plugin.getDataFolder(), parentFolder);

        if (!dataFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configFile = file;
    }

    public void write(String output) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(configFile);
        fileOutputStream.write(output.getBytes());
        fileOutputStream.close();
    }

    public String read() throws IOException {
        FileInputStream stream = new FileInputStream(configFile);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8.name()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } finally {
            stream.close();
            return sb.toString();
        }
    }

    public File getFile() {
        return configFile;
    }
}
