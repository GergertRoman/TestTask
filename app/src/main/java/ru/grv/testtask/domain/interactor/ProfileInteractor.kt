package ru.grv.testtask.domain.interactor

import io.reactivex.Observable
import ru.grv.testtask.domain.repository.IBookRepository
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.domain.repository.IProfileRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepository: IProfileRepository, private val bookRepository: IBookRepository
): IProfileInteractor {

    override fun getProfileInfo(): Observable<ProfileEntity> {
        return profileRepository.getProfileInfo()
    }

    override fun getBooks(): Observable<List<BookEntity>> {
        return bookRepository.getBooks()
    }

    override fun writeProfileInfoInDb(entity: ProfileEntity?) {
        profileRepository.writeProfileInfoInDb(entity)
    }

    override fun fetchProfileInfoFromDb(): Observable<ProfileEntity> {
        return profileRepository.fetchProfileInfoFromDb()
    }

    override fun writeBooksListInDb(entityList: List<BookEntity?>) {
        bookRepository.writeBooksListInDb(entityList)
    }

    override fun fetchBooksFromDb(): Observable<List<BookEntity>> {
        return bookRepository.fetchBooksFromDb()
    }
}