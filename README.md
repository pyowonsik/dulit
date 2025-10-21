# 🎀 Dulit - 커플 데이트 관리 앱 (Android)

<div align="center">

![App Icon](./readme_source/app_icon.png)

**Clean Architecture 기반 Android Native 프로젝트**

[![GitHub](https://img.shields.io/badge/GitHub-pyowonsik-181717?style=flat-square&logo=github)](https://github.com/pyowonsik/Dulit-android)
[![Backend](https://img.shields.io/badge/Backend-Dulit--server-3DDC84?style=flat-square&logo=nestjs)](https://github.com/pyowonsik/Dulit-server)

**개발 기간**: 2025.01 ~ 진행중

</div>

---

## 📑 목차

- [📱 스크린샷](#-스크린샷)
- [🛠 기술 스택](#-기술-스택)
- [💡 프로젝트 소개](#-프로젝트-소개)
- [🏗️ 아키텍처](#️-아키텍처)
- [🔥 주요 기능 & 구현 내용](#-주요-기능--구현-내용)
- [🔍 리뷰 & 회고](#-리뷰--회고)
- [⚠️ 트러블슈팅](#️-트러블슈팅)

---

## 📱 스크린샷

<p align="center">
  <img src="./readme_source/screenshot_1.png" width="200">
  <img src="./readme_source/screenshot_2.png" width="200">
  <img src="./readme_source/screenshot_3.png" width="200">
</p>

<p align="center">
  <img src="./readme_source/screenshot_4.png" width="200">
  <img src="./readme_source/screenshot_5.png" width="200">
  <img src="./readme_source/screenshot_6.png" width="200">
</p>

---

## 🛠 기술 스택

### Language & Framework

<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white"> <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white">

### Architecture & DI

<img src="https://img.shields.io/badge/Clean%20Architecture-6DB33F?style=flat-square"> <img src="https://img.shields.io/badge/MVVM-4285F4?style=flat-square"> <img src="https://img.shields.io/badge/Hilt-FF6F00?style=flat-square&logo=dagger&logoColor=white">

### Network & Data

<img src="https://img.shields.io/badge/Retrofit-48B983?style=flat-square"> <img src="https://img.shields.io/badge/OkHttp-3E4348?style=flat-square"> <img src="https://img.shields.io/badge/Socket.IO-010101?style=flat-square&logo=socketdotio&logoColor=white"> <img src="https://img.shields.io/badge/Room-4285F4?style=flat-square&logo=android&logoColor=white">

### Async & State

<img src="https://img.shields.io/badge/Coroutine-7F52FF?style=flat-square&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/Flow-7F52FF?style=flat-square&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/StateFlow-7F52FF?style=flat-square&logo=kotlin&logoColor=white">

### Security & Auth

<img src="https://img.shields.io/badge/EncryptedSharedPreferences-4285F4?style=flat-square"> <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white"> <img src="https://img.shields.io/badge/Kakao%20Login-FFCD00?style=flat-square&logo=kakao&logoColor=black">

### UI & Libraries

<img src="https://img.shields.io/badge/Material3-757575?style=flat-square&logo=materialdesign&logoColor=white"> <img src="https://img.shields.io/badge/Coil-2E7D32?style=flat-square"> <img src="https://img.shields.io/badge/Calendar%20Compose-4285F4?style=flat-square">

---

## 💡 프로젝트 소개

### 개요

**Dulit**은 커플이 함께 기념일, 데이트 계획, 추억을 관리할 수 있는 **Android Native 앱**입니다.

단순한 CRUD를 넘어, **실시간 통신**, **복잡한 인증 로직**, **보안 토큰 관리** 등 **실무 수준의 기술 스택**을 적용한 프로젝트입니다.

### 기술적 도전 과제

이 프로젝트는 다음과 같은 **기술적 난이도**를 해결하기 위해 설계되었습니다:

#### 1️⃣ Clean Architecture 구현

- **Data-Domain-Presentation** 3-Layer 완벽 분리
- **의존성 역전 원칙** (Repository Interface)
- **단일 책임 원칙** (UseCase 단위 분리)

#### 2️⃣ 실시간 통신 (Socket.IO)

- **커플 매칭 알림** (양방향 실시간 연결)
- **실시간 채팅** (메시지 송수신)
- **자동 재연결** 및 에러 핸들링

#### 3️⃣ 복잡한 인증 로직

- **JWT 자동 갱신** (TokenAuthenticator)
- **401 에러 자동 처리** (무한 루프 방지)
- **소셜 로그인** (카카오 OAuth2)

#### 4️⃣ 보안

- **EncryptedSharedPreferences** (AES256-GCM)
- **Android Keystore** 기반 토큰 암호화
- **재시도 + Fallback 전략**

#### 5️⃣ 하이브리드 데이터 소스

- **Remote First**: Retrofit 기반 서버 통신
- **Local Cache**: Room 기반 오프라인 지원
- **자동 동기화**

#### 6️⃣ 현대적 UI

- **Jetpack Compose** 선언형 UI
- **StateFlow** 기반 상태 관리
- **Material 3** 디자인 시스템

---

## 🏗️ 아키텍처

### Clean Architecture (3-Layer)

```
┌─────────────────────────────────────────────────────────┐
│                   Presentation Layer                     │
│  ┌─────────────┐  ┌──────────────┐  ┌────────────────┐ │
│  │   Screen    │  │  ViewModel   │  │  Compose UI    │ │
│  │  (Compose)  │──│  (StateFlow) │──│   Component    │ │
│  └─────────────┘  └──────────────┘  └────────────────┘ │
└────────────────────────────┬────────────────────────────┘
                             │
                   ┌─────────▼──────────┐
                   │   Domain Layer     │
                   │  ┌──────────────┐  │
                   │  │   UseCase    │  │ ← Business Logic
                   │  ├──────────────┤  │
                   │  │  Repository  │  │ ← Interface
                   │  │  (Interface) │  │
                   │  └──────────────┘  │
                   └─────────┬──────────┘
                             │
┌────────────────────────────▼────────────────────────────┐
│                      Data Layer                          │
│  ┌───────────────────┐        ┌─────────────────────┐   │
│  │  RepositoryImpl   │        │   Data Source       │   │
│  │                   │───────▶│  ┌──────────────┐   │   │
│  │  (구현체)          │        │  │  Remote API  │   │   │
│  │                   │        │  │  (Retrofit)  │   │   │
│  │                   │        │  ├──────────────┤   │   │
│  │                   │        │  │  Local DB    │   │   │
│  │                   │        │  │  (Room)      │   │   │
│  │                   │        │  ├──────────────┤   │   │
│  │                   │        │  │  Socket.IO   │   │   │
│  └───────────────────┘        │  └──────────────┘   │   │
│                                └─────────────────────┘   │
└──────────────────────────────────────────────────────────┘
```

### 패키지 구조

```
com.example.dulit/
├── core/                        # 공통 모듈
│   ├── network/                 # 네트워크 설정
│   │   ├── AuthInterceptor      # JWT 헤더 자동 추가
│   │   ├── TokenAuthenticator   # 401 시 토큰 자동 갱신
│   │   └── LoggingInterceptor   # 로깅
│   ├── local/
│   │   ├── TokenStorage         # 암호화 토큰 저장소
│   │   └── database/
│   │       └── AppDatabase      # Room Database
│   └── ui/
│       ├── component/           # 재사용 가능한 UI
│       └── theme/               # 테마 설정
│
├── di/                          # Hilt 의존성 주입
│   ├── NetworkModule
│   ├── DatabaseModule
│   └── RepositoryModule
│
├── feature/                     # 기능별 모듈
│   ├── auth/                    # 🔐 인증
│   │   ├── data/
│   │   │   ├── api/             # AuthApi
│   │   │   ├── model/           # DTO
│   │   │   └── repository/      # RepositoryImpl
│   │   ├── domain/
│   │   │   ├── model/           # Domain Model
│   │   │   ├── repository/      # Repository Interface
│   │   │   └── usecase/         # UseCase
│   │   └── presentation/
│   │       ├── LoginScreen
│   │       └── LoginViewModel
│   │
│   ├── couple/                  # 💑 커플 연결
│   │   ├── data/
│   │   │   ├── api/
│   │   │   ├── remote/
│   │   │   │   └── CoupleMatchingSocketClient  # Socket.IO
│   │   │   └── repository/
│   │   ├── domain/
│   │   └── presentation/
│   │
│   ├── home/                    # 🏠 홈 (기념일, 약속)
│   │   ├── data/
│   │   │   ├── local/           # Room Entity, DAO
│   │   │   ├── remote/          # API, DTO
│   │   │   └── repository/
│   │   ├── domain/
│   │   └── presentation/
│   │
│   ├── chat/                    # 💬 채팅
│   ├── calendar/                # 📅 캘린더
│   ├── post/                    # 📝 게시글
│   └── user/                    # 👤 사용자
│
└── navigation/                  # 네비게이션
    ├── DulitNavigation
    └── Route
```

### 데이터 플로우 예시

```kotlin
// 1️⃣ Presentation → UseCase 호출
// HomeScreen.kt
LaunchedEffect(Unit) {
    anniversaryViewModel.getAnniversaries()
}

// 2️⃣ ViewModel → UseCase 실행
// AnniversaryViewModel.kt
fun getAnniversaries() = viewModelScope.launch {
    val result = getAnniversariesUseCase()
    _anniversaries.value = result.getOrElse { emptyList() }
}

// 3️⃣ UseCase → Repository 호출
// GetAnniversariesUseCase.kt
suspend operator fun invoke(): Result<List<Anniversary>> {
    return anniversaryRepository.findAllAnniversaries()
}

// 4️⃣ Repository → Remote/Local 데이터 소스
// AnniversaryRepositoryImpl.kt
override suspend fun findAllAnniversaries(): Result<List<Anniversary>> {
    return runCatching {
        val response = anniversaryApi.findAllAnniversaries()
        response.body()!!.map { it.toDomain() }
    }
}
```

---

## 🔥 주요 기능 & 구현 내용

### 1. 🔐 JWT 자동 갱신 (TokenAuthenticator)

#### 문제 상황

AccessToken이 만료되면 **모든 API 요청이 401 에러**를 반환합니다. 사용자에게 **로그인을 다시 요구하는 것은 나쁜 UX**입니다.

#### 해결: OkHttp Authenticator

```kotlin
// core/network/TokenAuthenticator.kt
class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val rotateAccessTokenUseCase: Lazy<RotateAccessTokenUseCase>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("TokenAuthenticator", "401 에러 감지 - 토큰 갱신 시작")

        // ⭐ 재시도 방지 (무한 루프 차단)
        if (response.request.header("X-Retry-Auth") != null) {
            tokenStorage.clearTokens()
            return null
        }

        val refreshToken = tokenStorage.getRefreshToken() ?: return null

        return runBlocking {
            try {
                val result = rotateAccessTokenUseCase.get().invoke()

                result.fold(
                    onSuccess = { tokens ->
                        tokenStorage.saveAccessToken(tokens.accessToken)

                        // ⭐ 새 토큰으로 재요청
                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${tokens.accessToken}")
                            .header("X-Retry-Auth", "true")
                            .build()
                    },
                    onFailure = {
                        tokenStorage.clearTokens()
                        null
                    }
                )
            } catch (e: Exception) {
                tokenStorage.clearTokens()
                null
            }
        }
    }
}
```

#### OkHttp 클라이언트 분리

```kotlin
// di/NetworkModule.kt

// 1️⃣ AuthApi 전용 (토큰 갱신용 - Authenticator 제외)
@Provides
@Named("AuthOkHttpClient")
fun provideAuthOkHttpClient(
    loggingInterceptor: LoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

// 2️⃣ 일반 API 전용 (Authenticator 포함)
@Provides
@Named("MainOkHttpClient")
fun provideMainOkHttpClient(
    authInterceptor: AuthInterceptor,
    tokenAuthenticator: TokenAuthenticator,  // ⭐ 자동 갱신
    loggingInterceptor: LoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)  // ⭐ 401 시 자동 갱신
        .addInterceptor(loggingInterceptor)
        .build()
}
```

#### 동작 흐름

```
[Client]
   ↓ API 요청 (AccessToken 만료됨)
[Server]
   ↓ 401 Unauthorized
[TokenAuthenticator]
   ↓ RefreshToken으로 토큰 갱신 API 호출
[Server]
   ↓ 새로운 AccessToken 발급
[TokenAuthenticator]
   ↓ 새 토큰 저장 + 원래 요청 재시도
[Server]
   ↓ 200 OK
[Client]
   ✅ 사용자는 로그인 화면으로 튕기지 않음!
```

#### 핵심 포인트

✅ **무한 루프 방지**: `X-Retry-Auth` 헤더로 재시도 1회 제한  
✅ **OkHttp 클라이언트 분리**: 토큰 갱신 API는 Authenticator 제외  
✅ **Lazy 주입**: 순환 참조 방지 (`Lazy<RotateAccessTokenUseCase>`)  
✅ **runBlocking 사용**: Authenticator는 Suspend 불가

---

### 2. 🔒 EncryptedSharedPreferences (보안 토큰 저장)

#### 왜 필요한가?

일반 SharedPreferences는 **평문(Plain Text)으로 저장**되어 **루팅된 기기에서 토큰 탈취 가능**합니다.

#### Android Keystore 기반 암호화

```kotlin
// core/local/TokenStorage.kt
class TokenStorage(private val context: Context) {

    // ⭐ Android Keystore 기반 MasterKey
    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    // ⭐ 암호화된 SharedPreferences
    private val sharedPreferences: SharedPreferences by lazy {
        createEncryptedPreferences()
    }

    private fun createEncryptedPreferences(): SharedPreferences {
        return runCatching {
            // 1차 시도
            EncryptedSharedPreferences.create(
                context,
                "token_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }.recoverCatching {
            // 2차 시도 (손상된 파일 정리 후 재시도)
            deleteCorruptedFiles()
            EncryptedSharedPreferences.create(...)
        }.getOrElse {
            // Fallback: 일반 SharedPreferences
            context.getSharedPreferences("token_prefs_fallback", Context.MODE_PRIVATE)
        }
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit()
            .putString("access_token", token)
            .apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }
}
```

#### 암호화 전략

| 단계         | 동작                            | 결과                             |
| ------------ | ------------------------------- | -------------------------------- |
| **1차 시도** | EncryptedSharedPreferences 생성 | 성공 → 암호화 저장소 사용        |
| **2차 시도** | 손상된 파일 삭제 후 재시도      | 성공 → 암호화 저장소 사용        |
| **Fallback** | 일반 SharedPreferences 사용     | 암호화 없지만 **앱 크래시 방지** |

#### 보안 수준

✅ **AES256-GCM** 암호화 (군사 수준)  
✅ **Android Keystore** 저장 (하드웨어 보안 모듈)  
✅ **루팅 기기에서도 일정 수준 보호**  
✅ **토큰 탈취 난이도 극상**

---

### 3. 💑 Socket.IO 실시간 커플 매칭

#### 기능 설명

1. 사용자 A가 **초대 코드 생성**
2. 사용자 B가 **초대 코드 입력**
3. 백엔드에서 **커플 매칭 성공**
4. **양쪽에 실시간 알림** (Socket.IO)

#### Socket.IO 클라이언트

```kotlin
// feature/couple/data/remote/CoupleMatchingSocketClient.kt
@Singleton
class CoupleMatchingSocketClient @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    private var socket: Socket? = null

    fun connect(userId: String): Flow<MatchingSocketEvent> = callbackFlow {
        try {
            val accessToken = tokenStorage.getAccessToken() ?: ""

            val options = IO.Options().apply {
                query = "userId=$userId&token=Bearer $accessToken"
                transports = arrayOf("polling", "websocket")
                reconnection = true
                reconnectionAttempts = 5
                reconnectionDelay = 2000
                timeout = 20000
                forceNew = true
            }

            socket = IO.socket("$SOCKET_URL/notification", options)

            // 연결 성공
            socket?.on(Socket.EVENT_CONNECT) {
                Log.i("CoupleMatchingSocket", "✅ 소켓 연결 성공!")
                trySend(MatchingSocketEvent.Connected)
            }

            // 매칭 알림
            socket?.on("matchedNotification") { args ->
                val message = args.getOrNull(0) as? String
                Log.i("CoupleMatchingSocket", "📩 매칭 알림 수신: $message")
                trySend(MatchingSocketEvent.Matched(message ?: "커플 연결 완료!"))
            }

            socket?.connect()

        } catch (e: Exception) {
            trySend(MatchingSocketEvent.Error(e.message ?: "연결 실패"))
        }

        awaitClose {
            disconnect()
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
    }
}

sealed class MatchingSocketEvent {
    object Connected : MatchingSocketEvent()
    object Disconnected : MatchingSocketEvent()
    data class Matched(val message: String) : MatchingSocketEvent()
    data class Error(val message: String) : MatchingSocketEvent()
}
```

#### ViewModel에서 사용

```kotlin
// feature/couple/presentation/CoupleMatchingViewModel.kt
@HiltViewModel
class CoupleMatchingViewModel @Inject constructor(
    private val connectMatchingSocketUseCase: ConnectMatchingSocketUseCase
) : ViewModel() {

    private val _matchingState = MutableStateFlow<MatchingState>(MatchingState.Idle)
    val matchingState: StateFlow<MatchingState> = _matchingState

    fun startMatching(userId: String) {
        viewModelScope.launch {
            connectMatchingSocketUseCase(userId).collect { event ->
                when (event) {
                    is MatchingSocketEvent.Connected -> {
                        _matchingState.value = MatchingState.Waiting
                    }
                    is MatchingSocketEvent.Matched -> {
                        _matchingState.value = MatchingState.Success(event.message)
                    }
                    is MatchingSocketEvent.Error -> {
                        _matchingState.value = MatchingState.Error(event.message)
                    }
                    else -> {}
                }
            }
        }
    }
}
```

#### 핵심 포인트

✅ **callbackFlow**: Socket 이벤트를 Flow로 변환  
✅ **awaitClose**: ViewModel 종료 시 자동 소켓 해제  
✅ **Reconnection 전략**: 최대 5회 재시도  
✅ **JWT 인증**: Socket 연결 시 Query Parameter로 토큰 전달

---

### 4. 💬 실시간 채팅 (Socket.IO)

#### 채팅 ViewModel

```kotlin
// feature/chat/presentation/ChatViewModel.kt
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private var socket: Socket? = null

    private val _chatState = MutableStateFlow<ChatState>(ChatState.Idle)
    val chatState: StateFlow<ChatState> = _chatState

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun connectChatSocket() {
        viewModelScope.launch {
            try {
                val accessToken = tokenStorage.getAccessToken() ?: ""

                val options = IO.Options().apply {
                    query = "token=Bearer $accessToken"
                    transports = arrayOf("polling", "websocket")
                    reconnection = true
                }

                socket = IO.socket("$SOCKET_URL/chat", options)

                // 연결 성공
                socket?.on(Socket.EVENT_CONNECT) {
                    _chatState.value = ChatState.Connected
                }

                // 메시지 수신
                socket?.on("sendMessage") { args ->
                    val data = args.getOrNull(0) as? JSONObject
                    // 메시지 파싱 후 _messages에 추가
                }

                socket?.connect()

            } catch (e: Exception) {
                _chatState.value = ChatState.Error(e.message ?: "연결 실패")
            }
        }
    }

    fun sendMessage(message: String) {
        try {
            val data = JSONObject().apply {
                put("message", message)
            }
            socket?.emit("sendMessage", data)
        } catch (e: Exception) {
            Log.e("ChatViewModel", "메시지 전송 실패", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        socket?.disconnect()
        socket?.off()
        socket = null
    }
}
```

#### 핵심 포인트

✅ **양방향 통신**: `emit(sendMessage)` + `on(sendMessage)`  
✅ **onCleared()**: ViewModel 종료 시 소켓 정리  
✅ **StateFlow**: UI에 실시간 메시지 업데이트

---

### 5. 🏠 Room + Retrofit 하이브리드 데이터 소스

#### Repository 패턴

```kotlin
// feature/home/data/repository/AnniversaryRepositoryImpl.kt
class AnniversaryRepositoryImpl @Inject constructor(
    private val anniversaryApi: AnniversaryApi,  // Remote
    private val anniversaryDao: AnniversaryDao   // Local
) : AnniversaryRepository {

    // ⭐ Remote 우선 + Local 캐싱
    override suspend fun findAllAnniversaries(): Result<List<Anniversary>> {
        return runCatching {
            // 1️⃣ 서버에서 최신 데이터 조회
            val response = anniversaryApi.findAllAnniversaries()
            val anniversaries = response.body()!!.map { it.toDomain() }

            // 2️⃣ 로컬 DB에 캐싱
            anniversaryDao.insertAll(anniversaries.map { AnniversaryEntity.fromDomain(it) })

            anniversaries
        }.recoverCatching {
            // 3️⃣ 서버 실패 시 로컬 DB에서 조회
            anniversaryDao.findAll().map { it.toDomain() }
        }
    }

    override suspend fun createAnniversary(request: CreateAnniversaryRequest): Result<Anniversary> {
        return runCatching {
            val response = anniversaryApi.createAnniversary(request.toDto())
            val anniversary = response.body()!!.toDomain()

            // 로컬 DB에도 저장
            anniversaryDao.insert(AnniversaryEntity.fromDomain(anniversary))

            anniversary
        }
    }
}
```

#### Room Entity

```kotlin
// feature/home/data/local/entity/AnniversaryEntity.kt
@Entity(tableName = "anniversaries")
data class AnniversaryEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val date: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun toDomain() = Anniversary(
        id = id,
        title = title,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
```

#### 데이터 흐름

```
┌────────────────────────────────────────────┐
│          ViewModel (StateFlow)             │
└───────────────┬────────────────────────────┘
                │
┌───────────────▼────────────────────────────┐
│           UseCase (비즈니스 로직)           │
└───────────────┬────────────────────────────┘
                │
┌───────────────▼────────────────────────────┐
│       Repository (데이터 조정자)            │
│  ┌─────────────────┬──────────────────┐   │
│  │  Remote (API)   │  Local (Room)    │   │
│  │  ✅ 최신 데이터  │  ✅ 오프라인 지원 │   │
│  └─────────────────┴──────────────────┘   │
└────────────────────────────────────────────┘
```

#### 핵심 포인트

✅ **Remote First**: 서버 우선, 실패 시 로컬 Fallback  
✅ **자동 캐싱**: API 응답을 Room에 저장  
✅ **오프라인 지원**: 네트워크 끊겨도 로컬 데이터 표시  
✅ **Single Source of Truth**: Repository가 데이터 출처 단일화

---

### 6. 🎨 Jetpack Compose UI

#### 선언형 UI

```kotlin
// feature/home/presentation/HomeScreen.kt
@Composable
fun HomeScreen(
    anniversaryViewModel: AnniversaryViewModel = hiltViewModel()
) {
    val anniversaries by anniversaryViewModel.anniversaries.collectAsState()
    val anniversaryState by anniversaryViewModel.anniversaryState.collectAsState()

    // 초기 데이터 로드
    LaunchedEffect(Unit) {
        anniversaryViewModel.getAnniversaries()
    }

    when (anniversaryState) {
        is AnniversaryState.Loading -> LoadingIndicator()
        is AnniversaryState.Success -> {
            LazyColumn {
                items(anniversaries) { anniversary ->
                    AnniversaryCard(
                        item = anniversary,
                        onEdit = { /* 수정 */ },
                        onDelete = { anniversaryViewModel.deleteAnniversary(it.id) }
                    )
                }
            }
        }
        is AnniversaryState.Error -> ErrorScreen()
    }
}
```

#### 상태 관리 (StateFlow)

```kotlin
// feature/home/presentation/viewmodel/AnniversaryViewModel.kt
@HiltViewModel
class AnniversaryViewModel @Inject constructor(
    private val getAnniversariesUseCase: GetAnniversariesUseCase,
    private val deleteAnniversaryUseCase: DeleteAnniversaryUseCase
) : ViewModel() {

    private val _anniversaries = MutableStateFlow<List<Anniversary>>(emptyList())
    val anniversaries: StateFlow<List<Anniversary>> = _anniversaries

    private val _anniversaryState = MutableStateFlow<AnniversaryState>(AnniversaryState.Idle)
    val anniversaryState: StateFlow<AnniversaryState> = _anniversaryState

    fun getAnniversaries() = viewModelScope.launch {
        _anniversaryState.value = AnniversaryState.Loading

        val result = getAnniversariesUseCase()

        result.fold(
            onSuccess = { list ->
                _anniversaries.value = list
                _anniversaryState.value = AnniversaryState.Success
            },
            onFailure = { error ->
                _anniversaryState.value = AnniversaryState.Error(error.message ?: "")
            }
        )
    }

    fun deleteAnniversary(id: Int) = viewModelScope.launch {
        deleteAnniversaryUseCase(id)
        getAnniversaries()  // 삭제 후 재조회
    }
}

sealed class AnniversaryState {
    object Idle : AnniversaryState()
    object Loading : AnniversaryState()
    object Success : AnniversaryState()
    data class Error(val message: String) : AnniversaryState()
}
```

#### 핵심 포인트

✅ **Recomposition**: State 변경 시 자동 UI 업데이트  
✅ **collectAsState()**: Flow → Compose State 변환  
✅ **LaunchedEffect**: Side Effect 실행  
✅ **hiltViewModel()**: Hilt DI로 ViewModel 주입

---

### 7. 💉 Hilt 의존성 주입

#### Application 설정

```kotlin
@HiltAndroidApp
class DulitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }
}
```

#### Module 예시

```kotlin
// di/NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStorage: TokenStorage): AuthInterceptor {
        return AuthInterceptor(tokenStorage)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenStorage: TokenStorage,
        rotateAccessTokenUseCase: Lazy<RotateAccessTokenUseCase>
    ): TokenAuthenticator {
        return TokenAuthenticator(tokenStorage, rotateAccessTokenUseCase)
    }

    @Provides
    @Singleton
    @Named("MainOkHttpClient")
    fun provideMainOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }
}
```

#### ViewModel 주입

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    val tokenStorage: TokenStorage
) : ViewModel() {
    // ...
}
```

#### 핵심 포인트

✅ **@HiltAndroidApp**: Application에 Hilt 활성화  
✅ **@Provides**: 의존성 제공  
✅ **@Singleton**: 싱글톤 스코프  
✅ **@Named**: 같은 타입 구분 (AuthOkHttpClient vs MainOkHttpClient)  
✅ **Lazy<T>**: 순환 참조 방지

---

## 🔍 리뷰 & 회고

### 배운 점

#### 1. Clean Architecture의 진정한 가치

**"처음에는 과한 설계라고 생각했지만, 개발이 진행될수록 그 가치를 깨달았습니다."**

**왜 필수인가?**

- **레이어 분리**: Data-Domain-Presentation이 명확하게 나뉘어야 테스트 가능
- **의존성 역전**: Domain은 Data를 모르고, Interface만 알아야 함
- **확장성**: 새로운 데이터 소스(GraphQL, WebSocket) 추가 시 Repository만 수정
- **팀 협업**: 각 레이어의 책임이 명확해서 코드 리뷰가 쉬워짐

**실제 경험한 장점**

예를 들어, Anniversary 기능에 **로컬 캐싱**을 추가할 때:

- ViewModel, UseCase는 **수정 불필요**
- Repository 구현체만 수정 (Remote + Local 로직 추가)
- Domain Layer는 여전히 "데이터를 가져온다"는 추상적 개념만 알고 있음

이것이 **"변경에 닫혀있고 확장에 열려있다"**는 OCP의 실체였습니다.

#### 2. Hilt의 강력함

**"의존성 주입이 이렇게 편할 수 있다니!"**

```kotlin
// ❌ 수동 DI (의존성 직접 생성)
class AnniversaryViewModel(
    private val getAnniversariesUseCase: GetAnniversariesUseCase
) : ViewModel() {
    // 어디선가 수동으로 생성해서 넘겨줘야 함
}

// ✅ Hilt (자동 주입)
@HiltViewModel
class AnniversaryViewModel @Inject constructor(
    private val getAnniversariesUseCase: GetAnniversariesUseCase
) : ViewModel()
// Composable에서 hiltViewModel()로 자동 주입!
```

**Hilt의 핵심 장점**

✅ **컴파일 타임 체크**: 의존성 그래프 누락 시 빌드 실패  
✅ **보일러플레이트 제거**: Module만 정의하면 자동 주입  
✅ **생명주기 관리**: Singleton, ViewModelScoped 등 자동 관리  
✅ **테스트 용이**: Mock 객체 주입이 간단함

#### 3. Jetpack Compose의 선언형 UI

**"XML을 벗어나니 UI 개발이 이렇게 즐거울 수 있다니!"**

**Compose의 핵심 개념**

```kotlin
// 선언형 UI: 상태(State)가 변경되면 UI가 자동 업데이트
@Composable
fun AnniversaryList(anniversaries: List<Anniversary>) {
    LazyColumn {
        items(anniversaries) { anniversary ->
            AnniversaryCard(item = anniversary)
        }
    }
}

// StateFlow 변경 시 자동 Recomposition
val anniversaries by viewModel.anniversaries.collectAsState()
```

**배운 핵심 포인트**

✅ **Recomposition 최적화**: 변경된 Composable만 재구성  
✅ **remember**: 상태 기억 (화면 회전에도 유지되지 않음)  
✅ **LaunchedEffect**: Coroutine 기반 Side Effect  
✅ **collectAsState()**: Flow → State 변환  
✅ **key 파라미터**: LazyColumn 성능 최적화

#### 4. Socket.IO를 Kotlin Flow로 통합하기

**"콜백 기반 Socket.IO를 Kotlin Coroutine과 통합하는 것이 가장 어려웠습니다."**

**문제점**

Socket.IO는 **JavaScript 스타일 콜백 기반**인데, Android는 **Kotlin Flow 기반**입니다.

```kotlin
// ❌ 콜백 지옥에 빠질 뻔한 코드
socket.on("matchedNotification") { args ->
    // 여기서 ViewModel에 어떻게 전달하지?
    // StateFlow를 어떻게 업데이트하지?
}
```

**해결: callbackFlow**

```kotlin
// ✅ Socket 이벤트를 Flow로 변환
fun connect(): Flow<MatchingSocketEvent> = callbackFlow {
    val socket = IO.socket("http://localhost:3000", options)

    socket.on("matchedNotification") { args ->
        trySend(MatchingSocketEvent.Matched(args[0] as String))
    }

    socket.connect()

    awaitClose {
        socket.disconnect()  // Flow 종료 시 자동 정리
    }
}
```

**배운 점**

✅ **callbackFlow**: 콜백을 Flow로 변환  
✅ **trySend**: Flow로 이벤트 전달  
✅ **awaitClose**: 리소스 정리 (메모리 누수 방지)  
✅ **Lifecycle 인식**: ViewModel 종료 시 자동 소켓 해제

#### 5. TokenAuthenticator의 우아함

**"OkHttp Authenticator의 설계가 정말 우아하다고 느꼈습니다."**

**설계의 아름다움: 책임 분리**

```kotlin
// 1️⃣ AuthInterceptor: 모든 요청에 토큰 추가
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStorage.getAccessToken()
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}

// 2️⃣ TokenAuthenticator: 401 에러 시에만 작동
class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 401 에러 발생 시에만 호출됨!
        val newToken = refreshToken()
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
}
```

**핵심 포인트**

✅ **401 전용**: 인증 실패 시에만 자동 호출  
✅ **재요청 자동화**: 새 토큰으로 원래 요청 재시도  
✅ **사용자 무감각**: 로그인 화면으로 튕기지 않음  
✅ **중복 방지**: `X-Retry-Auth` 헤더로 무한 루프 차단

---

## ⚠️ 트러블슈팅

### 1. 순환 참조 문제 (TokenAuthenticator ↔ RotateAccessTokenUseCase)

#### 문제

```kotlin
// ❌ 순환 참조 발생
class TokenAuthenticator @Inject constructor(
    private val rotateAccessTokenUseCase: RotateAccessTokenUseCase
) : Authenticator

class RotateAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
)

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi  // ← OkHttp 클라이언트 필요
)

// OkHttp 클라이언트 생성 시 TokenAuthenticator 필요
// → 순환 참조!
```

#### 해결: Lazy 주입

```kotlin
class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val rotateAccessTokenUseCase: Lazy<RotateAccessTokenUseCase>  // ⭐ Lazy
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val result = rotateAccessTokenUseCase.get().invoke()  // ⭐ get()으로 지연 호출
            // ...
        }
    }
}
```

**핵심**: `Lazy<T>`는 **실제 사용 시점에 인스턴스 생성**하여 순환 참조 방지

---

### 2. EncryptedSharedPreferences 초기화 실패

#### 문제

```
java.security.KeyStoreException: Failed to decrypt keys
```

기기 재부팅, 앱 업데이트 등으로 **MasterKey가 손상**되면 EncryptedSharedPreferences 초기화 실패

#### 해결: 재시도 + Fallback 전략

```kotlin
private fun createEncryptedPreferences(): SharedPreferences {
    return runCatching {
        // 1차 시도
        EncryptedSharedPreferences.create(...)
    }.recoverCatching {
        // 2차 시도 (파일 정리 후 재시도)
        deleteCorruptedFiles()
        EncryptedSharedPreferences.create(...)
    }.getOrElse {
        // Fallback: 일반 SharedPreferences
        context.getSharedPreferences("token_prefs_fallback", Context.MODE_PRIVATE)
    }
}
```

**핵심**: **보안과 안정성의 균형**. 암호화 실패해도 **앱 크래시는 방지**.

---

### 3. Socket.IO Reconnection 실패

#### 문제

소켓 연결이 끊긴 후 **자동 재연결이 안 됨**.

#### 해결: `forceNew = true`

```kotlin
val options = IO.Options().apply {
    reconnection = true
    reconnectionAttempts = 5
    reconnectionDelay = 2000
    forceNew = true  // ⭐ 기존 소켓 재사용 안 함
}
```

**핵심**: **기존 소켓 인스턴스**가 남아있으면 재연결 실패. `forceNew`로 새 인스턴스 생성.

---

### 4. Room + Retrofit 동기화 문제

#### 문제

서버에서 데이터를 삭제했는데, **로컬 DB에는 남아있어서 UI에 계속 표시**됨.

#### 해결: Remote First + Local Cache

```kotlin
override suspend fun findAllAnniversaries(): Result<List<Anniversary>> {
    return runCatching {
        // 1️⃣ 서버에서 최신 데이터 조회
        val response = anniversaryApi.findAllAnniversaries()
        val anniversaries = response.body()!!.map { it.toDomain() }

        // 2️⃣ 로컬 DB 전체 삭제 후 재저장
        anniversaryDao.deleteAll()
        anniversaryDao.insertAll(anniversaries.map { AnniversaryEntity.fromDomain(it) })

        anniversaries
    }.recoverCatching {
        // 3️⃣ 서버 실패 시에만 로컬 DB 사용
        anniversaryDao.findAll().map { it.toDomain() }
    }
}
```

**핵심**: **서버가 Single Source of Truth**. 로컬은 캐시일 뿐.

---

### 5. Compose Recomposition 성능 이슈

#### 문제

`List<Anniversary>`를 `LazyColumn`으로 표시할 때, **하나만 수정해도 전체 리스트가 Recomposition**됨.

#### 해결: `key` 파라미터

```kotlin
LazyColumn {
    items(
        items = anniversaries,
        key = { anniversary -> anniversary.id }  // ⭐ 고유 키 지정
    ) { anniversary ->
        AnniversaryCard(item = anniversary)
    }
}
```

**핵심**: `key`를 지정하면 **변경된 아이템만 Recomposition**됨.

---
