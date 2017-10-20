package prj.serenasimon.datas;

import java.util.Date;

import prj.serenasimon.enums.UserActions;

public class ChatMessage {

    private String message;
    private String senderid;
    private String reveiverid;
    private Date received;
    private Enum<UserActions> action;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public Enum<UserActions> getAction() {
        return action;
    }

    public void setAction(UserActions action) {
        this.action = action;
    }

    public void setAction(String action) {
        for (UserActions act : UserActions.values()) {
            if (act.toString().equals(action)) {
                this.setAction(act);
            }
        }
    }

    public String getReveiverid() {
        return reveiverid;
    }

    public void setReveiverid(String reveiverid) {
        this.reveiverid = reveiverid;
    }

}
