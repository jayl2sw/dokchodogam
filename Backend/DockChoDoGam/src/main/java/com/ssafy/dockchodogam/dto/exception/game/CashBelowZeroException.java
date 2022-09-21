package com.ssafy.dockchodogam.dto.exception.game;

import com.ssafy.dockchodogam.dto.exception.CustomException;
import com.ssafy.dockchodogam.dto.exception.ErrorCode;

public class CashBelowZeroException extends CustomException {
    public CashBelowZeroException(){
        super(ErrorCode.CASH_BELOW_ZERO);
    }
}
