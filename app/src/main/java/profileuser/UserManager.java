package profileuser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.bookapp.Login;

import java.util.HashMap;

public class UserManager {
    Context context;
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public static final String PREF_NAME = "User_Login";
    public static final String LOGIN = "is_user_login";
    public static final String TAIKHOAN = "tk";
    public static final String MATKHAU = "mk";
    public UserManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void UserSessorManager(String tK){
        editor.putBoolean(LOGIN, true);
        editor.putString(TAIKHOAN, tK);
        editor.apply();
    }

    public void checkLogin(){
        if(!this.isUserLogin()){
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
            ((ProfileUser) context).finish();
        }
    }

    public HashMap<String, String> userDatail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(TAIKHOAN, sharedPreferences.getString(TAIKHOAN,null));
        user.put(MATKHAU, sharedPreferences.getString(MATKHAU,null));
        return user;
    }

    public void Register(){
        editor.clear();
        editor.apply();
    }
}
