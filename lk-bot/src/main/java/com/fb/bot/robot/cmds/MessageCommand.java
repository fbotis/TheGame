package com.fb.bot.robot.cmds;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.fb.messages.BaseMessage;

@Retention(RetentionPolicy.RUNTIME)
public @interface MessageCommand {
    Class<? extends BaseMessage> type();
}
