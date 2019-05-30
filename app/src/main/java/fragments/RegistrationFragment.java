package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clothingonline.R;

import api.ClothesApi;
import model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText etUsername, etPassword, etRePassword, etUserFname, etUserLname;
    private Button btnRegister;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        etPassword = view.findViewById(R.id.etPasswordRegistration);
        etUserFname = view.findViewById(R.id.etUserFname);
        etUserLname = view.findViewById(R.id.etUserLname);
        etRePassword = view.findViewById(R.id.etConfirmPasswordRegistration);
        etUsername = view.findViewById(R.id.etUsernameRegistration);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();

                if(password.equals(rePassword)){
                    NewUser();
                }
                else{
                    Toast.makeText(getActivity(), "Password Doesn't Match", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void NewUser() {
        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
        String userFname = etUserFname.getText().toString();
        String userLname = etUserLname.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        Users users = new Users(userFname,userLname,username,password);

        Call<Void> call = clothesApi.addUser(users);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "User added successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}