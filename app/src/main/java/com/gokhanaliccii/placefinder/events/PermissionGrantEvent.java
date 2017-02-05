package com.gokhanaliccii.placefinder.events;

import com.gokhanaliccii.placefinder.enums.PermissionType;

/**
 * Created by gokhan on 04/02/17.
 */

public class PermissionGrantEvent {

    private PermissionType permissionType=PermissionType.NONE;
    private boolean granted;

    public boolean isGranted() {
        return granted;
    }

    public PermissionGrantEvent(boolean granted) {

        this.granted = granted;
    }

    public PermissionGrantEvent(PermissionType permissionType, boolean granted) {

        this.permissionType = permissionType;
        this.granted = granted;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }
}
