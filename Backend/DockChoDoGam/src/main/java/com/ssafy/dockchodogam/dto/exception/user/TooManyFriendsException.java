package com.ssafy.dockchodogam.dto.exception.user;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class TooManyFriendsException extends CustomException {
    public TooManyFriendsException(){
        super(ErrorCode.TOO_MANY_FRIENDS);
    }
}
