package com.example.a230802

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.a230802.databinding.ActivityMainBinding
import database.UserDatabase
import database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = UserDatabase.getInstance(this)!!

        with(binding) {
            btnSave.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    database.userDao().insertInfo(
                        UserEntity(
                            name = etName.text.toString(),
                            age = etAge.text.toString(),
                            birth = etBirth.text.toString()
                        )
                    )
                }
            }
            btnRead.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = database.userDao().getUser()

                    launch(Dispatchers.Main) {
                        textView.text = """
                        이름 : ${user.name}
                        나이 : ${user.age}
                        생년월일 : ${user.birth}
                    """.trimIndent()
                        // 들여쓰기를 제거하여 문자열의 왼쪽 가장자리를 정렬하는 역할 / """ <-은 문자열의 형태를 그대로 유지
                    }
                }
            }
            btnDelete.setOnClickListener {
                val userDelete = database.userDao().getUser()

                //가져온 데이터가 있다면
                if (userDelete != null) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        database.userDao().deleteInfo(userDelete)

                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "사용자 정보가 삭제되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        //UserDao 에서 entity 데이터를 다 모으는 함수 gerUser를 변수로 만들어 인자로 넣음.
                    }
                }
            }

            btnUpdate.setOnClickListener {
                val newName = etName.text.toString()
                val newAge = etAge.text.toString()
                val newBirth = etBirth.text.toString()

                lifecycleScope.launch(Dispatchers.IO) {
                    database.userDao().updateInfo(
                        UserEntity(
                            name = newName,
                            age = newAge,
                            birth = newBirth
                        )
                    )
                }

            }

        }
    }
}




