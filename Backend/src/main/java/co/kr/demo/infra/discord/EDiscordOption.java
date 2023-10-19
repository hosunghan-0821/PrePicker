package co.kr.demo.infra.discord;

public enum EDiscordOption {

    SEARCH_START_DATE("start"),
    SEARCH_END_DATE("end"),
    CLIENT_PHONE_NUM("phone"),
    CLIENT_NAME("name");
    private String optionName;
    EDiscordOption(String name) {
        this.optionName = name;
    }

    public String getOptionName() {
        return this.optionName;

    }
}
