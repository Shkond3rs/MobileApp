package shkonda.artschools.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shkonda.artschools.data.data_source.arts.category.ArtCategoryRemoteDataSource
import shkonda.artschools.data.data_source.arts.category.ArtCategoryRemoteDataSourceImp
import shkonda.artschools.data.data_source.arts.genre.ArtGenresRemoteDataSource
import shkonda.artschools.data.data_source.arts.genre.ArtGenresRemoteDataSourceImpl
import shkonda.artschools.data.data_source.arts.type.ArtTypesRemoteDataSource
import shkonda.artschools.data.data_source.arts.type.ArtTypesRemoteDataSourceImpl
import shkonda.artschools.data.data_source.auth.AuthRemoteDataSource
import shkonda.artschools.data.data_source.auth.AuthRemoteDataSourceImpl
import shkonda.artschools.data.data_source.quizzes.QuizRemoteDataSource
import shkonda.artschools.data.data_source.quizzes.QuizRemoteDataSourceImpl
import shkonda.artschools.data.data_source.user.UserRemoteDataSource
import shkonda.artschools.data.data_source.user.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    //arts
    @Binds
    @Singleton
    abstract fun bindArtCategoryRemoteDataSource(artCategoryRemoteDataSourceImpl: ArtCategoryRemoteDataSourceImp): ArtCategoryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArtTypesRemoteDataSource(artTypesRemoteDataSourceImpl: ArtTypesRemoteDataSourceImpl): ArtTypesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArtGenresRemoteDataSource(artGenresRemoteDataSourceImpl: ArtGenresRemoteDataSourceImpl): ArtGenresRemoteDataSource

    //quizzes
    @Binds
    @Singleton
    abstract fun bindQuizRemoteDataSource(quizRemoteDataSourceImpl: QuizRemoteDataSourceImpl): QuizRemoteDataSource

}