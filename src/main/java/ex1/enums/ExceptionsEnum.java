package ex1.enums;

public enum ExceptionsEnum {
    ERROR_UNKNOWN_WORD(1, "Слова нет в словаре."),
    ERROR_REPEAT_WORD(2, "Повтор слова."),
    ERROR_CAPITALIZATION(3, "Неверное употребление прописных и строчных букв."),
    ERROR_TOO_MANY_ERRORS(4, "Текст содержит слишком много ошибок. При этом приложение может отправить Яндекс." +
            "Спеллеру оставшийся непроверенным текст в следующем запросе.");

    public String description;
    public int number;

    ExceptionsEnum(int number, String description) {
        this.number = number;
        this.description = description;
    }
}
