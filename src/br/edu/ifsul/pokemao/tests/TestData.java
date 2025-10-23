package br.edu.ifsul.pokemao.tests;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifsul.pokemao.utils.JsonDB;

public class TestData {
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path BACKUP_DIR = DATA_DIR.resolve("__backup_before_tests");

    public static void backup() throws IOException {
        if (!Files.exists(DATA_DIR)) Files.createDirectories(DATA_DIR);
        if (Files.exists(BACKUP_DIR)) return; // don't overwrite existing backup
        Files.createDirectories(BACKUP_DIR);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(DATA_DIR, "*.json")) {
            for (Path p : ds) {
                Files.copy(p, BACKUP_DIR.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void restore() throws IOException {
        if (!Files.exists(BACKUP_DIR)) return;
        // clean data dir jsons
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(DATA_DIR, "*.json")) {
            for (Path p : ds) Files.deleteIfExists(p);
        }
        // restore
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BACKUP_DIR, "*.json")) {
            for (Path p : ds) {
                Files.copy(p, DATA_DIR.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        // cleanup backup
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BACKUP_DIR, "*")) {
            for (Path p : ds) Files.deleteIfExists(p);
        }
        Files.deleteIfExists(BACKUP_DIR);
    }

    public static void writeTreinadores(List<Map<String,String>> list) throws IOException {
        JsonDB.write("treinadores.json", list);
    }
    public static void writePokemaosCatalogo(List<Map<String,String>> list) throws IOException {
        JsonDB.write("pokemaos.json", list);
    }
    public static void writePokemaosTreinadores(List<Map<String,String>> list) throws IOException {
        JsonDB.write("pokemaos_treinadores.json", list);
    }
    public static void writeTrocas(List<Map<String,String>> list) throws IOException {
        JsonDB.write("trocas.json", list);
    }
    public static void writeBatalhas(List<Map<String,String>> list) throws IOException {
        JsonDB.write("batalhas.json", list);
    }

    public static Map<String,String> map(Object... kv) {
        Map<String,String> m = new HashMap<>();
        for (int i = 0; i < kv.length; i += 2) {
            m.put(String.valueOf(kv[i]), kv[i+1] == null ? null : String.valueOf(kv[i+1]));
        }
        return m;
    }

    @SafeVarargs
    public static List<Map<String,String>> list(Map<String,String>... items) {
        List<Map<String,String>> l = new ArrayList<>();
        for (Map<String,String> it : items) l.add(it);
        return l;
    }
}
