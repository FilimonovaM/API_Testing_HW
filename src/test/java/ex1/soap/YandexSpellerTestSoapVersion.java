package ex1.soap;

import ex1.enums.LanguageEnum;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static ex1.enums.ErrorCodesEnum.*;
import static ex1.enums.OptionsEnum.*;
import static org.testng.Assert.*;


import static ex1.entity.TestingValues.*;

public class YandexSpellerTestSoapVersion {

    @Test
    public void simpleCall() {
        YandexSpellerSoap
                .with().text(DATA_IGNORE_DIGITS
                .getWrongWord())
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder(Arrays.asList(DATA_IGNORE_DIGITS.getWrongWord(),
                        DATA_IGNORE_DIGITS.getRightWord())));
    }

    @Test
    public void useRequestBuilderToChangeParams() {
        YandexSpellerSoap.with()
                .language(LanguageEnum.EN)
                .text(DATA_IGNORE_DIGITS.getWrongWord())
                .options(IGNORE_URLS.options)
                .callSOAP()
                .then()
                .assertThat().body(Matchers.stringContainsInOrder(Arrays.asList(DATA_IGNORE_DIGITS.getWrongWord(),
                DATA_IGNORE_DIGITS.getRightWord())))
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body(Matchers.containsString(ERROR_UNKNOWN_WORD.xmlValue));
    }

    @Test
    public void makeBasicRequestTest() {
        //GET
        System.out.println("----------------------GET----------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .get()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //PUT
        System.out.println("----------------------PUT----------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .put()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);

        //POST
        System.out.println("----------------------PUT----------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .post()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //OPTIONS
        System.out.println("--------------------OPTIONS--------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .options()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //HEAD
        System.out.println("----------------------HEAD---------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .head()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //DELETE
        System.out.println("--------------------DELETE----------------------");
        YandexSpellerSoap
                .with()
                .customCallSOAP()
                .delete()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void checkOptionsIgnoreDigits() {
        //ignore digit
        assertFalse(YandexSpellerSoap.with()
                .text(DATA_IGNORE_DIGITS.getWrongWord())
                .options(IGNORE_DIGITS.options)
                .callSOAP().prettyPrint().contains("<SpellResult>"), "Function 'check ignore digit' don`t work! " +
                "Answer contains result!");
    }

    @Test
    public void checkOptionsIgnoreURL() {
        //ignore url
        assertFalse(YandexSpellerSoap.with()
                        .text(DATA_IGNORE_URL.getWrongWord())
                        .options(IGNORE_URLS.options)
                        .callSOAP().prettyPrint().contains(DATA_IGNORE_URL.getResultSection()),
                "Function 'check ignore url' don`t work! Answer contains result!");
    }

    @Test
    public void checkOptionsIgnoreCapitalization() {
        //ignore capitalization
        assertFalse(YandexSpellerSoap.with()
                        .text(DATA_IGNORE_CAPITALIZATION.getWrongWord())
                        .options(IGNORE_CAPITALIZATION.options)
                        .callSOAP().prettyPrint().contains(DATA_IGNORE_CAPITALIZATION.getResultSection()),
                "Function 'check ignore capitalization' don`t work! " + "Answer contains result!");
    }

    @Test
    public void checkOptionsFindRepeatingWords() {
        //find repeating words
        assertTrue(YandexSpellerSoap.with()
                        .text(DATA_FIND_REPEAT_WORDS.getWrongWord())
                        .options(FIND_REPEAT_WORDS.options)
                        .callSOAP().prettyPrint().contains(DATA_FIND_REPEAT_WORDS.getRightWord()),
                "Function 'find repeat words' don`t work! " + "Answer should contains repeating words!");
    }

    @Test
    public void checkUnknownWordError() {
        //Unknown word
        assertTrue(YandexSpellerSoap.with()
                .text(DATA_IGNORE_DIGITS.getWrongWord())
                .options(DEFAULT_OPTION.options)
                .callSOAP().prettyPrint().contains(ERROR_UNKNOWN_WORD.xmlValue), "Wrong error code");
    }

    @Test
    public void checkRepeatWordError() {
        //find repeating words
        assertTrue(YandexSpellerSoap.with()
                .text(DATA_FIND_REPEAT_WORDS.getWrongWord())
                .options(FIND_REPEAT_WORDS.options)
                .callSOAP().prettyPrint().contains(ERROR_REPEAT_WORD.xmlValue), "Wrong error code");
    }

    @Test
    public void checkCapitalizationError() {
        //with wrong capitalization
        assertTrue(YandexSpellerSoap.with()
                .text(DATA_IGNORE_CAPITALIZATION.getWrongWord())
                .options(DEFAULT_OPTION.options)
                .callSOAP().prettyPrint().contains(ERROR_CAPITALIZATION.xmlValue), "Wrong error code");
    }


    //DO NOT WORK!
    @Test
    public void checkTooManyErrorsOption() {
        //Unknown word
        assertTrue(YandexSpellerSoap.with()
                .text(WORDS_WITH_RANDOMIZED_ALPHABET.getWrongWord())
                .options(DEFAULT_OPTION.options)
                .callSOAP().prettyPrint().contains(ERROR_TOO_MANY_ERRORS.xmlValue), "Wrong error code");
    }
}
