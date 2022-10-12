package no.ntnu;

public class General {

    /**
     * return the last character of a string
     * @param string the string to get the last charcetr of
     * @return the last character of a given string
     */
    public String getLastChar(String string) {
        String lastChar = string.substring(string.length() - 1);
        return lastChar;
    }

    /**
     * returns the number of words
     * @param input provide text to count number of words
     * @return return number of words
     */
    public int countWords(String input) {
        if (input == null || input.isEmpty()) { return 0; } String[] words = input.split("\\s+");
        return words.length;
    }
    public int length(String value) {
        return value.length();
    }
}
