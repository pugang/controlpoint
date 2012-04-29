package br.controlpoint.pages

import org.apache.wicket.markup.html.form.{Form,PasswordTextField,TextField}
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.protocol.http.request.WebClientInfo
import org.apache.wicket.spring.injection.annot.SpringBean
import br.controlpoint.entities.Usuario
import br.controlpoint.mediators.TUsuarioMediator
import javax.persistence.Entity

class LoginPage extends WebPage {

  val serialVersionUID = 1L;

  @SpringBean
  var usuarioMediatorScala: TUsuarioMediator = _

  var info2 = getSession.getClientInfo.asInstanceOf[WebClientInfo]

  var usuario = new Usuario()

  getSession().clear()

  var form = new Form[Usuario]("form") {
    val serialVersionUID = 1L
    override protected def onSubmit() {
      var usuarioLogado = usuarioMediatorScala.fazerLogin(usuario)
      if (usuarioLogado != null) {
        var teste = info2.getProperties().getRemoteAddress()
        var teste2 = teste.replace(".", " ").split(" ")
        teste = teste2(0) + "." + teste2(1) + "." + teste2(2) + "."
        if (usuarioLogado.ips.contains(teste)) {
          setResponsePage(new PontoPage(usuarioLogado, true))
        } else {
          error("Você não tem acesso desse ip!")
        }
      } else {
        error("Login Incorretor!")
      }
    }
  }

  form.add(new TextField[String]("login", new PropertyModel[String](usuario, "login")).setRequired(true))

  form.add(new PasswordTextField("senha", new PropertyModel[String](usuario, "senha")).setRequired(true))

  form.add(new FeedbackPanel("feedback"))

  this.add(form)

}