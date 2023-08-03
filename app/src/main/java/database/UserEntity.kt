package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(
    //실제 데이터베이스에 데이터를 삽입할 때 Room 라이브러리는 이 id 필드에 자동으로 증가하는 값을 할당
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "birth")
    val birth: String,
    @ColumnInfo(name = "age")
    val age: String
)
