package com.assessment.socialdeal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.assessment.socialdeal.ui.theme.SocialDealAssessmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialDealAssessmentTheme {
                DealApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DealAppPreview() {
    SocialDealAssessmentTheme {
        DealApp()
    }
}