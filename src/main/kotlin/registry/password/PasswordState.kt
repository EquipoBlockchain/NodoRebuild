package registry.password

enum class PasswordState {
    EMPTY,
    VALID,
    INVALID_SIZE,
    INVALID_NO_UPPERCASE,
    INVALID_NO_LOWERCASE,
    INVALID_NO_NUMBER
}