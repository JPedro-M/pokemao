package br.edu.ifsul.pokemao.tests;

import static br.edu.ifsul.pokemao.tests.Asserts.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import br.edu.ifsul.pokemao.utils.JsonDB;

public class TestJsonDB {
    public static void run() throws Exception {
        String file = "tmp_test_file.json";
        try {
            // empty read
            List<Map<String,String>> before = JsonDB.read(file);
            assertEquals(0, before.size(), "empty file should read as empty list");

            // write and read back
            Map<String,String> obj = new HashMap<>();
            obj.put("id", "1"); obj.put("name", "X"); obj.put("num", "10");
            JsonDB.write(file, List.of(obj));
            List<Map<String,String>> after = JsonDB.read(file);
            assertEquals(1, after.size(), "one element after write");
            assertEquals("X", after.get(0).get("name"), "name roundtrip");
            assertEquals("10", after.get(0).get("num"), "num roundtrip");
        } finally {
            // cleanup
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get("data").resolve("tmp_test_file.json"));
        }
    }
}
