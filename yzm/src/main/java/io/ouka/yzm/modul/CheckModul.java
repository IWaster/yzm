package io.ouka.yzm.modul;

import com.sinosig.yzm.slap.model.form.CaptchaCheckForm;

/**
 * @author ouka
 * CheckModul
 */
public class CheckModul extends CaptchaCheckForm {
    private String userName;
    private String passWord;
    public CheckModul(){
        super();
    }

    public CheckModul(String token, Integer sliceX, String userName, String passWord) {
        super(token, sliceX);
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
