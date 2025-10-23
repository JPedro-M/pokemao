package br.edu.ifsul.pokemao.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitário muito simples para persistência em arquivos JSON no diretório
 * `data/` do projeto. Não é um parser JSON completo — ele lida com o formato
 * gerado por este projeto: arrays de objetos com valores primitivos e strings
 * (sem caracteres de escape complexos). Assunções razoáveis foram tomadas e
 * documentadas no código.
 */
public class JsonDB {
    private static final Path DATA_DIR = Paths.get("data");

    private static void ensureDataDir() throws IOException {
        if (!Files.exists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }
    }

    public static synchronized List<Map<String, String>> read(String filename) throws IOException {
        ensureDataDir();
        Path file = DATA_DIR.resolve(filename);
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8).trim();
        if (content.isEmpty()) return new ArrayList<>();
        return parseArrayOfObjects(content);
    }

    public static synchronized void write(String filename, List<Map<String, String>> list) throws IOException {
        ensureDataDir();
        Path file = DATA_DIR.resolve(filename);
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (Map<String, String> obj : list) {
            if (!first) sb.append(',');
            first = false;
            sb.append('{');
            boolean firstField = true;
            for (Map.Entry<String, String> e : obj.entrySet()) {
                if (!firstField) sb.append(',');
                firstField = false;
                sb.append('"').append(escape(e.getKey())).append('"').append(':');
                String v = e.getValue();
                if (v == null) {
                    sb.append("null");
                } else if (isNumeric(v) || "true".equals(v) || "false".equals(v)) {
                    sb.append(v);
                } else {
                    sb.append('"').append(escape(v)).append('"');
                }
            }
            sb.append('}');
        }
        sb.append(']');
        Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    private static String unescape(String s) {
        return s.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    // Muito simples — assume input começando com [ e contendo objetos {} possivelmente separados por ','
    private static List<Map<String, String>> parseArrayOfObjects(String s) {
        List<Map<String, String>> result = new ArrayList<>();
        s = s.trim();
        if (!s.startsWith("[")) return result;
        int i = 1;
        int n = s.length();
        while (i < n) {
            // skip whitespace and commas
            while (i < n && (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',')) i++;
            if (i >= n || s.charAt(i) != '{') break;
            int start = i;
            int depth = 0;
            while (i < n) {
                char c = s.charAt(i);
                if (c == '{') depth++;
                else if (c == '}') {
                    depth--;
                    if (depth == 0) { i++; break; }
                }
                i++;
            }
            String objText = s.substring(start, i);
            Map<String, String> map = parseObject(objText);
            result.add(map);
        }
        return result;
    }

    // Parse object content like {"a":1,"b":"str", "c": null}
    private static Map<String, String> parseObject(String s) {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        int n = s.length();
        if (i < n && s.charAt(i) == '{') i++;
        while (i < n) {
            // skip whitespace
            while (i < n && Character.isWhitespace(s.charAt(i))) i++;
            if (i < n && s.charAt(i) == '}') break;
            // read key
            if (i < n && s.charAt(i) == '"') {
                i++;
                StringBuilder keySb = new StringBuilder();
                while (i < n) {
                    char c = s.charAt(i);
                    if (c == '\\') {
                        if (i + 1 < n) { keySb.append(s.charAt(i+1)); i += 2; continue; }
                    }
                    if (c == '"') { i++; break; }
                    keySb.append(c); i++;
                }
                String key = keySb.toString();
                // skip whitespace and ':'
                while (i < n && Character.isWhitespace(s.charAt(i))) i++;
                if (i < n && s.charAt(i) == ':') i++;
                while (i < n && Character.isWhitespace(s.charAt(i))) i++;
                // read value
                String value = null;
                if (i < n && s.charAt(i) == '"') {
                    i++; StringBuilder valSb = new StringBuilder();
                    while (i < n) {
                        char c = s.charAt(i);
                        if (c == '\\') {
                            if (i + 1 < n) { valSb.append(s.charAt(i+1)); i += 2; continue; }
                        }
                        if (c == '"') { i++; break; }
                        valSb.append(c); i++;
                    }
                    value = unescape(valSb.toString());
                } else {
                    // read until comma or }
                    int startVal = i;
                    while (i < n && s.charAt(i) != ',' && s.charAt(i) != '}') i++;
                    value = s.substring(startVal, i).trim();
                    if (value.equals("null")) value = null;
                }
                map.put(key, value);
                // skip whitespace and optional comma
                while (i < n && Character.isWhitespace(s.charAt(i))) i++;
                if (i < n && s.charAt(i) == ',') i++;
            } else {
                break;
            }
        }
        return map;
    }
}
