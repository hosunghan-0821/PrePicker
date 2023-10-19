package co.kr.demo.infra.discord;

public enum EDiscordChannel {
    ORDER_NOTIFICATION_ROOM("주문알림"),
    ORDER_VIEW_ROOM("주문조회"),
    SERVER_ERROR("서버오류")
    ;
    private String name;

    EDiscordChannel(String name) {
        this.name = name;
    }
    public String getChannelName(){
        return this.name;
    }
}
