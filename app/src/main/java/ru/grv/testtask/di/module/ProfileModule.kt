package ru.grv.testtask.di.module

import dagger.Binds
import dagger.Module
import ru.grv.testtask.data.repository.BookRepository
import ru.grv.testtask.domain.repository.IBookRepository
import ru.grv.testtask.domain.interactor.IProfileInteractor
import ru.grv.testtask.presentation.profile.presenter.IProfilePresenter
import ru.grv.testtask.domain.interactor.ProfileInteractor
import ru.grv.testtask.presentation.profile.presenter.ProfilePresenter
import ru.grv.testtask.domain.repository.IProfileRepository
import ru.grv.testtask.data.repository.ProfileRepository

@Module
abstract class ProfileModule {
    // region Presentation
    @Binds
    abstract fun bindProfilePresenter(presenter: ProfilePresenter): IProfilePresenter
    // endregion

    // region Domain
    @Binds
    abstract fun bindProfileInteractor(interactor: ProfileInteractor): IProfileInteractor
    // endregion

    // region Data
    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepository): IProfileRepository

    @Binds
    abstract fun bindBookRepository(repository: BookRepository): IBookRepository
    // endregion
}
