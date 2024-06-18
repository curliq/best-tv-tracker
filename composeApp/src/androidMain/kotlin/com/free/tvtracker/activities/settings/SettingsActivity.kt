package com.free.tvtracker.activities.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import com.free.tvtracker.core.ui.BaseActivity
import com.free.tvtracker.ui.common.theme.TvTrackerTheme
import com.free.tvtracker.ui.settings.SettingsScreen
import com.free.tvtracker.ui.settings.SettingsScreenNavAction
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvTrackerTheme {
                val navAction = { action: SettingsScreenNavAction ->
                    when (action) {
                        SettingsScreenNavAction.GoLogin -> {
                            startActivity(Intent(this, LoginActivity::class.java))
                        }

                        SettingsScreenNavAction.GoSignup -> {
                            startActivity(Intent(this, SignupActivity::class.java))
                        }
                    }
                }
                Scaffold(
                    topBar = {
                        MediumTopAppBar(
                            title = {
                                Text(
                                    text = "Settings",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            },
                            scrollBehavior = TopAppBarDefaults
                                .exitUntilCollapsedScrollBehavior(rememberTopAppBarState()),
                            navigationIcon = {
                                IconButton(onClick = { this.finish() }) {
                                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                                }
                            }
                        )
                    }
                ) { padding ->
                    SettingsScreen(viewModel = get(), navAction, padding)
                }
            }
        }
    }
}
