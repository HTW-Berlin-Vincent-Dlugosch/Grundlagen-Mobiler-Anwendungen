package com.example.eventmapjava.logic;

import android.content.Context;

public interface UserManagerInterface {
    void saveCurrentUser(Context context);

    void loadCurrentUser(Context context);

    boolean currentUserExists(Context context);
}
