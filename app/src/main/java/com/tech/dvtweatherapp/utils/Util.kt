package com.tech.dvtweatherapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.tech.dvtweatherapp.R
import com.tech.dvtweatherapp.data.local.datasource.SharedPreferences
import com.tech.dvtweatherapp.ui.theme.CloudyBlue200
import com.tech.dvtweatherapp.ui.theme.RainyBlue200
import com.tech.dvtweatherapp.ui.theme.SunnyBlue200
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

internal class Util {
    companion object {
        fun getWeekDayFromUTC(timeString: Int?): String {
            val sdf = SimpleDateFormat("EEEE")
            val dateFormat: Date = Date(timeString.toString().toLong() * 1000)
            val weekday: String = sdf.format(dateFormat)
            return weekday
        }

        fun getMinWeekDayFromUTC(timeString: Int?): String {
            val sdf = SimpleDateFormat("EEE")
            val dateFormat: Date = Date(timeString.toString().toLong() * 1000)
            val weekday: String = sdf.format(dateFormat)
            return weekday
        }

        fun getMinMonthFromUTC(timeString: Int?): String {
            val sdf = SimpleDateFormat("d MMM")
            val dateFormat: Date = Date(timeString.toString().toLong() * 1000)
            val month: String = sdf.format(dateFormat)
            return month
        }

        fun getDateLatestUpdated(timeString: Int?): String {
            val sdf = SimpleDateFormat("hh:mm a")
            val dateFormat: Date = Date(timeString.toString().toLong() * 1000)
            val dateTime: String = sdf.format(dateFormat)
            return dateTime
        }


        fun getCurrentDayOfTheWeek(): String {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            return sdf.format(d)
        }

        fun hasPermissions(
            context: Context?,
            vararg permissions: String
        ): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            permission
                        ) !== PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
            }
            return true
        }

        private fun checkIfLocationIsEnabledDialog(context: Context) {
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.location_service_title))
            builder.setMessage(
                context.getString(R.string.location_service_sms)
            )
            builder.setCancelable(false)
            builder.setPositiveButton(
                context.getString(R.string.location_service_yes)
            ) { dialog, which ->
                dialog.dismiss()
                val myIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                context.startActivity(myIntent)
            }
            builder.show()
        }

        fun validateAndForceLocationSetting(context: FragmentActivity) {
            if (!isLocationEnabled(context)) {
                checkIfLocationIsEnabledDialog(context)
            }
            if (!hasPermissions(
                    context,
                    Constants.PERMISSIONS_LOCATION[0],
                    Constants.PERMISSIONS_LOCATION[1]
                )
            ) {
                ActivityCompat.requestPermissions(
                    Objects.requireNonNull(context),
                    Constants.PERMISSIONS_LOCATION,
                    1
                )
            }
        }

        private fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    locationMode = Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.LOCATION_MODE
                    )
                } catch (e: SettingNotFoundException) {
                    e.printStackTrace()
                }
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
                !TextUtils.isEmpty(locationProviders)
            }
        }

        private fun displayNeverAskAgainDialog(context: Context) {
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.label_location_title))
            builder.setMessage(
                context.getString(R.string.label_location_message)
            )
            builder.setCancelable(false)
            builder.setPositiveButton(
                context.getString(R.string.btn_permit_manually)
            ) { dialog, _ ->
                dialog.dismiss()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri =
                    Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }

        fun checkIfLocationPermissionIsEnabled(
            context: Context,
            sharedPreferences: SharedPreferences
        ) {
            if (!hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (sharedPreferences.neverAskAgainSelected(
                            context as Activity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        displayNeverAskAgainDialog(context)
                    } else {
                        ActivityCompat.requestPermissions(
                            context,
                            Constants.PERMISSIONS_LOCATION,
                            Constants.PERMISSION_ALL
                        )
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        Constants.PERMISSIONS_LOCATION,
                        Constants.PERMISSION_ALL
                    )
                }
            }
        }

        fun getWeatherIconDrawable(context: Context, mainDescription: String?): Drawable? {
            val weatherDrawable: Drawable? = when (mainDescription) {
                "Clouds" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_partlysunny_xxh,
                    null
                )

                "Rains" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_rain_xxh,
                    null
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_clear_xxh,
                    null
                )
            }
            return weatherDrawable
        }

        fun getWeatherBackgroundDrawable(context: Context, mainDescription: String?): Drawable? {
            val weatherDrawable: Drawable? = when (mainDescription) {
                "Clouds" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_cloudy,
                    null
                )

                "Rains" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_rainy,
                    null
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_sunnypng,
                    null
                )
            }
            return weatherDrawable
        }

        fun getWeatherBackgroundColor(mainDescription: String?): Color {
            val weatherColor: Color = when (mainDescription) {
                "Clouds" -> CloudyBlue200
                "Rains" -> RainyBlue200
                else -> SunnyBlue200
            }
            return weatherColor
        }

        fun getFavouriteDrawable(context: Context, status: Boolean?): Drawable? {
            val weatherDrawable: Drawable? = when (status) {
                true -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_favorite_black_24dp,
                    null
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_favorite_border_black_24dp,
                    null
                )
            }
            return weatherDrawable
        }
    }
}
