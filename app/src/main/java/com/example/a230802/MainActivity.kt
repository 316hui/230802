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
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = UserDatabase.getInstance(this)!!

        with(binding) {
            btnSave.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    database.userDao().insertUser(
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
                    }
                }
            }
            btnDelete.setOnClickListener {
                val user = UserEntity(etName)

            }
            btnUpdate.setOnClickListener {
                val newName = etName.text.toString()
                val newAge = etAge.text.toString()
                val newBirth = etBirth.text.toString()

                if (newName.isNotBlank() && newAge.isNotBlank() && newBirth.isNotBlank()) {
                    //Dispatchers를 사용하여 코루틴이 동작할 스레드를 지정 / 메인 스레드를 차단하지 않으면서 I/O 작업 (파일 읽기쓰기, 네트워크 요청)을 수행하는 데 적합한 스레드를 제공
                    lifecycleScope.launch(Dispatchers.IO) {
                        val currentUser = database.userDao().getUser()

                        if (currentUser != null) {
                            val updatedUser = currentUser.copy(name = newName,age = newAge, birth = newBirth)
                            database.userDao().updateMember(updatedUser)
                            Toast.makeText(, "", Toast.LENGTH_SHORT).show()
                        } //update 구현

                    }
                }
            }
        }
    }
}