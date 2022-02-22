package com.gilvano

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GrpcClientService {

    suspend fun saveUser() {
        val serverStub = createStub()

        val saveUserRequest = SaveUserRequest.newBuilder()
            .setName("Fernando")
            .setLastName("Queiroz")
            .setDocument("07284650714")
            .build()

        val saveUserResponse = serverStub.saveUser(saveUserRequest)

        println("Usuário registrado com id = " + saveUserResponse.id)
    }

    suspend fun saveUserStream() {
        val serverStub = createStub()

        val requests = generateOutgoingRequests()

        serverStub.saveUserStream(requests).collect { println("Resposta: " + it.id) }
//        collect { response ->
//            println("Resposta: " + response.id)
//        }
    }

    private fun generateOutgoingRequests(): Flow<SaveUserRequest> = flow {

        val request1 = SaveUserRequest.newBuilder()
            .setName("Eduardo")
            .setLastName("Silva")
            .setDocument("05262438594")
            .build()

        val request2 = SaveUserRequest.newBuilder()
            .setName("Carol")
            .setLastName("Souza")
            .setDocument("07262438594")
            .build()

        val request3 = SaveUserRequest.newBuilder()
            .setName("Murilo")
            .setLastName("Oliveira")
            .setDocument("09262438594")
            .build()

        val requests = listOf(request1, request2, request3)

        for (request in requests) {
            println("Requisição: " + request.name)
            emit(request)
            delay(5000)
        }
    }

    private fun createStub(): GrpcServerServiceGrpcKt.GrpcServerServiceCoroutineStub {
        val channel: Channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

        return GrpcServerServiceGrpcKt.GrpcServerServiceCoroutineStub(channel)
    }
}