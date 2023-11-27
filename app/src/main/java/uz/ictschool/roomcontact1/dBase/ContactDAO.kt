package uz.ictschool.roomcontact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDAO {

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): MutableList<Contact>

    @Insert
     fun insertContact(contact: Contact)

}