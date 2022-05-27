package trashissue.rebage.data.mapper

import trashissue.rebage.data.local.entity.UserEntity
import trashissue.rebage.data.remote.payload.UserResponse
import trashissue.rebage.domain.model.User

fun UserResponse.asModel(token: String): User {
    return User(
        id = id,
        name = name,
        email = email,
        photo = photo,
        token = token
    )
}

fun UserResponse.asEntity(token: String): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        photo = photo,
        token = token
    )
}
