package br.edu.ifsul.pokemao.tests;

public class Asserts {
    public static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }
    public static void assertFalse(boolean cond, String msg) {
        if (cond) throw new AssertionError(msg);
    }
    public static void assertEquals(Object expected, Object actual, String msg) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(msg + " | expected=" + expected + ", actual=" + actual);
    }
    public static void fail(String msg) {
        throw new AssertionError(msg);
    }
}
