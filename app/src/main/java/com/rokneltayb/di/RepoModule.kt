package com.exas.crm.di


import com.exas.crm.data.repository.HelperRepositoryImpl
import com.exas.crm.data.repository.MailRepositoryImpl
import com.exas.crm.data.repository.NewRequestRepositoryImpl
import com.exas.crm.data.repository.ProjectRepositoryImpl
import com.exas.crm.data.repository.QuestionsRepositoryImpl
import com.exas.crm.data.repository.UserRepositoryImpl
import com.exas.crm.domain.repository.HelperRepository
import com.exas.crm.domain.repository.MailRepository
import com.exas.crm.domain.repository.NewRequestRepository
import com.exas.crm.domain.repository.ProjectRepository
import com.exas.crm.domain.repository.QuestionRepository
import com.exas.crm.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {


    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideProjectsRepository(projectRepositoryImpl: ProjectRepositoryImpl): ProjectRepository

    @Binds
    abstract fun provideQuestionsRepository(questionsRepositoryImpl: QuestionsRepositoryImpl): QuestionRepository

    @Binds
    abstract fun provideHelperRepository(helperRepositoryImpl: HelperRepositoryImpl): HelperRepository

    @Binds
    abstract fun provideMailRepository(mailRepositoryImpl: MailRepositoryImpl): MailRepository

    @Binds
    abstract fun provideNewRequestRepository(newRequestRepositoryImpl: NewRequestRepositoryImpl): NewRequestRepository
}