package com.github.cxlina.smpbot.util;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ReplyManager {

    private List<String> replies;

    public ReplyManager(List<String> replies) {
        this.replies = replies;
    }

    public String getReply() {
        return replies.get(ThreadLocalRandom.current().nextInt(replies.size()));
    }
}
