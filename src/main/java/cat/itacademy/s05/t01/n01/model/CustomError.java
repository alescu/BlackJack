package cat.itacademy.s05.t01.n01.model;

public class CustomError {
    int errorCode;
    String msg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public CustomError(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
