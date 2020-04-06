package pe.solera.testing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.text.HtmlCompat

class MainActivity : AppCompatActivity(){

    private var btnSendEmail : AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.btnSendEmail = findViewById(R.id.btnSendEmail)
        this.btnSendEmail?.setOnClickListener {
            this.emailIntent()
        }
    }

    private fun emailIntent() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "message/rfc822"
            val uriText = String.format("mailto:%s?subject=%s&body=%s", "socios@dinersclub.com.pe", "Reprogramación Diners Club",
                emailBody()
            )
            data = Uri.parse(uriText)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun emailBody() : String {
        return StringBuilder()
            .append("Estimado(a) Socio(a):<br><br>")
            .append("Muchas gracias por contactarse con nosotros, en estos momentos Diners Club es su aliado.<br>")
            .append("Usted cuenta con 2 opciones de reprogramación:<br><br>")
            .append("<b>Opción 1:</b> Reprogramación de su pago mínimo en 6 Cuotas Sin Intereses.<br>")
            .append("<b>Opción 2:</b> Aplazar su Pago Total del mes para el próximo Estado de Cuenta.<br><br>")
            .append("Por favor, para proceder indíquenos la opción que mejor se le acomode y su Documento de Identidad:")
            .append("<ul><li>Número de Opción [1 o 2]:</li>")
            .append("<li>Tipo de Documento [DNI o CE]:</li>")
            .append("<li>Número de Documento:</li>")
            .append("</ul>")
            .append("Recordemos que hoy más que nunca, <b>la Serenidad, Solidaridad, Responsabilidad y Control</b> son fundamentales para superar esta crisis.<br><br>")
            .append("Protéjase, proteja a su familia y proteja a los demás.<br><br>")
            .append("#QuédateEnCasa<br>")
            .append("#YoMeQuedoEnCasa<br><br>")
            .append("Atentamente,<br>")
            .append("<b>DINERS CLUB</b><br>")
            .toString()
    }

    /*
    intent.putExtra(Intent.EXTRA_TEXT,
            Html.fromHtml(StringBuilder()
                .append("<html>")
                .append("<head>")
                .append("<title></title>")
                .append("<link href='https://svc.webspellchecker.net/spellcheck31/lf/scayt3/ckscayt/css/wsc.css' rel='stylesheet' type='text/css' />")
                .append("</head>")
                .append("<body aria-readonly='false'>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Estimado(a) Socio(a):</span></p>")
                .append("&nbsp;")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Muchas gracias por contactarse con nosotros, en estos momentos Diners Club es su aliado.</span></p>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Usted cuenta con 2 opciones de reprogramaci&oacute;n:</span></p>")
                .append("<strong>​​Opci&oacute;n 1: </strong><span style='font-family:calibri,sans-serif; font-size:11pt'>Reprogramaci&oacute;n de su pago m&iacute;nimo en 6 Cuotas Sin Intereses.</span>")
                .append("<p><strong>Opci&oacute;n 2:</strong><span style='font-family:calibri,sans-serif; font-size:11pt'> Aplazar su Pago Total del mes para el pr&oacute;ximo Estado de Cuenta.</span></p>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Por favor, para proceder ind&iacute;quenos la opci&oacute;n que mejor se le acomode y su Documento de Identidad:</span></p>")
                .append("<ul>")
                .append("<li>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>N&uacute;mero de Opci&oacute;n </span><em>[1 o 2]</em><span style='font-family:calibri,sans-serif; font-size:11pt'>:</span></p>")
                .append("</li>")
                .append("<li>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Tipo de Documento </span><em>[DNI o CE]</em><span style='font-family:calibri,sans-serif; font-size:11pt'>:</span></p>")
                .append("</li>")
                .append("<li>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>N&uacute;mero de Documento:</span></p>")
                .append("</li>")
                .append("</ul>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Recordemos que hoy m&aacute;s que nunca, la </span><strong>Serenidad, Solidaridad, Responsabilidad y Control </strong><span style='font-family:calibri,sans-serif; font-size:11pt'>son fundamentales para superar esta crisis. </span><br />")
                .append("<span style='font-family:calibri,sans-serif; font-size:11pt'>&nbsp; </span><br />")
                .append("<span style='font-family:calibri,sans-serif; font-size:11pt'>Prot&eacute;jase, proteja a su familia y proteja a los dem&aacute;s. </span><br />")
                .append("<span style='font-family:calibri,sans-serif; font-size:11pt'>&nbsp; </span><br />")
                .append("<span style='font-family:calibri,sans-serif; font-size:11pt'>#Qu&eacute;dateEnCasa&nbsp;</span></p>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>#YoMeQuedoEnCasa&nbsp;</span></p>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>&nbsp;</span></p>")
                .append("<p><span style='font-family:calibri,sans-serif; font-size:11pt'>Atentamente,&nbsp;</span></p>")
                .append("<p><strong>DINERS CLUB</strong></p>")
                .append("</body>")
                .append("</html>")
                .toString(), FROM_HTML_MODE_LEGACY)
        )
    * */
}
