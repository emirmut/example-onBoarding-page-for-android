package emir.mut.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import emir.mut.onboarding.ui.theme.OnBoardingTheme
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
//    var locationNotificationsPopUpEnabled: MutableState<Boolean> = mutableStateOf(false)
//    var contactNotificationsPopUpEnabled: MutableState<Boolean> = mutableStateOf(false)
//    var appNotificationsPopUpEnabled: MutableState<Boolean> = mutableStateOf(false)
    var notificationState: MutableState<Int> = mutableStateOf(0)
    companion object {
        private const val ACCESS_FINE_LOCATION_CODE = 100
        private const val ACCESS_COARSE_LOCATION_CODE = 101
        private const val ACCESS_BACKGROUND_LOCATION_CODE = 102
        private const val READ_CONTACTS_CODE = 103
        private const val WRITE_CONTACTS_CODE = 104
        private const val POST_NOTIFICATIONS_CODE = 105
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnBoardingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    main(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun main(modifier: Modifier) {
        if (notificationState.value == 0) {
            locationNotifications(modifier = Modifier)
        } else if (notificationState.value == 1) {
            contactNotifications(modifier = Modifier)
        } else if (notificationState.value == 2) {
            appNotifications(modifier = Modifier)
        } else if (notificationState.value == 3) {
            welcomePage(modifier = Modifier)
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            // permission already granted
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun locationNotifications(modifier: Modifier) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.location_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
//      val coroutineScope = rememberCoroutineScope()
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_background)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp),
                horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress
                    },
                    modifier = Modifier
                        .size(500.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(32.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                )
            ) {
                Column(modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bildirimlere izin vererek her şeyden haberdar olun!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 24.dp),
                        color = colorResource(id = R.color.golden_global)
                    )
                    Text(
                        text = "Bu uygulamada bildirimlere izin vererek size özel ve diğer tüm fırsatları yakalayabilir " +
                                "ve aynı zamanda hesabınızda olan banka işlemlerini kolayca görebilirsiniz.",
                        color = colorResource(id = R.color.notifications_text),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(1.5f, TextUnitType.Em),
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentWidth()
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 32.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            notificationState.value = 1
                        }
                    ) {
                        Text(
                            text = "Geç",
                            color = colorResource(id = R.color.skip_button)
                        )
                    }
                    Spacer(modifier = Modifier
                        .weight(1f))
                    Button(
                        onClick = {
                            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                                ACCESS_FINE_LOCATION_CODE)
                            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                                ACCESS_COARSE_LOCATION_CODE)
                            checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                ACCESS_BACKGROUND_LOCATION_CODE)
                            notificationState.value = 1
//                            coroutineScope.launch {
//                                locationNotificationsPopUpEnabled.value = true
//                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.button_background),
                            contentColor = Color.White, // alttaki colorFilter'ın işleviyle aynı işlev
                            disabledContainerColor = Color.Unspecified,
                            disabledContentColor = Color.Unspecified
                        ),
                        modifier = Modifier
                            .background(colorResource(id = R.color.button_background),
                                RoundedCornerShape(25.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tailed_right_arrow),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
//                    if (locationNotificationsPopUpEnabled.value) {
//                         CustomDialog(openDialogCustom = mutableStateOf(false))
//                    }
                }
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun contactNotifications(modifier: Modifier) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.contact_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        val coroutineScope = rememberCoroutineScope()
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_background)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp),
                horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress
                    },
                    modifier = Modifier
                        .size(500.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(32.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                )
            ) {
                Column(modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bildirimlere izin vererek her şeyden haberdar olun!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 24.dp),
                        color = colorResource(id = R.color.golden_global)
                    )
                    Text(
                        text = "Bu uygulamada bildirimlere izin vererek size özel ve diğer tüm fırsatları yakalayabilir " +
                                "ve aynı zamanda hesabınızda olan banka işlemlerini kolayca görebilirsiniz.",
                        color = colorResource(id = R.color.notifications_text),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(1.5f, TextUnitType.Em),
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentWidth()
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 32.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            notificationState.value = 2
                        }
                    ) {
                        Text(
                            text = "Geç",
                            color = colorResource(id = R.color.skip_button)
                        )
                    }
                    Spacer(modifier = Modifier
                        .weight(1f))
                    Button(
                        onClick = {
                            checkPermission(Manifest.permission.READ_CONTACTS,
                                READ_CONTACTS_CODE)
                            checkPermission(Manifest.permission.WRITE_CONTACTS,
                                WRITE_CONTACTS_CODE)
                            notificationState.value = 2
//                          coroutineScope.launch {
//                              contactNotificationsPopUpEnabled.value = true
//                          }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.button_background),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Unspecified,
                            disabledContentColor = Color.Unspecified
                        ),
                        modifier = Modifier
                            .background(colorResource(id = R.color.button_background),
                                RoundedCornerShape(25.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tailed_right_arrow),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
//                    if (contactNotificationsPopUpEnabled.value) {
//                         CustomDialog(openDialogCustom = mutableStateOf(false))
//                    }
                }
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun appNotifications(modifier: Modifier) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.app_notifications_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        val coroutineScope = rememberCoroutineScope()
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_background)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp),
                horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress
                    },
                    modifier = Modifier
                        .size(500.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(32.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                )
            ) {
                Column(modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bildirimlere izin vererek her şeyden haberdar olun!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 24.dp),
                        color = colorResource(id = R.color.golden_global)
                    )
                    Text(
                        text = "Bu uygulamada bildirimlere izin vererek size özel ve diğer tüm fırsatları yakalayabilir " +
                                "ve aynı zamanda hesabınızda olan banka işlemlerini kolayca görebilirsiniz.",
                        color = colorResource(id = R.color.notifications_text),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(1.5f, TextUnitType.Em),
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentWidth()
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 32.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            notificationState.value = 3
                        }
                    ) {
                        Text(
                            text = "Geç",
                            color = colorResource(id = R.color.skip_button)
                        )
                    }
                    Spacer(modifier = Modifier
                        .weight(1f))
                    Button(
                        onClick = {
                            checkPermission(Manifest.permission.POST_NOTIFICATIONS,
                                POST_NOTIFICATIONS_CODE)
                            notificationState.value = 3
//                          coroutineScope.launch {
//                              appNotificationsPopUpEnabled.value = true
//                          }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.button_background),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Unspecified,
                            disabledContentColor = Color.Unspecified
                        ),
                        modifier = Modifier
                            .background(colorResource(id = R.color.button_background),
                                RoundedCornerShape(25.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tailed_right_arrow),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
//                    if (appNotificationsPopUpEnabled.value) {
//                         CustomDialog(openDialogCustom = mutableStateOf(false))
//                    }
                }
            }
        }
    }

    @Composable
    fun welcomePage(modifier: Modifier) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.welcome_page_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_background)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp),
                horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress
                    },
                    modifier = Modifier
                        .size(500.dp)
                )
            }
            Spacer(modifier = Modifier
                .height(32.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)
                )
            ) {
                Column(modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Her şey tamam! Artık uygulamayı kullanmaya hazırsınız.",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.golden_global),
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 24.dp)
                    )
                    Text(
                        text = "Tekrardan Hoşgeldiniz! Uygulamayı kullanmaya devam etmek için ilerle butonuna basınız. " +
                                "Eğer uygulamayı kullanırken herhangi bir sorun yaşarsanız canlı destek sisteminden yardım alabilirsiniz. Haydi Başlayalım! ",
                        color = colorResource(id = R.color.notifications_text),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(1.5f, TextUnitType.Em),
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentWidth()
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                        horizontalArrangement = Arrangement.Center) {
                        Button(
                            onClick = {
                                notificationState.value = 4
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.button_background),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Unspecified,
                                disabledContentColor = Color.Unspecified
                            ),
                            modifier = Modifier
                                .background(colorResource(id = R.color.button_background),
                                    shape = RoundedCornerShape(25.dp))
                        ) {
                            Text(
                                text = "İlerle"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CustomDialog(openDialogCustom: MutableState<Boolean>) {
        Dialog(onDismissRequest = {
            openDialogCustom.value = true // kullanıcı dialog'un dışına bastığında dialog kapanmasın
        }) {
            CustomDialogUI(openDialogCustom = openDialogCustom)
        }
    }

    @Composable
    fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>){
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp)
        ) {
            Column(modifier = Modifier.
            background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = if(notificationState.value == 0) R.drawable.ic_location else if(notificationState.value == 1) R.drawable.ic_contact else R.drawable.ic_notification_bell),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        color = colorResource(id = R.color.notification_bell)
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth()
                )
                Column(modifier = Modifier
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (notificationState.value == 0) "Bu uygulamanın cihazınızın konumuna erişmesine izin verilsin mi?" else if (notificationState.value == 1) "Bu uygulamanın cihazınızın rehberine erişmesine izin verilsin mi?" else "Bu uygulamanın size bildirimler göndermesine izin verilsin mi?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2
                    )
                    Text(
                        text = if(notificationState.value == 0) "(Bu uygulamanın konum ayarlarını daha sonra Ayarlar/Konum kısmından değiştirebilirsiniz.)" else if (notificationState.value == 1) "(Bu uygulamanın telefon rehber ayarlarını daha sonra Ayarlar/Rehber kısmından değiştirebilirsiniz.)" else "(Bu uygulamanın bildirm ayarlarını daha sonra Ayarlar/Bildirimler kısmından değiştirebilirsiniz.)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(colorResource(id = R.color.notifications_decisions)),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    TextButton(onClick = {
                        openDialogCustom.value = false
                        if (notificationState.value == 0) {
                            notificationState.value = 1
                        } else if (notificationState.value == 1) {
                            notificationState.value = 2
                        }
                    }) {
                        Text(
                            "İzin Verme",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.do_not_allow),
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        openDialogCustom.value = false
                        if (notificationState.value == 0) {
                            notificationState.value = 1
                        } else if (notificationState.value == 1) {
                            notificationState.value = 2
                        }
                    }) {
                        Text(
                            "İzin Ver",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun welcomePagePreview() {
        welcomePage(modifier = Modifier)
    }

    //    @SuppressLint("UnrememberedMutableState")
//    @Preview (name="Custom Dialog")
//    @Composable
//    fun MyDialogUIPreview(){
//        CustomDialogUI(openDialogCustom = mutableStateOf(false))
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun appNotificationsPreview() {
//        OnBoardingTheme {
//            appNotifications(modifier = Modifier)
//        }
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun enableContactPreview() {
//        OnBoardingTheme {
//            contactNotifications(modifier = Modifier)
//        }
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun locationNotificationsPreview() {
//        OnBoardingTheme {
//            locationNotifications(modifier = Modifier)
//        }
//    }
//
    @Preview(showBackground = true)
    @Composable
    fun mainPreview() {
        OnBoardingTheme {
            main(modifier = Modifier)
        }
    }
}