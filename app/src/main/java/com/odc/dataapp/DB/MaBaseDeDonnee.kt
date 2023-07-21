package com.odc.dataapp.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.odc.dataapp.models.Comments
import com.odc.dataapp.models.CommentsDAO

@Database(entities = [Comments::class], version = 1, exportSchema = false)
abstract class MaBaseDeDonne : RoomDatabase() {
    abstract fun commentsDAO(): CommentsDAO

    companion object {
        @Volatile
        private var INSTANCE: MaBaseDeDonne? = null

        fun getInstance(context: Context): MaBaseDeDonne {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MaBaseDeDonne::class.java,
                        "ODC_DB"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}