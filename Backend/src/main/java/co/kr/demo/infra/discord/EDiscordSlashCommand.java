package co.kr.demo.infra.discord;

public enum EDiscordSlashCommand {


    ORDER_LIST("주문목록"),
    INDIVIDUAL_ORDER("유저주문내역");
    private String commandName;

    EDiscordSlashCommand(String name) {
        this.commandName = name;
    }

    public String getCommandName() {
        return this.commandName;

    }
}
