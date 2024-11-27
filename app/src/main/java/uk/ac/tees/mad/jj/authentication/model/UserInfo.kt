package uk.ac.tees.mad.jj.authentication.model

data class UserInfo(
    val name: String? = "",
    val username: String? = "",
    val email: String? = "",
    val profilePicture: String? = ""
)
