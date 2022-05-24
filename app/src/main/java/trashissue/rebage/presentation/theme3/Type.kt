package trashissue.rebage.presentation.theme3

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import trashissue.rebage.R

val OpenSans = FontFamily(
	Font(R.font.opensans_regular),
	Font(R.font.opensans_bold, FontWeight.Bold),
	Font(R.font.opensans_bold, FontWeight.Bold, FontStyle.Italic),
	Font(R.font.opensans_extrabold, FontWeight.ExtraBold),
	Font(R.font.opensans_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
	Font(R.font.opensans_italic, FontWeight.Normal, FontStyle.Italic),
	Font(R.font.opensans_light, FontWeight.Light),
	Font(R.font.opensans_light, FontWeight.Light, FontStyle.Italic),
	Font(R.font.opensans_medium, FontWeight.Medium),
	Font(R.font.opensans_medium_italic, FontWeight.Medium, FontStyle.Italic),
	Font(R.font.opensans_semibold, FontWeight.SemiBold),
	Font(R.font.opensans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic)
)

val Typography = Typography(
	displayLarge = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 57.sp,
		lineHeight = 64.sp,
		letterSpacing = (-0.25).sp,
	),
	displayMedium = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 45.sp,
		lineHeight = 52.sp,
		letterSpacing = 0.sp,
	),
	displaySmall = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 36.sp,
		lineHeight = 44.sp,
		letterSpacing = 0.sp,
	),
	headlineLarge = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 32.sp,
		lineHeight = 40.sp,
		letterSpacing = 0.sp,
	),
	headlineMedium = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 28.sp,
		lineHeight = 36.sp,
		letterSpacing = 0.sp,
	),
	headlineSmall = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 24.sp,
		lineHeight = 32.sp,
		letterSpacing = 0.sp,
	),
	titleLarge = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 22.sp,
		lineHeight = 28.sp,
		letterSpacing = 0.sp,
	),
	titleMedium = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.1.sp,
	),
	titleSmall = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	labelLarge = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp,
	),
	bodyLarge = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp,
	),
	bodyMedium = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 14.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.25.sp,
	),
	bodySmall = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.W400,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.4.sp,
	),
	labelMedium = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	),
	labelSmall = TextStyle(
		fontFamily = OpenSans,
		fontWeight = FontWeight.Medium,
		fontSize = 11.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp,
	),
)
