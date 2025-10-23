package br.edu.ifsul.pokemao.tests;

public class TestSuite {
    public static void main(String[] args) {
        try {
            TestData.backup();
            try {
                runTest("JsonDB", () -> TestJsonDB.run());
                runTest("TreinadorRepository", () -> TestTreinadorRepository.run());
                runTest("PokemaoCatalogoRepository", () -> TestPokemaoCatalogoRepository.run());
                runTest("PokemaoTreinadorRepository", () -> TestPokemaoTreinadorRepository.run());
                runTest("AcontecimentoRepository", () -> TestAcontecimentoRepository.run());
            } finally {
                TestData.restore();
            }
            System.out.println("All tests passed.");
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("Tests failed: " + t.getMessage());
            System.exit(1);
        }
    }

    @FunctionalInterface
    interface ThrowingRunnable { void run() throws Exception; }

    private static void runTest(String name, ThrowingRunnable test) throws Exception {
        System.out.println("Running: " + name);
        test.run();
        System.out.println("Passed: " + name);
    }
}
