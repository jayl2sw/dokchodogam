package com.ssafy.dockchodogam.dto.exception.user;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class ExceededMoneyException extends CustomException {
    public ExceededMoneyException(){
        super(ErrorCode.EXCEEDED_MONEY);
    }
}
