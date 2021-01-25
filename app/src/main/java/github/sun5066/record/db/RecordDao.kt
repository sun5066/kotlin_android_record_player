package github.sun5066.record.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.sun5066.record.model.RecordData

@Dao
interface RecordDao {

    @Query("SELECT * FROM tbl_records ORDER BY id DESC")
    open fun selectAll(): LiveData<MutableList<RecordData>>

    @Query("SELECT * FROM tbl_records WHERE id= :id")
    open fun findById(id: Long): RecordData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    open fun save(recordData: RecordData)

    @Query("DELETE FROM tbl_records WHERE id= :id")
    open fun delete(id: Long)
}