package skywolf46.HumAInity.GrammerParser.Enum;

public enum DialogType {
    // Question = 의문문 / 학습된 데이터 기준으로 사용자에게 데이터 반환.
    // VALIDATION = 의미 교정, 봇이 학습되지 못한 단어를 인식할 경우, 사용자에게 물어 데이터를 반환받을때 사용자의 입력.
    // PROGRAMMED_DATA_ORDERING = 사용자 명령. 사용자가 데이터를 입력하여 다른 응용 프로그램 혹은 모듈 기능으로 연계해야한다면 발생한다.
    // LEARN_WORD_RELATED = 연계 단어 학습.
    // 만약 사용자가 데이터를 입력하여 데이터를 교정한다면 이 입력 데이터가 발생된다.
    // ex)
    // Bot: Hello, <User>! Sky is blue, and sunny! Good day to go outside, right?
    // User: Hello, <Bot>. Today is not sunny now. Where do you saw the blu sky?
    QUESTION,VALIDATION,PROGRAMMED_DATA_ORDERING,LEARN_WORD_RELATED,
}

