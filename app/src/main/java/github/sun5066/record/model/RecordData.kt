package github.sun5066.record.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_records")
class RecordData: Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = 0

    @ColumnInfo(name = "title")
    var title: String? = ""

    @ColumnInfo(name = "writer")
    var writer: String? = ""

    @ColumnInfo(name = "record")
    var record: String? = ""

    @ColumnInfo(name = "date")
    var date: String? = ""

    @ColumnInfo(name = "time")
    var time: String? = ""

    fun toStringV2() =
        "[{RecordData {id: $id}, {title: $title}, {writer: $writer}, {record: $record}, {date: $date}, {time: $time}}]"
}