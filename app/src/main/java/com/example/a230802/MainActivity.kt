package com.example.a230802

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a230802.databinding.ActivityMainBinding
import com.example.a230802.database.UserDatabase
import com.example.a230802.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = UserDatabase.getInstance(this)!!
        val userDao = database.userDao()

        viewModel = ViewModelProvider(
            this,
            MainViewModel.ViewModelFactory(userDao)
        )[MainViewModel::class.java]
        //this -> 메인 엑티비티를 정함.

        with(binding) {
            btnSave.setOnClickListener {
                viewModel.insertUser(
                    UserEntity(
                        id = 0,
                        etName.text.toString(),
                        etBirth.text.toString(),
                        etAge.text.toString(),
                    )
                )

            }
            btnRead.setOnClickListener {
                viewModel.getUser()
                viewModel.user.observe(this@MainActivity) {
                    textView.text = """
                        이름 : ${it.name}
                        생년월일 : ${it.birth}
                        나이 : ${it.age}
                    """.trimIndent()


                }
            }
            btnDelete.setOnClickListener {
                if (viewModel.user.value != null)
                    viewModel.deleteUser(viewModel.user.value!!)
            }
            btnUpdate.setOnClickListener {
                if (viewModel.user.value != null)
                    viewModel.updateUser(
                        UserEntity(
                            id = 0,
                            etName.text.toString(),
                            etBirth.text.toString(),
                            etAge.text.toString()
                        )
                    )

            }

        }

    }
}






