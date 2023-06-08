package eu.epicompany.mobile.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.epicompany.mobile.sampleapp.ui.theme.SampleAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val requestResult by viewModel.requestResult
                    RequestExample(
                        modifier = Modifier
                            .fillMaxSize(),
                        requestResult = requestResult,
                        walletId = viewModel.walletId,
                        tokenValue = viewModel.tokenValue,
                        onButtonClick = viewModel::fetchWallet,
                        onTokenUpdate = viewModel::updateToken,
                        onWalletIdUpdate = viewModel::updateWalletId,

                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestExample(
    requestResult: String,
    modifier: Modifier = Modifier,
    onWalletIdUpdate: (String) -> Unit = {},
    onTokenUpdate: (String) -> Unit = {},
    tokenValue: String = "",
    walletId: String = "",
    onButtonClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    .heightIn(max = 88.dp),
                value = tokenValue, onValueChange = onTokenUpdate,
                label = {
                    Text(text = "Access token")
                })
            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    .heightIn(max = 88.dp),
                value = walletId, onValueChange = onWalletIdUpdate,
                label = {
                    Text(text = "Wallet ID")
                })
            requestResult.takeIf { it.isNotEmpty() }?.let {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = requestResult
                )
            }
            Button(onClick = onButtonClick) {
                Text(text = "Fetch wallet")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestExamplePreview() {
    SampleAppTheme {
        RequestExample("Android")
    }
}