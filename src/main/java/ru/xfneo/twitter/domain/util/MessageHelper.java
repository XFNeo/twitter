package ru.xfneo.twitter.domain.util;

import ru.xfneo.twitter.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "No author";
    }
}
