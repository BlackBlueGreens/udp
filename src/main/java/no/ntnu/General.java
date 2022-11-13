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
     *
     * @param string the string to check
     * @return wether this is a 'question' or 'statement' or 'unknown'
     */
    public String wordType(String string) {
        String answer;
        if(getLastChar(string).equals(".")) {
            answer = "statement";

        } else if(getLastChar(string).equals("?")) {
            answer= "question";
        } else{
            answer= "UNKNOWN";
        }
        return answer;
    }

    /**
     * returns the number of words
     * @param input provide text to count number of words
     * @return return number of words
     */
    public int countWords(String input) {
       String[] words = null;
        if (input == null || input.isEmpty()) {
            return 0;
        }else {
            words = input.split("\\s+");
        }
        return words.length;
    }
    public int length(String value) {
        return value.length();
    }
    public String taskAnswer(String string) {
        String answer;
        answer =wordType(string) + " " + countWords(string);
        return answer;
    }
}
