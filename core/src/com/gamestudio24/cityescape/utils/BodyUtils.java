package com.gamestudio24.cityescape.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.gamestudio24.cityescape.box2d.UserData;
import com.gamestudio24.cityescape.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }

}
