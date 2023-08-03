package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user_entity")
    fun getUser() : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(entity: UserEntity)

    @Update
    fun updateMember(entity: UserEntity)

    @Delete
    fun deleteMember(entity: UserEntity)
}