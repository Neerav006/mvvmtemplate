package com.example.mvvm.data.repository

import com.example.mvvm.data.remote.api.ApiService
import com.example.mvvm.data.remote.api.BaseService
import com.example.mvvm.model.MoviesResponse
import com.example.mvvm.model.Post
import com.example.mvvm.model.State
import com.example.mvvm.utils.AppConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(val service: ApiService) : PostRepository {


    /**
     * Fetched the posts from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    @ExperimentalCoroutinesApi
    override suspend fun getAllPosts(): Flow<Resource<List<Post>>> {
        return object : NetworkBoundRepository<List<Post>, List<Post>>() {

            /*override suspend fun saveRemoteData(response: List<Post>){ }

            override fun fetchFromLocal(): Flow<List<Post>> {
                TODO("Not yet implemented")
            }*/

            override suspend fun fetchFromRemote(): Response<List<Post>> = service.getPosts()
        }.asFlow()
    }



    /* override fun getPostById(postId: Int): Flow<Post> {
         TODO("Not yet implemented")
     }*/

}
interface PostRepository {
    suspend fun  getAllPosts(): Flow<Resource<List<Post>>>
   // fun getPostById(postId: Int): Flow<Post>
}
