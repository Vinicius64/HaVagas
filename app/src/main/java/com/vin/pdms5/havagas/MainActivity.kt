package com.vin.pdms5.havagas


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vin.pdms5.havagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.addMobilePhoneCb.setOnCheckedChangeListener { _, isChecked ->
            amb.mobilePhoneEt.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        amb.educationSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val educationLevel = parent?.getItemAtPosition(position).toString()
                when (educationLevel) {
                    "Fundamental", "Médio" -> {
                        amb.educationDetailsLl.visibility = View.VISIBLE
                        amb.graduationYearEt.visibility = View.VISIBLE
                        amb.institutionEt.visibility = View.GONE
                        amb.thesisTitleEt.visibility = View.GONE
                        amb.advisorEt.visibility = View.GONE
                    }
                    "Graduação", "Especialização" -> {
                        amb.educationDetailsLl.visibility = View.VISIBLE
                        amb.graduationYearEt.visibility = View.VISIBLE
                        amb.institutionEt.visibility = View.VISIBLE
                        amb.thesisTitleEt.visibility = View.GONE
                        amb.advisorEt.visibility = View.GONE
                    }
                    "Mestrado", "Doutorado" -> {
                        amb.educationDetailsLl.visibility = View.VISIBLE
                        amb.graduationYearEt.visibility = View.VISIBLE
                        amb.institutionEt.visibility = View.VISIBLE
                        amb.thesisTitleEt.visibility = View.VISIBLE
                        amb.advisorEt.visibility = View.VISIBLE
                    }
                    else -> amb.educationDetailsLl.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                amb.educationDetailsLl.visibility = View.GONE
            }
        }

        amb.saveBt.setOnClickListener {
            val filledFields = StringBuilder()
            filledFields.append("Nome completo: ${amb.fullNameEt.text}\n")
            filledFields.append("E-mail: ${amb.emailEt.text}\n")
            filledFields.append("Receber atualizações por e-mail: ${amb.emailUpdatesCb.isChecked}\n")
            filledFields.append("Telefone: ${amb.phoneEt.text}\n")
            filledFields.append("Tipo de telefone: ${if (amb.commercialRb.isChecked) "Comercial" else "Residencial"}\n")
            if (amb.addMobilePhoneCb.isChecked) {
                filledFields.append("Telefone celular: ${amb.mobilePhoneEt.text}\n")
            }
            filledFields.append("Sexo: ${if (amb.maleRb.isChecked) "Masculino" else "Feminino"}\n")
            filledFields.append("Data de nascimento: ${amb.dobEt.text}\n")
            filledFields.append("Formação: ${amb.educationSp.selectedItem}\n")
            if (amb.educationDetailsLl.visibility == View.VISIBLE) {
                filledFields.append("Ano de formatura: ${amb.graduationYearEt.text}\n")
                if (amb.institutionEt.visibility == View.VISIBLE) {
                    filledFields.append("Instituição: ${amb.institutionEt.text}\n")
                }
                if (amb.thesisTitleEt.visibility == View.VISIBLE) {
                    filledFields.append("Título da monografia: ${amb.thesisTitleEt.text}\n")
                }
                if (amb.advisorEt.visibility == View.VISIBLE) {
                    filledFields.append("Orientador: ${amb.advisorEt.text}\n")
                }
            }
            filledFields.append("Vagas de interesse: ${amb.jobInterestsEt.text}\n")

            Toast.makeText(this, filledFields.toString(), Toast.LENGTH_LONG).show()
        }

        amb.clearBt.setOnClickListener {
            amb.fullNameEt.text.clear()
            amb.emailEt.text.clear()
            amb.emailUpdatesCb.isChecked = false
            amb.phoneEt.text.clear()
            amb.phoneTypeRg.clearCheck()
            amb.addMobilePhoneCb.isChecked = false
            amb.mobilePhoneEt.text.clear()
            amb.genderRg.clearCheck()
            amb.dobEt.text.clear()
            amb.educationSp.setSelection(0)
            amb.graduationYearEt.text.clear()
            amb.institutionEt.text.clear()
            amb.thesisTitleEt.text.clear()
            amb.advisorEt.text.clear()
            amb.jobInterestsEt.text.clear()
        }
    }
}