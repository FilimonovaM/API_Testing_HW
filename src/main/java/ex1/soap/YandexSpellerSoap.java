package ex1.soap;

import ex1.enums.LanguageEnum;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static ex1.entity.TestingValues.DATA_DEFAULT_DIGITS;
import static ex1.enums.OptionsEnum.DEFAULT_OPTION;
import static ex1.enums.ParametersEnum.LANG;
import static ex1.enums.ParametersEnum.OPTIONS;
import static ex1.enums.ParametersEnum.TEXT;

public class YandexSpellerSoap {

    static RequestSpecification spellerRequest = new RequestSpecBuilder()
            .addHeader("Accept-Encoding", "gzip,deflate")
            .setContentType("text/xml;charset=UTF-8")
            .addHeader("SOAPAction", "http://speller.yandex.net/services/spellservice/checkText")
            .addHeader("Host", "speller.yandex.net")
            .setBaseUri("http://speller.yandex.net/services/spellservice")
            .build();

    //builder pattern
    private YandexSpellerSoap() {

    }

    ;
    private HashMap<String, String> params = new HashMap<>();

    public static class SoapBuilder {
        YandexSpellerSoap spellerSoap;

        private SoapBuilder(YandexSpellerSoap soap) {
            spellerSoap = soap;
        }

        public YandexSpellerSoap.SoapBuilder text(String text) {
            spellerSoap.params.put(TEXT.param, text);
            return this;
        }

        public YandexSpellerSoap.SoapBuilder options(String options) {
            spellerSoap.params.put(OPTIONS.param, "\"" + options + "\"");
            return this;
        }

        public YandexSpellerSoap.SoapBuilder language(LanguageEnum language) {
            spellerSoap.params.put(LANG.param, "\"" + language.lang + "\"");
            return this;
        }

        public Response callSOAP() {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel" +
                    "=\"http://speller.yandex.net/services/spellservice\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <spel:CheckTextRequest lang=" + (spellerSoap.params.getOrDefault(LANG.param,
                    "\"en\""))
                    + " options=" + (spellerSoap.params.getOrDefault(OPTIONS.param, "\"" +
                    DEFAULT_OPTION.options + "\"")) + " format=\"\">\n" +
                    "         <spel:text>" + (spellerSoap.params.getOrDefault(TEXT.param, DATA_DEFAULT_DIGITS
                    .getWrongWord())) + "</spel:text>\n" +
                    "      </spel:CheckTextRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";


            return RestAssured.with()
                    .spec(spellerRequest)
                    .body(soapBody)
                    .log().all().with()
                    .post().prettyPeek();
        }

        public RequestSpecification customCallSOAP() {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel" +
                    "=\"http://speller.yandex.net/services/spellservice\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <spel:CheckTextRequest lang=" + (spellerSoap.params.getOrDefault(LANG.param,
                    "\"en\""))
                    + " options=" + (spellerSoap.params.getOrDefault(OPTIONS.param, "\"0\"")) +
                    " format=\"\">\n" +
                    "         <spel:text>" + (spellerSoap.params.getOrDefault(TEXT.param, DATA_DEFAULT_DIGITS
                    .getWrongWord())) + "</spel:text>\n" +
                    "      </spel:CheckTextRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";


            return RestAssured.with()
                    .spec(spellerRequest)
                    .body(soapBody)
                    .log().all().with();
        }

    }


    public static SoapBuilder with() {
        YandexSpellerSoap soap = new YandexSpellerSoap();
        return new SoapBuilder(soap);
    }
}
