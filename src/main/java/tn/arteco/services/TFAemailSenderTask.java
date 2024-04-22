package tn.arteco.services;

import javafx.concurrent.Task;

public class TFAemailSenderTask extends Task<Void> {
    private String recipient;
    private String subject;
    private String content;

    public TFAemailSenderTask(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    @Override
    protected Void call() {
        String desc="";
        if(subject.equals("pass"))
            desc="Your code for password recover";
        else{
            desc="Your code for logging in for the first time";
        }
        TFAFirstLogin.sendAuthEmail(recipient,desc,content);
        return null;
    }


}
