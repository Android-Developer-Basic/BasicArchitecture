package ru.otus.basicarchitecture.data.local_storage

import android.content.Context
import android.content.SharedPreferences.Editor
import ru.otus.basicarchitecture.DEFAULT_BIRTH_DATE
import ru.otus.basicarchitecture.DEFAULT_NAME
import ru.otus.basicarchitecture.DEFAULT_SURNAME
import ru.otus.basicarchitecture.USER_ADDRESS_KEY
import ru.otus.basicarchitecture.USER_BIRTH_DATE_KEY
import ru.otus.basicarchitecture.USER_CITY_KEY
import ru.otus.basicarchitecture.USER_COUNTRY_KEY
import ru.otus.basicarchitecture.USER_HOBBY_KEY
import ru.otus.basicarchitecture.USER_NAME_KEY
import ru.otus.basicarchitecture.USER_SHARED_PREFS
import ru.otus.basicarchitecture.USER_SURNAME_KEY
import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.domain.models.UserBirthDate
import ru.otus.basicarchitecture.domain.models.UserHobby
import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserSurname

class WizardCatch(context: Context): UserStorage {

    private val sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFS, Context.MODE_PRIVATE)
    private val editor:Editor = sharedPreferences.edit()
    override fun getData(): User {

        val name = sharedPreferences.getString(USER_NAME_KEY, DEFAULT_NAME)?: DEFAULT_NAME
        val surname = sharedPreferences.getString(USER_SURNAME_KEY, DEFAULT_SURNAME)?: DEFAULT_SURNAME
        val birthDate = sharedPreferences.getString(USER_BIRTH_DATE_KEY, DEFAULT_BIRTH_DATE)?: DEFAULT_BIRTH_DATE
        val country = sharedPreferences.getString(USER_COUNTRY_KEY, "")?: ""
        val city = sharedPreferences.getString(USER_CITY_KEY, "")?:""
        val address = sharedPreferences.getString(USER_ADDRESS_KEY, "")?: ""
        val hobby = sharedPreferences.getStringSet(USER_HOBBY_KEY, setOf())?: setOf()
        return User(name, surname, birthDate, country, city, address, hobby)
    }

    override fun saveUserName(userName: UserName) {
        editor.putString(USER_NAME_KEY, userName.name).apply()
    }

    override fun saveUserSurname(userSurname: UserSurname) {
        editor.putString(USER_SURNAME_KEY, userSurname.surname).apply()
    }

    override fun saveUserBirthDate(userBirthDate: UserBirthDate) {
        editor.putString(USER_BIRTH_DATE_KEY, userBirthDate.birthDate).apply()
    }

    override fun saveUserAddress(userAddress: UserAddress) {

        userAddress.apply {
            editor.apply {
                putString(USER_COUNTRY_KEY, country)
                putString(USER_CITY_KEY, city)
                putString(USER_ADDRESS_KEY, address)
                apply()
            }
        }
    }

    override fun saveUserHobby(userHobby: UserHobby) {
        editor.putStringSet(USER_HOBBY_KEY, userHobby.hobby).apply()
    }
}