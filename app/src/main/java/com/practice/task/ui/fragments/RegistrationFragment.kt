package com.practice.task.ui.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.practice.task.R
import com.practice.task.databinding.FragmentRegistrationBinding
import com.practice.task.models.RegisterUserResponse
import com.practice.task.models.UiState
import com.practice.task.sharedprefs.SharedPrefManager
import com.practice.task.viewmodels.RegisterationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private var imageUri: Uri? = null
    private lateinit var getImage: ActivityResultLauncher<String>
    private val registrationViewModel by viewModel<RegisterationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.imageViewProfile.setImageURI(it)
                imageUri = it
            }
        }

        binding.buttonRegister.setOnClickListener {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            if (validate()) {
                registerUser()
            }
        }

        binding.imageViewProfile.setOnClickListener {
            getImage.launch("image/*")
        }

        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                registrationViewModel.registerResponse.collectLatest {
                    when (it) {
                        is UiState.Data -> {
                            val data = it.data as RegisterUserResponse
                            SharedPrefManager.registerSuccess(data.token,requireContext())
                            SharedPrefManager.saveUser(requireContext(),data.user)
                            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                        }
                        is UiState.Error -> {
                            Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_SHORT).show()
                            binding.buttonRegister.isEnabled = true
                            binding.buttonRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.peach,null))
                        }
                        else ->{

                        }
                    }
                }
            }

        }


        return binding.root
    }

    private fun validate(): Boolean {
        if (binding.editTextName.text.length < 4) {
            Snackbar.make(
                binding.root,
                "Minimum 4 characters required for name!",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }
        if (binding.editTextEmail.text.isEmpty()) {
            Snackbar.make(binding.root, "Email required!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (binding.editTextPhone.text.isEmpty()) {
            Snackbar.make(binding.root, "Phone no. required!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (binding.editTextPassword.text.isEmpty()) {
            Snackbar.make(binding.root, "Password required!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (binding.editTextConfirm.text.isEmpty()) {
            Snackbar.make(binding.root, "Confirm password required!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (binding.editTextPassword.text.toString() != binding.editTextConfirm.text.toString()) {
            Snackbar.make(binding.root, "Passwords do not match!", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (imageUri == null) {
            Snackbar.make(binding.root, "Please select an image first!", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun registerUser() {
        binding.buttonRegister.isEnabled = false
        binding.buttonRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.peach_l,null))

        val name = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            binding.editTextName.text.toString()
        )
        val email = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            binding.editTextEmail.text.toString()
        )
        val phone = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            binding.editTextPhone.text.toString()
        )
        val password = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            binding.editTextPassword.text.toString()
        )
        val confirmPassword = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            binding.editTextConfirm.text.toString()
        )

        val filesDir = requireActivity().applicationContext.filesDir
        val file = File(filesDir,"profile.png")

        val inputStream = requireActivity().contentResolver.openInputStream(imageUri!!)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        inputStream.close()

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val image = MultipartBody.Part.createFormData("photo", file.name, requestFile)

        lifecycleScope.launch(Dispatchers.IO) {
            registrationViewModel.registerUser(name, email, phone, password, confirmPassword, image)
        }
    }

}