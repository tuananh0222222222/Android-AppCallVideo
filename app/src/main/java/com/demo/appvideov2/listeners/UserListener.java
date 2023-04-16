package com.demo.appvideov2.listeners;

import com.demo.appvideov2.models.User;

public interface UserListener {
    void initiateVideoMeeting(User user);
    void initiateAudioMeeting(User user);
}
