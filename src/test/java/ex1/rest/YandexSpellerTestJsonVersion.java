package ex1.rest;

import beans.YandexSpellerAnswer;
import ex1.util.CustomReader;
import ex1.entity.TestingValues;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ex1.entity.TestingValues.*;
import static ex1.enums.ErrorCodesEnum.ERROR_CAPITALIZATION;
import static ex1.enums.ErrorCodesEnum.ERROR_REPEAT_WORD;
import static ex1.enums.ErrorCodesEnum.ERROR_UNKNOWN_WORD;
import static ex1.enums.FormatEnum.PLAIN;
import static ex1.enums.LanguageEnum.EN;
import static ex1.enums.OptionsEnum.*;
import static ex1.enums.ParametersEnum.*;
import static org.hamcrest.Matchers.lessThan;

public class YandexSpellerTestJsonVersion {

    @DataProvider(name = "params data update")
    public Object[][] newParams() {
        Map<String, TestingValues> dataMap = CustomReader.readJson("data1.json");
        Object[] values = dataMap.values().toArray();
        Object[][] newData = new Object[values.length][1];
        for (int i = 0; i < values.length; i++) {
            newData[i][0] = values[i];
            System.out.println(values[i].getClass().getName());
        }
        return newData;
    }

    @Test(dataProvider = "word data update")
    public void simpleAPITest(TestingValues data) {
        RestAssured
                .given()
                .queryParam(TEXT.param, data.getWrongWord())
                .params(LANG.param, EN.lang)
                .params(OPTIONS.param, DEFAULT_OPTION.options)
                .params(FORMAT.param, PLAIN.format)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .get(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(data.getStatus())
                .assertThat().body(Matchers.allOf(
                Matchers.stringContainsInOrder(Arrays.asList(data.getWrongWord(), data.getRightWord())),
                Matchers.containsString(CODE.param)))
                .assertThat().contentType(ContentType.JSON)
                .time(lessThan(20000L));
    }

    @Test
    public void makeBasicRequestTest() {
        //get
        RestAssured
                .given()
                .log().everything()
                .get(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
        System.out.println("GET==================================================\n");

        //post
        RestAssured
                .given()
                .log().everything()
                .post(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
        System.out.println("POST==================================================\n");

        //head
        RestAssured
                .given()
                .log().everything()
                .head(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
        System.out.println("HEAD==================================================\n");

        //options
        RestAssured
                .given()
                .log().everything()
                .options(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
        System.out.println("OPTIONS==================================================\n");

        //patch
        RestAssured
                .given()
                .log().everything()
                .patch(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
        System.out.println("PATCH==================================================\n");

        //put
        RestAssured
                .given()
                .log().everything()
                .put(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
        System.out.println("PUT==================================================\n");


        //delete
        RestAssured
                .given()
                .log().everything()
                .delete(YANDEX_SPELLER_API_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
        System.out.println("DELETE==================================================\n");
    }

    @Test
    public void useIgnoreDigitOption() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_DIGITS.getWrongWord())
                        .language(EN.lang)
                        .options(IGNORE_DIGITS.options)
                        .callApi());
        assert answers.size() == 0 : "wrong amount of answers";
    }

    @Test
    public void useIgnoreUrlsOption() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_URL.getWrongWord())
                        .language(EN.lang)
                        .options(IGNORE_URLS.options)
                        .callApi());
        assert answers.size() == 0 : "wrong amount of answers";

    }

    @Test
    public void useIgnoreCapitalization() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_CAPITALIZATION.getWrongWord())
                        .language(EN.lang)
                        .options(IGNORE_CAPITALIZATION.options)
                        .callApi());
        assert answers.size() == 0 : "wrong amount of answers";
    }

    //    Function don`t work!
    @Test
    public void useFindRepeatsOption() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_FIND_REPEAT_WORDS.getWrongWord())
                        .language(EN.lang)
                        .options(FIND_REPEAT_WORDS.options)
                        .callApi());
        assert answers.size() > 0 : "wrong amount of answers";
    }

    //    Function don`t work!
    @Test
    public void checkRepeatExceptions() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_FIND_REPEAT_WORDS.getWrongWord())
                        .language(EN.lang)
                        .callApi());
        answers.forEach(answer -> {
            assert answer.code == ERROR_REPEAT_WORD.number : "Wrong error code";
            assert answer.s.contains(DATA_FIND_REPEAT_WORDS.getRightWord()) : "Wrong variants";
        });
    }

    //    Function works particular
    @Test
    public void checkUrlIgnoringExceptions() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_URL.getWrongWord())
                        .language(EN.lang)
                        .callApi());
        assert answers.size() > 0 : "expected number of answers is wrong.";
        answers.forEach(answer -> {
            assert answer.code == ERROR_UNKNOWN_WORD.number : "Wrong error code";
            assert answer.s.contains(DATA_IGNORE_URL.getRightWord()) : "Wrong variants";
        });
    }

    //    Function don`t work!
    @Test
    public void checkCapitalizationExceptions() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_CAPITALIZATION.getWrongWord())
                        .language(EN.lang)
                        .callApi());
        assert answers.size() > 0 : "expected number of answers is wrong.";
        answers.forEach(answer -> {
            assert answer.code == ERROR_CAPITALIZATION.number : "Wrong error code";
            assert answer.s.contains(DATA_IGNORE_CAPITALIZATION.getRightWord()) : "Wrong variants";
        });
    }

    @Test
    public void checkServiceExceptionsWithDigits() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(DATA_IGNORE_DIGITS.getWrongWord())
                        .language(EN.lang)
                        .callApi());
        assert answers.size() > 0 : "expected number of answers is wrong.";
        answers.forEach(answer -> {
            assert answer.code == ERROR_UNKNOWN_WORD.number : "Wrong error code";
            assert answer.s.contains(DATA_IGNORE_DIGITS.getRightWord()) : "Wrong variants";
        });
    }

    //    Function don`t work!
    @Test
    public void checkServiceExceptions() {
        List<YandexSpellerAnswer> answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with()
                        .text(WORDS_WITH_RANDOMIZED_ALPHABET.getWrongWord())
                        .language(EN.lang)
                        .callApi());
        assert answers.size() > 0 : "expected number of answers is wrong.";
        answers.forEach(answer -> {
            assert answer.code == ERROR_UNKNOWN_WORD.number : "Wrong error code";
        });
    }
}
