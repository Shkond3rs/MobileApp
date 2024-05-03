package shkonda.artschools.data.repository.arts

import shkonda.artschools.data.data_source.arts.type.ArtTypesRemoteDataSource
import shkonda.artschools.data.mappers.toArtTypes
import shkonda.artschools.domain.model.arts.ArtTypes
import shkonda.artschools.domain.repository.arts.ArtTypesRepository
import javax.inject.Inject

class ArtTypesRepositoryImpl @Inject constructor(private val remoteDataSource: ArtTypesRemoteDataSource) : ArtTypesRepository {
    override suspend fun getArtTypesByArtCategoryId(categoryId: Long): ArtTypes =
        remoteDataSource.getArtTypesByArtCategoryId(categoryId).toArtTypes()

}