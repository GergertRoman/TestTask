package ru.grv.testtask.domain.interactor

import io.reactivex.Single
import ru.grv.testtask.domain.repository.IBookRepository
import ru.grv.testtask.domain.repository.IProfileRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepository: IProfileRepository, private val bookRepository: IBookRepository
): IProfileInteractor {

    override fun getProfileInfo() = Single.fromCallable {
        var profileInfo = profileRepository.getProfileInfo().blockingGet()
        if (profileInfo == null) {
            profileInfo = profileRepository.fetchProfileInfo().blockingGet()
            profileRepository.writeProfileInfoInDb(profileInfo)
        }
        profileInfo
    }

    override fun getBooks() = Single.fromCallable {
        var books = bookRepository.getBooks().blockingGet()
        if (books.isEmpty()) {
            books = bookRepository.fetchBooks().blockingGet()
            bookRepository.writeBooksListInDb(books)
        }
        books
    }
}