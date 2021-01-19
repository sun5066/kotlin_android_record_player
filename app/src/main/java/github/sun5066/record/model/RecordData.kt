package github.sun5066.record.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_records")
class RecordData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = 0

    @ColumnInfo(name = "title")
    var title: String? = ""

    @ColumnInfo(name = "writer")
    var writer: String? = ""

    @ColumnInfo(name = "date")
    var date: String? = ""

    @ColumnInfo(name = "time")
    var time: String? = ""
}