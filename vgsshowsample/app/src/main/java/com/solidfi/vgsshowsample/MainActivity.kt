package com.solidfi.vgsshowsample

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.solidfi.vgsshowsample.databinding.ActivityMainBinding
import com.verygoodsecurity.vgsshow.VGSShow
import com.verygoodsecurity.vgsshow.core.VGSEnvironment
import com.verygoodsecurity.vgsshow.core.listener.VGSOnResponseListener
import com.verygoodsecurity.vgsshow.core.logs.VGSShowLogger
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    //function used to initialize UI
    private fun initUI() {
        binding.environment.setText(getString(R.string.sandbox))
        binding.environment.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.environment)
            popupMenu.menu.add(getString(R.string.sandbox))
            popupMenu.menu.add(getString(R.string.production))
            popupMenu.setOnMenuItemClickListener { item ->
                binding.environment.setText(item.title)
                true
            }
            popupMenu.show()
        }

        binding.card.cardNumberVGS.addTransformationRegex(
            "(\\d{4})(\\d{4})(\\d{4})(\\d{4})".toRegex(),
            "$1  $2  $3  $4"
        )
        binding.submitButton.setOnClickListener {
            it.hideKeyboard()
            callVGSShow()
        }
    }

    //function used to call vgs show
    private fun callVGSShow() {
        val vgsShow = VGSShow(this, binding.vgsVaultId.text.toString().trim(), getVGSEnv())
        VGSShowLogger.isEnabled = true
        vgsShow.subscribe(binding.card.cardNumberVGS)
        vgsShow.subscribe(binding.card.expiryMonthVGS)
        vgsShow.subscribe(binding.card.expiryYearVGS)
        vgsShow.subscribe(binding.card.cvvVGS)
        vgsShow.setCustomHeader("sd-show-token", binding.showToken.text.toString().trim())
        val cardId = binding.cardId.text.toString().trim()
        vgsShow.requestAsync("/v2/card/$cardId/show", VGSHttpMethod.GET)
        vgsShow.addOnResponseListener(object : VGSOnResponseListener {
            override fun onResponse(response: VGSResponse) {
                when (response) {
                    is VGSResponse.Success -> {
                        val successCode = response.code
                        binding.card.slashTextView.visibility = View.VISIBLE
                    }
                    is VGSResponse.Error -> {
                        val errorCode = response.code
                        val message = response.message
                        handleError(message)
                    }
                }
            }
        })
    }

    //function used to show the error message to the user
    private fun handleError(message: String?) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setCancelable(false)
            .setTitle(null)
            .setMessage(if (message.isNullOrEmpty().not()) message else getString(R.string.something_went_wrong))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->

            }
            .show()
    }

    //function used to show the error message to the user if the pin setting is failed
    private fun getVGSEnv(): VGSEnvironment {
        return if (binding.environment.text.toString() == getString(R.string.production)) {
            VGSEnvironment.Live()
        } else {
            VGSEnvironment.Sandbox()
        }
    }

    //function used to hide keyboard
    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
