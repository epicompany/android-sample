package eu.epicompany.mobile.sampleapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.epicompany.mobile.core.datatypes.UUID4
import eu.epicompany.mobile.core.network.EpiSdk
import eu.epicompany.mobile.core.network.authentication.AccessTokenProvider
import eu.epicompany.mobile.core.network.error.NetworkErrorCallback
import eu.epicompany.mobile.core.network.model.TokenResource
import eu.epicompany.mobile.core.network.model.wallet.WalletResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainViewModel : ViewModel() {

    val requestResult = mutableStateOf("")
    var tokenValue by mutableStateOf("eyJraWQiOiJjb25zdW1lciIsImFsZyI6IlJTMjU2IiwidHlwIjoiSldUIn0.eyJzdWIiOiJmMzY3ZGE5NC1hODVkLTQ3ZjQtODQ5OC1kYjk3ZmNjY2ViNmIiLCJpYXQiOjE2ODYxNTIxMjYsImV4cCI6MTY4NjE1NTcyNiwic2NvcGUiOiJ3YWxsZXQ6cHJvdmlzaW9uaW5nIHAycDp0cmFuc2Zlck1vbmV5In0.REFVtRtHAx9LOE6ZT0GnlNRmQp3c59LKWSe5uzzz1sdqC9VRNPsYi8qcmzA4ICfe400QnavOIW9whQ3BeMliIL9ZT15uHqVScrwdFhR67UnBkoKjUq-i08rw41FaDLCKqM52my3KL4bExKMxwV6kTTm8fXCgMS8yWOScAwrvbD7jGxFrIsigD2TSbMaJr5Twyg0IJcBfVveKGkPiHZBl-pRSscNuhPPRPuhbtfRtoDKkw03RmGzyG6OzAzgWhnIMml9IpfuMjeVbqOojYlBfMqtPD_pt0kUut83SPTgYUWUYd2MOd_Pghh2_CzDKB3S4fNNm4G1X2dTkFAl_JuFuFZmVDnseSZDGvfFEotXYAmJeyRbLX7CDUfM9vgJlODW9dUq3wD7G8fGkvBEhXNDPpw3BYnA1n2iVjaknpZmiTKCd7pHmfDMVFQJUGcD6auDzL9kn7-bsP0upQ1MqxCfHSiszdSMcf6jhFEnPkiB-83IG0k9bvNBI7zn5PCmSL-trLDL47jEvZR9NjFvYUIdot-oA1nU_RWLqs1aYnWLAR1pEjoEiCOOOPgdd0rwQPQyR5BXO_03y4mBgKcC3qRVSMhQ7EoELEMJlxsaQt9fnahvIfg3W88cYlX7PVZTmXsq1nkd0AcQdN7er4cqXMY3Li5QrZmQLllkmjZuDlC3jhsQ")
    var walletId by mutableStateOf("80b88999-8217-4ed3-b5be-69577cf6345a")
    private val dataSources = EpiSdk.buildDataSources(
        baseUrl = "https://api.dev.epi.engineering",
        accessTokenProvider = object : AccessTokenProvider {
            override fun provideAccessToken(): String? {
                return tokenValue
            }
        }, networkErrorCallback = object : NetworkErrorCallback {
            override fun onUnauthorizedCall() {
                Log.e("onUnauthorizedCall", "the sdk received a 401 response, invalid token")
            }
        }, isDebug = true
    )
    private val walletNetworkDataSource = dataSources.walletNetworkDataSource()

    private val authenticationNetworkDataSource = dataSources.authenticationNetworkDataSource()

    init {
        viewModelScope.launch {
            refreshToken()
                .onFailure { requestResult.value = "refresh token failed: ${it.message}" }
                .onSuccess { requestResult.value = it.accessToken }
        }
    }

    fun fetchWallet() {
        val walletId = kotlin.runCatching { UUID.fromString(walletId) }.getOrNull() ?: UUID4()
        viewModelScope.launch {
            fetchWallet(walletId)
                .onFailure { requestResult.value = "wallet fetch failed: ${it.message}" }
                .onSuccess { requestResult.value = "wallet fetch succeeded: ${it.walletName}" }
        }
    }

    private suspend fun fetchWallet(walletId: UUID4): Result<WalletResource> {
        return withContext(Dispatchers.IO) {
            runCatching {
                walletNetworkDataSource.fetchWallet(walletId)
            }
        }
    }

    private suspend fun refreshToken(): Result<TokenResource> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val assertionJwt = "" /* generate JWT for wallet */
                authenticationNetworkDataSource.refreshAccessToken(assertionJwt)
            }
        }
    }

    fun updateToken(token: String) {
        tokenValue = token
    }

    fun updateWalletId(wallet: String) {
        walletId = wallet
    }
}