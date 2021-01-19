package github.sun5066.record.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.sun5066.record.model.RecordData

@Dao
interface RecordDao {

    @Query("SELECT * FROM tbl_records")
    open fun selectAll(): LiveData<MutableList<RecordData>>

    @Query("SELECT * FROM tbl_records WHERE id= :_id")
    open fun findById(_id: Long): RecordData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    open fun save(_recordData: RecordData)

    @Query("DELETE FROM tbl_records WHERE id= :_id")
    open fun delete(_id: Long)
}