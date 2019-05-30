package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import api.ClothesApi;

import com.example.clothingonline.R;
import com.example.clothingonline.Dashboard;
import model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etPassword = view.findViewById(R.id.etPasswordLogin);
        etUsername = view.findViewById(R.id.etUsernameLogin);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUser();
            }
        });
        return view;

    }


    private void CheckUser() {
        final String userName = etUsername.getText().toString();
        final String passWord = etPassword.getText().toString();
        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
        Call<List<Users>> listCall = clothesApi.getUsers();

        listCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> usersList = response.body();
                for(Users users:usersList){
                    String dbUname = users.getUsername();
                    String dbPass = users.getPassword();

                    if(userName.equals(dbUname) && passWord.equals(dbPass)){
                        Toast.makeText(getActivity(), "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext(), Dashboard.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getActivity(), "Username or Password incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}