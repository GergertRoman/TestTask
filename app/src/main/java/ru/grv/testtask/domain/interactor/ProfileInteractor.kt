package ru.grv.testtask.domain.interactor

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.grv.testtask.domain.repository.IBookRepository
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.domain.repository.IProfileRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepository: IProfileRepository, private val bookRepository: IBookRepository
): IProfileInteractor {

    override fun getProfileInfo(): Single<ProfileEntity> {
        return profileRepository.getProfileInfo()
    }

    override fun getBooks(): Single<List<BookEntity>> {
        return bookRepository.getBooks()
    }

    override fun writeProfileInfoInDb(entity: ProfileEntity?) {
        profileRepository.writeProfileInfoInDb(entity)
    }

    override fun writeBooksListInDb(entityList: List<BookEntity?>) {
        //bookRepository.writeBooksListInDb(entityList)
    }
}