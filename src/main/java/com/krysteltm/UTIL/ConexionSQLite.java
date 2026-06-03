
package com.krysteltm.UTIL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {

    private static final String APP_DIR_NAME = "RESI-ESPOCH";
    private static final String DB_FILENAME = "residencia.db";
    private static final String SEED_RESOURCE = "/db/residencia.db";

    private static Path dbPath;

    public static Connection conectar() {
        try {
            Path db = resolveDbPath();
            String url = "jdbc:sqlite:" + db.toAbsolutePath().toString().replace('\\', '/');
            Connection conn = DriverManager.getConnection(url);
            System.out.println("✅ Conexión a SQLite exitosa: " + db);
            return conn;
        } catch (SQLException | IOException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }

    private static synchronized Path resolveDbPath() throws IOException {
        if (dbPath != null) {
            return dbPath;
        }

        Path target = userDataDir().resolve(DB_FILENAME);

        if (!Files.exists(target)) {
            Files.createDirectories(target.getParent());

            Path legacy = Paths.get(DB_FILENAME);
            if (Files.exists(legacy) && Files.size(legacy) > 0) {
                Files.copy(legacy, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Base de datos migrada desde " + legacy.toAbsolutePath() + " a " + target);
            } else {
                try (InputStream in = ConexionSQLite.class.getResourceAsStream(SEED_RESOURCE)) {
                    if (in == null) {
                        throw new IOException("No se encontró el recurso semilla " + SEED_RESOURCE);
                    }
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Base de datos inicializada en " + target);
                }
            }
        }

        dbPath = target;
        return target;
    }

    private static Path userDataDir() {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) {
            String localAppData = System.getenv("LOCALAPPDATA");
            if (localAppData != null && !localAppData.isBlank()) {
                return Paths.get(localAppData, APP_DIR_NAME);
            }
        } else if (os.contains("mac")) {
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support", APP_DIR_NAME);
        }
        return Paths.get(System.getProperty("user.home"), "." + APP_DIR_NAME.toLowerCase());
    }
}
