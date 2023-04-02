package com.ldh.crm.resultEnum;

import java.io.Serializable;

public interface IResultCode extends Serializable {
    String getMessage();

    int getCode();
}
