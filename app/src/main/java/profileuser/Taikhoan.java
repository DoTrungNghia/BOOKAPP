package profileuser;

public class Taikhoan {
    private String TK;
    private String Avatar;
    private String BackGround;

    public Taikhoan(String TK, String avatar, String backGround) {
        this.TK = TK;
        Avatar = avatar;
        BackGround = backGround;
    }

    public String getTK() {
        return TK;
    }

    public void setTK(String TK) {
        this.TK = TK;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBackGround() {
        return BackGround;
    }

    public void setBackGround(String backGround) {
        BackGround = backGround;
    }
}
