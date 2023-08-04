package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    //생성자x -> 클래스의 단일 인스턴스를 생성하는데 사용되며, 이를 통해 정적 메서드와 속성을 클래스에 추가할 수 있다.
    companion object {
        private var instance: UserDatabase? = null

        @Synchronized //Java와 Kotlin에서 사용되는 동기화(synchronization) 관련 어노테이션
        fun getInstance(context: Context): UserDatabase? {
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "database"
                ).build()
            }
            return instance
        }
    }
}