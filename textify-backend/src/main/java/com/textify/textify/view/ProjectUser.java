package com.textify.textify.view;

public class ProjectUser {
    public interface UserProjection {

        long getId();

        String getUsername();

        String getDisplayName();

        String getImage();

    }
}
