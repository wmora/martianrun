package com.gamestudio24.cityescape.box2d;

import com.gamestudio24.cityescape.enums.UserDataType;

public abstract class UserData {

    protected UserDataType userDataType;

    public UserData() {

    }

    public UserDataType getUserDataType() {
        return userDataType;
    }
}
