package ex1.entity;

public class TestingValues {
    private String wrongWord;
    private String rightWord;
    private int status;
    private final String RESULT_SECTION = "<SpellResult>";

    private TestingValues(String wrongWord, String rightWord) {
        this.wrongWord = wrongWord;
        this.rightWord = rightWord;
    }

    public static final TestingValues WORDS_WITH_RANDOMIZED_ALPHABET = new TestingValues("IGgHhn OobJjKkLfe PpQvWCcD dEilMmNAx Y" +
            "UaBsTtu VyZzFwX qRrS", null);
    public static final TestingValues DATA_DEFAULT_DIGITS = new TestingValues("Strorg", "Strong");
    public static final TestingValues DATA_IGNORE_DIGITS = new TestingValues("Strorg1", "Strong1");
    public static final TestingValues DATA_IGNORE_URL = new TestingValues("www.Strorg1.com", "Strong");
    public static final TestingValues DATA_FIND_REPEAT_WORDS = new TestingValues("I believe I can can fly",
            "can");
    public static final TestingValues DATA_IGNORE_CAPITALIZATION = new TestingValues("moscow",
            "Moscow");


    public String getWrongWord() {
        return wrongWord;
    }

    public String getRightWord() {
        return rightWord;
    }

    public int getStatus() {
        return status;
    }

    public String getResultSection() {
        return RESULT_SECTION;
    }
}
