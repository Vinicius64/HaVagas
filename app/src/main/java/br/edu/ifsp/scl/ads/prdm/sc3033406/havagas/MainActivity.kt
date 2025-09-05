package br.edu.ifsp.scl.ads.prdm.sc3033406.havagas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3033406.havagas.databinding.ActivityMainBinding
import java.util.Calendar
class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setupDatePicker()
        setupCelularToggle()
        setupFormacaoSpinner()
        setupButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDatePicker() {
        amb.dataNascimentoEt.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, year, month, day ->
                val dd = day.toString().padStart(2, '0')
                val mm = (month + 1).toString().padStart(2, '0')
                amb.dataNascimentoEt.setText("$dd/$mm/$year")
            }, y, m, d).show()
        }
    }

    private fun setupCelularToggle() {
        amb.mostrarCelularCb.setOnCheckedChangeListener { _, checked ->
            amb.telefoneCelularEt.visibility = if (checked) View.VISIBLE else View.GONE
            if (!checked) amb.telefoneCelularEt.setText("")
        }
    }

    private fun setupFormacaoSpinner() {
        amb.formacaoSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val sel = parent?.getItemAtPosition(position)?.toString() ?: ""

                amb.containerFundamentalMedio.visibility = View.GONE
                amb.containerGraduacaoEspecializacao.visibility = View.GONE
                amb.containerMestradoDoutorado.visibility = View.GONE

                when (sel) {
                    "Fundamental", "Médio" -> {
                        amb.containerFundamentalMedio.visibility = View.VISIBLE
                    }
                    "Graduação", "Especialização" -> {
                        amb.containerGraduacaoEspecializacao.visibility = View.VISIBLE
                    }
                    "Mestrado", "Doutorado" -> {
                        amb.containerMestradoDoutorado.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupButtons() {
        amb.salvarBtn.setOnClickListener {
            if (!validarCampos()) return@setOnClickListener
            mostrarResumo()
        }

        amb.limparBtn.setOnClickListener {
            limparFormulario()
        }
    }


    private fun validarCampos(): Boolean {
        if (amb.nomeCompletoEt.text.isNullOrBlank()) {
            amb.nomeCompletoEt.error = "Informe o nome completo"
            amb.nomeCompletoEt.requestFocus()
            return false
        }
        if (amb.emailEt.text.isNullOrBlank()) {
            amb.emailEt.error = "Informe o e-mail"
            amb.emailEt.requestFocus()
            return false
        }

        if(amb.telefoneEt.text.isNullOrBlank()){
            amb.telefoneEt.error = "Informe o telefone"
            amb.telefoneEt.requestFocus()
            return false
        }

        if(amb.mostrarCelularCb.isChecked && amb.telefoneCelularEt.text.isNullOrBlank()){
            amb.telefoneCelularEt.error = "Informe o telefone celular"
            amb.telefoneCelularEt.requestFocus()
            return false
        }

        if(amb.dataNascimentoEt.text.isNullOrBlank()){
            amb.dataNascimentoEt.error = "Informe a data de nascimento"
            amb.dataNascimentoEt.requestFocus()
            return false
        }

        when (amb.formacaoSp.selectedItem?.toString()) {
            "Selecione..." -> {
                AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Selecione a formação")
                    .setPositiveButton("OK") { _, _ ->
                        amb.formacaoSp.requestFocus()
                    }
                    .show()
                return false
            }
            "Fundamental", "Médio" -> {
                if (amb.anoFormaturaFmEt.text.isNullOrBlank()) {
                    amb.anoFormaturaFmEt.error = "Informe o ano de formatura"
                    amb.anoFormaturaFmEt.requestFocus()
                    return false
                }
            }
            "Graduação", "Especialização" -> {
                if (amb.anoConclusaoGeEt.text.isNullOrBlank()) {
                    amb.anoConclusaoGeEt.error = "Informe o ano de conclusão"
                    amb.anoConclusaoGeEt.requestFocus()
                    return false
                }
                if (amb.instituicaoGeEt.text.isNullOrBlank()) {
                    amb.instituicaoGeEt.error = "Informe a instituição"
                    amb.instituicaoGeEt.requestFocus()
                    return false
                }
            }
            "Mestrado", "Doutorado" -> {
                if (amb.anoConclusaoMdEt.text.isNullOrBlank()) {
                    amb.anoConclusaoMdEt.error = "Informe o ano de conclusão"
                    amb.anoConclusaoMdEt.requestFocus()
                    return false
                }
                if (amb.instituicaoMdEt.text.isNullOrBlank()) {
                    amb.instituicaoMdEt.error = "Informe a instituição"
                    amb.instituicaoMdEt.requestFocus()
                    return false
                }
                if (amb.tituloMonografiaMdEt.text.isNullOrBlank()) {
                    amb.tituloMonografiaMdEt.error = "Informe o título da monografia"
                    amb.tituloMonografiaMdEt.requestFocus()
                    return false
                }
                if (amb.orientadorMdEt.text.isNullOrBlank()) {
                    amb.orientadorMdEt.error = "Informe o orientador"
                    amb.orientadorMdEt.requestFocus()
                    return false
                }
            }
        }

        if(amb.vagasInteresseEt.text.isNullOrBlank()){
            amb.vagasInteresseEt.error = "Informe as vagas de interesse"
            amb.vagasInteresseEt.requestFocus()
            return false
        }

        return true
    }

    private fun mostrarResumo() {
        val nome = amb.nomeCompletoEt.text.toString()
        val email = amb.emailEt.text.toString()
        val receberEmails = if (amb.receberEmailCb.isChecked) "Sim" else "Não"
        val telefone = amb.telefoneEt.text.toString()

        val tipoTelefone = when (amb.tipoTelefoneRg.checkedRadioButtonId) {
            amb.telefoneComercialRb.id -> "Comercial"
            amb.telefoneResidencialRb.id -> "Residencial"
            else -> "—"
        }

        val celular = if (amb.mostrarCelularCb.isChecked)
            amb.telefoneCelularEt.text.toString()
        else
            "Não informado"

        val sexo = getSelectedRadioText(amb.sexoRg)
        val dataNasc = amb.dataNascimentoEt.text.toString()
        val formacao = amb.formacaoSp.selectedItem?.toString() ?: "—"

        val detalhesFormacao = when (formacao) {
            "Fundamental", "Médio" -> """
                • Ano de formatura: ${amb.anoFormaturaFmEt.text}
            """.trimIndent()

            "Graduação", "Especialização" -> """
                • Ano de conclusão: ${amb.anoConclusaoGeEt.text}
                • Instituição: ${amb.instituicaoGeEt.text}
            """.trimIndent()

            "Mestrado", "Doutorado" -> """
                • Ano de conclusão: ${amb.anoConclusaoMdEt.text}
                • Instituição: ${amb.instituicaoMdEt.text}
                • Título da monografia: ${amb.tituloMonografiaMdEt.text}
                • Orientador: ${amb.orientadorMdEt.text}
            """.trimIndent()

            else -> "—"
        }

        val vagas = amb.vagasInteresseEt.text.toString()

        val msg = buildString {
            appendLine("Nome completo: $nome")
            appendLine("E-mail: $email")
            appendLine("Receber e-mails de oportunidades: $receberEmails")
            appendLine("Telefone: $telefone ($tipoTelefone)")
            appendLine("Telefone celular: $celular")
            appendLine("Sexo: $sexo")
            appendLine("Data de nascimento: $dataNasc")
            appendLine("Formação: $formacao")
            if (detalhesFormacao != "—") {
                appendLine(detalhesFormacao)
            }
            appendLine("Vagas de interesse: ${if (vagas.isBlank()) "—" else vagas}")
        }

        AlertDialog.Builder(this)
            .setTitle("Confirmação do Cadastro")
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun limparFormulario() {
        amb.nomeCompletoEt.setText("")
        amb.emailEt.setText("")
        amb.receberEmailCb.isChecked = false

        amb.telefoneEt.setText("")
        amb.tipoTelefoneRg.check(amb.telefoneComercialRb.id)

        amb.mostrarCelularCb.isChecked = false
        amb.telefoneCelularEt.setText("")
        amb.telefoneCelularEt.visibility = View.GONE

        amb.sexoRg.check(amb.masculinoRb.id)
        amb.dataNascimentoEt.setText("")

        amb.formacaoSp.setSelection(0)
        amb.containerFundamentalMedio.visibility = View.GONE
        amb.containerGraduacaoEspecializacao.visibility = View.GONE
        amb.containerMestradoDoutorado.visibility = View.GONE

        amb.anoFormaturaFmEt.setText("")
        amb.anoConclusaoGeEt.setText("")
        amb.instituicaoGeEt.setText("")
        amb.anoConclusaoMdEt.setText("")
        amb.instituicaoMdEt.setText("")
        amb.tituloMonografiaMdEt.setText("")
        amb.orientadorMdEt.setText("")

        amb.vagasInteresseEt.setText("")
    }

    private fun getSelectedRadioText(rg: android.widget.RadioGroup): String {
        val id = rg.checkedRadioButtonId
        if (id == -1) return "—"
        val rb = findViewById<RadioButton>(id)
        return rb.text?.toString() ?: "—"
    }
}