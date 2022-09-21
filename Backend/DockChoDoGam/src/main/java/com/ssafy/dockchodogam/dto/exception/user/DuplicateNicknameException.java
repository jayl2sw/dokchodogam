package com.ssafy.dockchodogam.dto.exception.user;


import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class DuplicateNicknameException extends CustomException {
    public DuplicateNicknameException(){
        super(ErrorCode.DUPLICATE_NICKNAME);
    }
}
