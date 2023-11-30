package uz.ictschool.roomcontact

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    companion object {
        @Volatile
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }
    }
}