package net.unraveled.config;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigIndex {
    public static void createDefaultConfiguration(final String fileName) {
        final File targetFile = new File(new Container().getPlugin().getDataFolder(), fileName);

        if (targetFile.exists()) {
            return;
        }

        Bukkit.getLogger().info("Installing default configuration file template: " + targetFile.getPath());

        try {
            final InputStream inputStream = new Container().getPlugin().getResource(fileName);
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(inputStream), targetFile);
            inputStream.close();
        } catch (IOException ex) {
            Bukkit.getLogger().severe(ex.getMessage());
        }
    }

    public static boolean deleteFolder(final File file) {
        if (file.exists() && file.isDirectory()) {
            return FileUtils.deleteQuietly(file);
        }
        return false;
    }

    public static void deleteCoreDumps() {
        final File[] coreDumps = new File(".").listFiles(f -> f.getName().startsWith("java.core"));

        Arrays.stream(Objects.requireNonNull(coreDumps)).forEach(dump -> {
            Bukkit.getLogger().info("Removing core dump file: " + dump.getName());
            //noinspection ResultOfMethodCallIgnored
            dump.delete();
        });
    }

    public static void copy(InputStream inputStream, File file) throws IOException {
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }

        final OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int fuzz;
        while ((fuzz = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, fuzz);
        }
        outputStream.close();
        inputStream.close();
    }

    public static File getPluginFile(Plugin plugin, String name) {
        return new File(plugin.getDataFolder(), name);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Boolean> getSavedFlags() {
        Map<String, Boolean> flags = null;
        File input = new File(new Container().getPlugin().getDataFolder(), "flags.yml");
        if (input.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(input);
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                flags = (HashMap<String, Boolean>) objectStream.readObject();
                objectStream.close();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return flags;
    }

    public static boolean getSavedFlag(String flag) throws Exception {
        Boolean value = null;
        Map<String, Boolean> flags = getSavedFlags();
        if (flags != null) {
            if (flags.containsKey(flag)) {
                value = flags.get(flag);
            }
        }

        if (value != null) {
            return value;
        } else {
            throw new Exception();
        }
    }

    public static void setSavedFlag(String flag, boolean value) {
        Map<String, Boolean> flags = getSavedFlags();
        if (flags == null) {
            flags = new HashMap<>();
        }
        flags.put(flag, value);

        try {
            final FileOutputStream outputStream = new FileOutputStream((new File(new Container().getPlugin().getDataFolder(), "flags.yml")));
            final ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
            objectStream.writeObject(flags);
            objectStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
